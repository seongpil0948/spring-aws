package com.sixplus.server.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MaskUtils {

	private static Logger logger = LogManager.getLogger(MaskUtils.class);

	/**
	 * 전화번호 형태를 마스킹 처리한다.
	 *
	 * @param txt 암호화 하려하는 문자열
	 * @return String
	 * @throws Exception
	 */
	public static String convPhoneFormatMask(String phone) {

		if (phone == null)
			return "";

		StringBuffer rtnVal = new StringBuffer(phone.replaceAll("-", ""));
		try {
			if (rtnVal.indexOf("01") == 0) {
				rtnVal = new StringBuffer(rtnVal.toString().substring(0, 3) + "-" + rtnVal.substring(3));
			} else if (rtnVal.indexOf("02") == 0) {
				rtnVal = new StringBuffer(rtnVal.toString().substring(0, 2) + "-" + rtnVal.substring(2));
			} else if (rtnVal.indexOf("0") == 0) {
				rtnVal = new StringBuffer(rtnVal.toString().substring(0, 3) + "-" + rtnVal.substring(3));
			}
			if (rtnVal.length() > 4) {
				rtnVal = new StringBuffer(rtnVal.toString().substring(0, (rtnVal.length() - 4)) + "-"
						+ rtnVal.substring(rtnVal.length() - 4));
			}

			String[] strArr = rtnVal.toString().split("-");
			if (strArr.length >= 3) {
				rtnVal = new StringBuffer(strArr[0] + "-");
				for (int i = 0; i < strArr[1].length(); i++) {
					rtnVal.append("*");
				}
				rtnVal.append("-" + strArr[2]);
			} else if (strArr.length == 2) {
				rtnVal = new StringBuffer(strArr[0] + "-");
				for (int i = 0; i < strArr[1].length(); i++) {
					rtnVal.append("*");
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());

		}

		return rtnVal.toString();
	}

	/**
	 * 전화번호 형태를 마스킹 처리한다.
	 *
	 * @param txt 암호화 하려하는 문자열
	 * @return String
	 * @throws Exception
	 */

	public static String convPhoneMask(String phone) {
		StringBuffer rtnStr = new StringBuffer();
		if (phone == null)
			return "";

		if (Pattern.compile("(\\d{3})-(\\d{3,4})-(\\d{4})").matcher(phone).find()) {
			String[] emailArr = phone.split("-");

			rtnStr.append(emailArr[0] + "-");
			for (int i = 0; i < emailArr[1].length(); i++) {
				rtnStr.append("*");
			}
			rtnStr.append("-" + emailArr[2]);
		} else {
			char[] charPhone = phone.toCharArray();
			for (int i = 0; i < charPhone.length; i++) {
				if (charPhone.length == 11) { // 000 0000 0000
					if (i > 2 && i < 7) {
						rtnStr.append("*");
					} else {
						rtnStr.append(charPhone[i]);
					}
				} else { // 000 000 0000
					if (i > 2 && i < 6) {
						rtnStr.append("*");
					} else {
						rtnStr.append(charPhone[i]);
					}
				}
			}
		}

		return rtnStr.toString();
	}

	/**
	 * 핀번호 형태를 마스킹 처리한다.
	 *
	 * @param txt 암호화 하려하는 문자열
	 * @return String
	 * @throws Exception
	 */
	public static String convPinFormatMask(String pinNo) {

		if (pinNo == null)
			return "";
		if (pinNo.length() <= 6)
			return pinNo;

		StringBuffer rtnVal = new StringBuffer();
		try {
			rtnVal.append(pinNo.substring(0, 4));
			rtnVal.append("****");
			rtnVal.append(pinNo.substring(8));

		} catch (Exception e) {
			logger.error(e.getMessage());
			return "";
		}

		return rtnVal.toString();
	}

	/**
	 * 이메일 형태를 마스킹 처리한다.
	 *
	 * @param txt 암호화 하려하는 문자열
	 * @return String
	 * @throws Exception
	 */
	public static String convMaskingEmail(String txt) throws Exception {
		StringBuffer rtnStr = new StringBuffer();
		String[] emailArr = txt.split("@");
		if (emailArr.length >= 2) {
			if (emailArr[0].length() > 2) {
				rtnStr = new StringBuffer(emailArr[0].substring(0, 2));
				for (int i = 2; i < emailArr[0].length(); i++)
					rtnStr.append("*");
			}
			rtnStr.append("@").append(emailArr[1]);
		}

		return rtnStr.toString();
	}

	/**
	 * 이메일 형태를 마스킹 처리한다.
	 * use***@test.com
	 *
	 * @param txt 암호화 하려하는 문자열
	 * @return String
	 * @throws Exception
	 */
	public static String convMaskingEmail02(String txt) throws Exception {
		StringBuffer rtnStr = new StringBuffer();
		String[] emailArr = txt.split("@");

		int cnt = emailArr[0].length() - 3;
		char[] emailChar = emailArr[0].toCharArray();

		for (int i = 0; i < emailChar.length; i++) {
			if (cnt <= i) {
				rtnStr.append("*");
			} else {
				rtnStr.append(emailChar[i]);
			}
		}

		rtnStr.append("@").append(emailArr[1]);
		return rtnStr.toString();

	}

	/**
	 * 이름 형태를 마스킹 처리한다.
	 *
	 * @param txt 암호화 하려하는 문자열
	 * @return String
	 * @throws Exception
	 */
	public static String convNameMask(String name) {

		if (name == null)
			return "";
		if (name.length() <= 1)
			return name;

		StringBuffer rtnVal = new StringBuffer();
		try {
			rtnVal.append(name.substring(0, 1));
			rtnVal.append("*");
			if (name.length() > 2)
				rtnVal.append(name.substring(2));

		} catch (Exception e) {
			logger.error(e.getMessage());
			return "";
		}

		return rtnVal.toString();
	}

	public static String convLoginIdMask(String id) {
		if (id == null)
			return "";
		if (id.length() <= 4)
			return id;

		StringBuffer rtnVal = new StringBuffer();
		try {
			rtnVal.append(id.substring(0, 4));
			for (int i = 2; i < id.length(); i++)
				rtnVal.append("*");

		} catch (Exception e) {
			logger.error(e.getMessage());
			return "";
		}
		return rtnVal.toString();
	}

	/**
	 * 리스트 형태를 마스킹 처리한다.
	 *
	 * @param String[] key = {"name", "email", "mobileNo"};
	 *            List<Map<String,Object>> list = new ArrayList<>();
	 * @return String
	 * @throws Exception
	 */

	public static void convMaskList(List<Map<String, Object>> list, String[] key) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			for (int x = 0; x < key.length; x++) {
				Map<String, Object> mObj = list.get(i);
				if ("name".equals(key[x])) {
					mObj.put(key[x], convNameMask(mObj.get(key[x]).toString()));
				}
				if ("email".equals(key[x])) {
					mObj.put(key[x], convMaskingEmail(mObj.get(key[x]).toString()));
				}
				if ("callbackNo".equals(key[x]) || "mobileNo".equals(key[x])) {
					mObj.put(key[x], convPhoneFormatMask(mObj.get(key[x]).toString()));
				}
			}
		}

	}

//	public static void main(String[] args) throws Exception {
//		String[] key = {"name", "email", "mobileNo"};
//		List<Map<String,Object>> list = new ArrayList<>();
//		Map<String,Object> map = new HashMap<>();
//		map.put("name", "임영진");
//		map.put("email", "seibin87@gmail.com");
//		map.put("mobileNo", "01098333060");
//		list.add(map);
//		map = new HashMap<>();
//		map.put("name", "홍길동");
//		map.put("email", "aaaaaaaaaa@naver.com");
//		map.put("mobileNo", "15885588");
//		list.add(map);
//		convMaskList(list, key);
//	}

}
