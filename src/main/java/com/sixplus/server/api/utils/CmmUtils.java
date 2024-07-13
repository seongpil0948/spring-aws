package com.sixplus.server.api.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.CharBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sixplus.server.api.core.exception.CustomErrorException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Slf4j
public class CmmUtils {
	static final String STR_SEP = "/";

	public static final BigDecimal MINUS_ONE = new BigDecimal("-1");

	public static String[] split(String str) {
		if(null == str || str.isEmpty()) return null;
		return str.split(STR_SEP);
	}

	public static String join(String... strs) {
		return String.join(STR_SEP, strs);
	}

	public static String join(List<String> strs) {
		if(null == strs) return null;
		return String.join(STR_SEP, strs);
	}

	public static String join(Collection<String> strs) {
		if(null == strs) return null;
		return String.join(STR_SEP, strs);
	}

	public static <T> String toString(T obj) {
		return ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public static <T> String toJsonString(T obj) {
		return toJsonString(obj, false);
	}

	private static final ObjectMapper jsonSimpleObjectMapper = new ObjectMapper()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
			.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
			.registerModule(new JavaTimeModule())
			.setSerializationInclusion(Include.NON_NULL);

	public static <T> String toJsonString(T obj, boolean isPretty) {
		try {
			if (isPretty) {
				return jsonSimpleObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			} else {
				return jsonSimpleObjectMapper.writeValueAsString(obj);
			}
		} catch (com.fasterxml.jackson.core.JsonProcessingException e) {
			return e.getMessage();
		}
	}

	/** 개인정보 마스킹 패턴(XML과 JSON 문자열에서 함께 사용) */
	private static String[] regExpPatterns = {
			"(Phone>|Phone\":\")\\d{9,16}",
			"(mobile\":\")\\d{9,16}",
			"(FirstName>|firstName\":\")[a-zA-Z ]+",
			"(DOB>|birthday\":\")[\\d-]+",
			"(PostalCode>|post\":\")\\d{4,16}",
			"(EmailAddress>|email\":\")[\\w.%+-]+@[\\w.]+\\.[a-zA-Z]{2,6}",
			"(AddressLine1>|addressLine1\":\"|address\":\")[\\w ,+\\-]+",
			"(AddressLine2>|addressLine2\":\")[\\w ,+\\-]+",
			"(AddressLine3>|addressLine3\":\")[\\w ,+\\-]+",
			"(DocNumber>|docNumber\":\")[\\w]+"
	};
	//개인정보 마스킹 2-1(로그), 개인정보 마스킹 2-2(VO객체 - BookingVO, BookingContactVO 등 toString 을 @Override 함)
	public static String toMaskPersonalInfo(String str) {
		String temp = str;
		for(String reg : regExpPatterns) {
			temp = temp.replaceAll(reg, "$1***");
		}

		return temp;
	}

	public static String toNumberOnly (String str) {
		if (!StringUtils.hasText(str)) return "";
		return str.replaceAll("[^\\d]", "");
	}

	public static <R> R toJsonObject(String jsonStr, Class<R> clazz) {
		if(!StringUtils.hasText(jsonStr)) return null;

		try {
			return jsonSimpleObjectMapper.readValue(jsonStr, clazz);
		} catch (IOException e) {
			throw new CustomErrorException(e.getMessage());
		}
	}

	/**
	 * 오류 식별코드로 사용할 랜덤 문자열 6자리 반환함(DataUtils.DEFAULT_KEY_SOURCE 이용함)
	 * @return
	 */
	public static String getNewErrorId() {
		return DataUtils.generateKey(6);
	}

	public static <T> String toString(T obj, ToStringStyle style) {
		return ToStringBuilder.reflectionToString(obj, style);
	}

	public static <T> String getListSize(List<T> obj) {
		StringBuilder sb = new StringBuilder();
		getListSize(obj, sb);
		return sb.toString();
	}

	public static String append(String... arrStr) {
		StringBuilder sb = new StringBuilder();
		Arrays.stream(arrStr).forEach(f -> sb.append(f));
		return sb.toString();
	}

	public static String append(Object... objects) {
		StringBuilder sb = new StringBuilder();
		Arrays.stream(objects).forEach(f -> sb.append(f.toString()));
		return sb.toString();
	}

	public static long diff(Date l, Date r) {
		return Math.abs(l.getTime() - r.getTime())/1000;
	}

	public static long diff(LocalDateTime l, LocalDateTime r) {
		return Duration.between(l, r).getSeconds();
	}

	public static long diff(Calendar l, Calendar r) {
		return Math.abs(l.getTimeInMillis()-r.getTimeInMillis())/1000;
	}

	@SuppressWarnings("unchecked")
	private static <T> void getListSize(List<T> obj, StringBuilder sb) {
		if(null == obj) return;
		sb.append("[size:").append(obj.size());
		if(!obj.isEmpty() && null != obj.get(0)) {
			String[] name = obj.get(0).getClass().getName().split("\\.");
			sb.append(",type:\"").append(name[name.length-1]).append("\"");
		}
		DataUtils.iterate(obj, it->{
			if (!(it instanceof List)) return;
			getListSize((List<Object>) it, sb);
		});
		sb.append("]");
	}

	public static Integer max(Integer l, Integer r) {
		if(null == l) return r;
		if(null == r) return l;
		return Math.max(l, r);
	}

	public static BigDecimal add(BigDecimal... vals) {
		return add(Arrays.asList(vals));
	}

	public static BigDecimal add(List<BigDecimal> vals) {
		if(null == DataUtils.findFirst(vals, it->null!=it)) return null;

		BigDecimal result = BigDecimal.ZERO;
		for(BigDecimal val : vals) {
			if(null == val) continue;
			result = result.add(val, MathContext.DECIMAL64);
		}
		return result;
	}

	public static BigDecimal add(BigDecimal l, BigDecimal r) {
		return null == r ? l : (null == l ? r : l.add(r));
	}

	public static BigDecimal subtract(BigDecimal l, BigDecimal r) {
		return null == r ? l : (null == l ? BigDecimal.ZERO.subtract(r) : l.subtract(r));
	}

	public static BigDecimal multiply(BigDecimal l, BigDecimal r) {
		return null == r ? null : (null == l ? null : l.multiply(r));
	}


	public static BigDecimal addRate(BigDecimal l, BigDecimal r) {
		return add(l, calcRate(l,r));
	}

	private static BigDecimal HUNDRED = BigDecimal.valueOf(100.0);
	public static BigDecimal calcRate(BigDecimal val, BigDecimal rate) {
		return val.multiply(rate).divide(HUNDRED);
	}

	public static BigDecimal add(BigDecimal l, BigDecimal r, boolean isRate) {
		return isRate ? addRate(l,r) : add(l,r);
	}

	//초단위를 문자열로 변환
	//{0}: day, {1}: hour, {2}: min, {3}: sec
	//ex) "{0}d{1}h{2}m"
	public static String timeFmt(long sec, String fmt) {
		long min = sec/60;
		long hour = min/60;
		long day = hour/24;

		return MessageFormat.format(fmt, day, hour%24, min%60, sec%60);
	}

	public static Integer min(Integer l, Integer r) {
		if (null == l) return r;
		if (null == r) return l;
		return Math.min(l,r);
	}

	public static void writeFile(String filepath, String content) throws IOException {
		File file = new File(filepath);

		String path = file.getParentFile().toString();
		File dir = new File(path);

		if(!dir.exists() && !dir.mkdirs()) return;

		FileOutputStream os = null;
		OutputStreamWriter osw = null;
		try {
			os = new FileOutputStream(file);
			osw = new OutputStreamWriter(os, "UTF8");
			osw.write(content);
			osw.flush();
		} finally {
			if(null != osw) osw.close();
			if(null != os)  os.close();
		}
	}

	public static String readResource(String filepath) throws IOException {
		return readResource(filepath, "UTF8");
	}

	public static String readResource(String filepath, String charset) throws IOException {
		InputStream fis = null;
		Reader reader = null;
		BufferedReader br = null;
		try {
			fis = CmmUtils.class.getClassLoader().getResourceAsStream(filepath);
			reader = new InputStreamReader(fis, charset);
			br = new BufferedReader(reader);
			StringBuilder sb = new StringBuilder();
			CharBuffer cbuf = CharBuffer.allocate(1024);
			while(0 < br.read(cbuf)) {
				cbuf.flip();
				sb.append(cbuf);
			}
			return sb.toString();
		} finally {
			if(null != br)     br.close();
			if(null != reader) reader.close();
			if(null != fis)    fis.close();
		}
	}

	public static <P> P[] toArray(List<P> data) {
		if(DataUtils.isEmpty(data)) return null;

		@SuppressWarnings("unchecked")
		P[] ret = (P[]) Array.newInstance(data.get(0).getClass(), data.size());
		data.toArray(ret);
		return ret;
	}

	public static String createFlow(StackTraceElement[] trace) {
		return String.join(" > ", DataUtils.map(trace,  StackTraceElement::getClassName, StackTraceElement::getMethodName, ".", "sun.reflect."));
	}

	public static <P> P[] clone(P[] data) {
		if(DataUtils.isEmpty(data)) return null;

		@SuppressWarnings("unchecked")
		P[] ret = (P[]) Array.newInstance(data[0].getClass(), data.length);
		for(int i = 0; i < ret.length; i++) ret[i] = clone(data[i]);
		return ret;
	}

	public static <P> List<P> clone(List<P> data) {
		if(DataUtils.isEmpty(data)) return null;
		return DataUtils.map(data, it->clone(it));
	}

	public static <P extends CmmBean> List<P> cloneCmmVOList(List<P> data) {
		if(DataUtils.isEmpty(data)) return null;
		return DataUtils.map(data, it->clone(it));
	}

	@SuppressWarnings("unchecked")
	public static <P extends CmmBean> P clone(P data) {
		if(null == data) return null;
		return (P)data.clone();
	}

	public static <P> P clone(P data) {
		return data;
	}

	/*
	 * 느린 방식의 복제
	 * Ref: http://javatechniques.com/blog/faster-deep-copies-of-java-objects/
	 */
	@SuppressWarnings("unchecked")
	public static <P extends Serializable> P cloneDeep(P data) throws IOException, ClassNotFoundException {
		try(
				FastByteArrayOutputStream outStream = new FastByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(outStream);
				ObjectInputStream in = new ObjectInputStream(outStream.getInputStream());

		) {
			out.writeObject(data);
			out.flush();

			return (P) in.readObject();
		}
	}

	public static int hashCode(Object[] data) {
		int result = 1;
		for(Object it : data) {
			result = 31 * result + (null==it?0:it.hashCode());
		}
		return result;
	}

	public static <P extends Comparable<P>> List<P> toSortedList(Set<P> data) {
		List<P> ret = new ArrayList<>(data);
		ret.sort((l,r)->l.compareTo(r));
		return ret;
	}

	public static String getMessage(MessageSource messageSource, String code, Object[] args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}

	public static String getMessage(MessageSource messageSource, String code) {
		return getMessage(messageSource, code, null);
	}

	public static String defaultIfEmpty(String value, String defaultVal) {
		return !StringUtils.hasText(value) ? defaultVal :value;
	}

	public static String leftPad(String value, int len, String padVal) {
		int padLen = null == value ? len : len - value.length();

		return len > 0 ? repeatString(padVal, padLen).concat(value) : value;
	}

	public static String repeatString(String value, int repeat) {
		String ret = "";
		for(int i=0; i<repeat; ++i) {
			ret = ret.concat(value);
		}
		return ret;
	}

	public static <T> BigDecimal sumBigDecimal(Collection<T> data, Function<T, BigDecimal> action) {
		return DataUtils.reduce(data, (n,i)->CmmUtils.add(n, action.apply(i)));
	}

	public static <T> Integer sumInteger(Collection<T> data, Function<T, Integer> action) {
		return DataUtils.reduce(data, (n,i)->{
			Integer val = action.apply(i);
			return null==n ? val : (null==val ? n : n+val);
		});
	}

	public static <T> boolean has(T[] data, T val) {
		if (DataUtils.isEmpty(data)) return false;

		for(T it : data) {
			if (it.equals(val)) return true;
		}

		return false;
	}

	public static <T> boolean has(Collection<T> data, T val) {
		if (DataUtils.isEmpty(data)) return false;
		return data.contains(val);
	}

	public static Integer parseInt(String str) {
		return parseInt(str, 0);
	}

	public static Integer parseInt(String str, Integer defaultVal) {
		if(!StringUtils.hasText(str)) return defaultVal;

		Integer ret = null;

		try {
			ret = Integer.parseInt(str);
		} catch(NumberFormatException e) {
			ret = defaultVal;
		}

		return ret;
	}

	// MultipartFile To File
	public static Optional<File> convert(String file_base, MultipartFile file) throws IOException {
		File convertFile = new File(file_base + File.separator + file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
			fos.write(file.getBytes());
		}
		return Optional.of(convertFile);
	}

	// 로컬에 저장된 파일 지우기
	public static void removeFile(File file) {
		if (file.delete()) {
			return;
		}
	}

	// 더샵 기존 비밀번호 암호화 함수
	/**
	 * 비밀번호 암호화
	 * @param amt
	 * @param rate
	 * @return
	 */
	public static String makeSha256Key(String str) {

		MessageDigest sha256;

		try {
			sha256 = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			return append(str, "-", System.currentTimeMillis());
		}

		try {
			sha256.update(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException ue) {
			log.error(ue.getMessage());
		}

		// sha256.update(str.getBytes());
		byte[] bytes = sha256.digest();
		int bytes_length = bytes.length;

		StringBuilder strBuffer = new StringBuilder();

		for (int i = 0; i < bytes_length; i++) {
			strBuffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		return strBuffer.toString().toUpperCase();
	}

	public static int getLength(String s) {
		return s.getBytes().length;
	}

	/**
	 * 스트링 잘라내기
	 * @param s 잘라낼 문자열
	 * @param len 잘라낼 길이
	 * @param tail 잘라낸 문자열 뒤에 붙일 문자열
	 * @return
	 */
	public static String cutString(String s, int len, String tail) {

		if (s == null) {
			return null;
		}

		int srcLen = getLength(s);
		if (srcLen < len) {
			return s;
		}

		String tmpTail = tail;
		if (tail == null) {
			tmpTail = "";
		}

		int tailLen = getLength(tmpTail);
		if (tailLen > len) {
			return "";
		}

		char a;
		int i = 0;
		int realLen = 0;
		for (i = 0; i < len - tailLen && realLen < len - tailLen; i++) {
			a = s.charAt(i);

			if ((a & 0xFF00) == 0) {
				realLen += 1;
			}
			else {
				realLen += getLength(Character.toString(a));
			}
		}

		while (getLength(s.substring(0, i)) > len - tailLen) {
			i--;
		}

		return s.substring(0, i) + tmpTail;
	}

	/**
	 * lambda distinct(key비교)
	 *
	 * @param keyExtractor
	 * @return
	 * @param <T>
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	/**
	 * lambda 안에 map key를 대문자로 변경
	 *
	 * @param map
	 * @return
	 * @param <D>
	 */
	public static <D> Map<String, D> convertUpperCaseMapInKey(Map<String, D> map) {
		return map.keySet().stream().collect(Collectors.toMap(String::toUpperCase, map::get));
	}

	/**
	 * batch redis key
	 *
	 * @param profile
	 * @param userId
	 * @return
	 */
	public static String getRedisBatchKey(String profile, String userId) {
		return CmmUtils.append("TK:", profile, ":B:", userId);
	}

	/**
	 * batch redis key
	 *
	 * @param profile
	 * @param userId
	 * @return
	 */
	public static String getRedisUserKey(String profile, String userId) {
		return CmmUtils.append("TK:", profile, ":U:", userId);
	}

	/**
	 * sonic redis key
	 *
	 * @param profile
	 * @param type
	 * @param userId
	 * @return
	 */
	public static String getRedisKey(String profile, String type, String userId) {
		return CmmUtils.append("TK:", profile, ":", type, ":", userId);
	}

	/**
	 *
	 * @param profile  activeProfile
	 * @param interval lifetime 또는 *
	 * @param keyName 키 이름
	 * @return
	 */
	public static String getRedisPatternKeys(String profile, String interval,String keyName) {
		return CmmUtils.append(profile, ":", interval,":", keyName , "*");
	}
	
    /**
     * 문자열이 Null이거나 ""일 경우 true 리턴
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if( str == null || str.length() < 1 ) {
            return true;
        }
        return false;
    }
	
    /**
     * long 숫자변환
     * @param str
     * @return
     */
    public static long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return 0L;
        }
    }
    
    /**
     * double 숫자변환
     * @param str
     * @return
     */
    public static double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0.0d;
        }
    }
    
    /**
     * 문자열이 NULL일 경우 빈문자열을 리턴
     * @param str
     * @return
     */
    public static String defaultString(String str) {

        if (str == null) {
            return "";
        }

        return str;
    }
    
	/** 널이거나 빈 문자열(숫자형)을 integer로 변환한다.
	*
	* @param org 입력문자열
	* @param converted 변환숫자
	* @return 치환된 Interger
	*/
	public static int null2Int(String org, int converted) {
		int i = 0;
		
		if (org == null || org.trim().length() == 0) {
			return converted;
		} else {
			try {
				i = Integer.parseInt(org); 
			} catch (Exception ex) { 
				i = converted;
			}
			return i;
		}
	}
	
    /**
     * 문자열에서 콤마(,) 제거
     * @param value
     * @return
     */
    public static String removeComma(String value) {

        if( isEmpty(value) ) {
            return "";
        }

        if( value.indexOf(",") > -1 ) {
            value = value.replaceAll(",", "");
        }

        return value;
    }
    
    /**
     * String 배열을 List로 변환한다.
     * @param arr String[]
     * @return List<String>
     */
    public static List<String> convertList(String[] arr) {

        List<String> list = null;

        if( arr != null && arr.length > 0 ) {
            list = new ArrayList<String>();

            for( int i=0; i<arr.length; i++ ) {
                list.add(arr[i]);
            }
        }

        return list;
    }
    
    /**
     * 문자열이 null이거나 혹은 빈문자열일 경우 대체문자열을 리턴
     * @param str
     * @param replaceStr
     * @return
     */
    public static String replaceNullString(String str, String replaceStr) {

        if (str == null || "".equals(str)) {
            return replaceStr;
        }

        return str;
    }
    
    /**
     * 주어진 스트링을 기본타입 int로 변환한다.
     * 
     * @param value
     *        변환할 스트링
     * @param defaultValue
     *        스트링이 null이거나 에러발생시 반환할 기본값
     * @return 변환된 값, 에러 발생시는 기본값 반환
     */
    public static int toInt(String value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException nfex) {
            return defaultValue;
        }
    }

	/**
	 * 지정 long 값에 대해 국 통화단위로 변경한다.
	 * @param price
	 * @return
	 */
	public static String getWon(String price) {
		try {
			return getWon(Long.parseLong(price));
		} catch(Exception e) {
			return "0";
		}
	}

	/**
	 * 지정 long 값에 대해 국 통화단위로 변경한다.
	 * @param w
	 * @return
	 */
	public static String getWon(long w) {
		DecimalFormat df = new DecimalFormat("###,###");
		return df.format(w);
	}

}
