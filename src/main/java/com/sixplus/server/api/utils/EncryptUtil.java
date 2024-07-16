package com.sixplus.server.api.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.text.ParseException;
import java.util.Arrays;

/**
 * ===========================================
 *
 * @ProgramName : EncryptUtil
 * @Author : cwcho
 * @Version : 1.0
 * @Dscription :
 *             ===========================================
 */
@SuppressWarnings("deprecation")
public class EncryptUtil extends DigestUtils {
	public static String defualtSaltKey = "uvmssaltkeysvc12";

	public static String encData(String str) throws NoSuchAlgorithmException,
			GeneralSecurityException, UnsupportedEncodingException {
		return EncryptUtil.encData(str, EncryptUtil.defualtSaltKey);
	}

	public static String decData(String str) throws NoSuchAlgorithmException,
			GeneralSecurityException, UnsupportedEncodingException, ParseException {
		return EncryptUtil.decData(str, EncryptUtil.defualtSaltKey);
	}

	/**
	 * AES256 으로 암호화 한다.
	 * 키값의 길이가 16이하 Check
	 *
	 * @param str
	 *            암호화할 문자열
	 * @return
	 *         Json 암호화
	 * @throws NoSuchAlgorithmException
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 */
	public static String encData(String str, String key16) throws NoSuchAlgorithmException,
			GeneralSecurityException, UnsupportedEncodingException {

		String iv = key16.substring(0, 16);
		byte[] keyBytes = new byte[16];
		byte[] b = key16.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}

		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(Base64.encodeBase64(encrypted));
		return enStr;
	}

	/**
	 * AES256으로 암호화된 txt를 복호화한다.
	 *
	 * @param str
	 *            복호화할 문자열
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	public static String decData(String str, String key16) throws NoSuchAlgorithmException,
			GeneralSecurityException, UnsupportedEncodingException, ParseException {
		String iv = key16.substring(0, 16);
		byte[] keyBytes = new byte[16];
		byte[] b = key16.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}

		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
		byte[] byteStr = Base64.decodeBase64(str.getBytes());

		String decryptedStr = new String(c.doFinal(byteStr), "UTF-8");

		return decryptedStr;
	}

	/*
	public static void main(String[] args) {
		System.out.println(decryptAES256("U2FsdGVkX1+iX5Ey7GqLND5UFUoV0b7rUJ2eEvHkYqA=", "Secret Passphrase"));
	}
	*/

	/**
	 *
	 * @methodName : decryptAES256
	 * @author : cwcho
	 * @param ciphertext
	 * @param passphrase
	 * @return
	 * @return : String
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @description : AES256방식 복호화
	 */
	public static String decryptAES256(String ciphertext, String passphrase)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		final int keySize = 256;
		final int ivSize = 128;

		// 텍스트를 BASE64 형식으로 디코드 한다.
		byte[] ctBytes = Base64.decodeBase64(ciphertext.getBytes("UTF-8"));

		// 솔트를 구한다. (생략된 8비트는 Salted__ 시작되는 문자열이다.)
		byte[] saltBytes = Arrays.copyOfRange(ctBytes, 8, 16);
		// System.out.println(Hex.encodeHexString(saltBytes));

		// 암호화된 테스트를 구한다.( 솔트값 이후가 암호화된 텍스트 값이다.)
		byte[] ciphertextBytes = Arrays.copyOfRange(ctBytes, 16, ctBytes.length);

		// 비밀번호와 솔트에서 키와 IV값을 가져온다.
		byte[] key = new byte[keySize / 8];
		byte[] iv = new byte[ivSize / 8];
		EvpKDF(passphrase.getBytes("UTF-8"), keySize, ivSize, saltBytes, key, iv);

		// 복호화
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
		byte[] recoveredPlaintextBytes = cipher.doFinal(ciphertextBytes);

		return new String(recoveredPlaintextBytes);
	}

	private static byte[] EvpKDF(byte[] password, int keySize, int ivSize, byte[] salt, byte[] resultKey, byte[] resultIv)
			throws NoSuchAlgorithmException {
		return EvpKDF(password, keySize, ivSize, salt, 1, "MD5", resultKey, resultIv);
	}

	private static byte[] EvpKDF(byte[] password,
			int keySize,
			int ivSize,
			byte[] salt,
			int iterations,
			String hashAlgorithm,
			byte[] resultKey,
			byte[] resultIv) throws NoSuchAlgorithmException {
		keySize = keySize / 32;
		ivSize = ivSize / 32;
		int targetKeySize = keySize + ivSize;
		byte[] derivedBytes = new byte[targetKeySize * 4];
		int numberOfDerivedWords = 0;
		byte[] block = null;
		MessageDigest hasher = MessageDigest.getInstance(hashAlgorithm);
		while (numberOfDerivedWords < targetKeySize) {
			if (block != null) {
				hasher.update(block);
			}
			hasher.update(password);
			// Salting
			block = hasher.digest(salt);
			hasher.reset();
			// Iterations : 키 스트레칭(key stretching)
			for (int i = 1; i < iterations; i++) {
				block = hasher.digest(block);
				hasher.reset();
			}
			System.arraycopy(block, 0, derivedBytes, numberOfDerivedWords * 4, Math.min(block.length, (targetKeySize - numberOfDerivedWords) * 4));
			numberOfDerivedWords += block.length / 4;
		}
		System.arraycopy(derivedBytes, 0, resultKey, 0, keySize * 4);
		System.arraycopy(derivedBytes, keySize * 4, resultIv, 0, ivSize * 4);
		return derivedBytes; // key + iv
	}

	public static String encryptAES256(String plaintext, String passphrase) {
		try {
			final int keySize = 256;
			final int ivSize = 128;

			byte[] key = new byte[keySize / 8];
			byte[] iv = new byte[ivSize / 8];

			byte[] saltBytes = new byte[8];
			SecureRandom.getInstanceStrong().nextBytes(saltBytes);

			EvpKDF(passphrase.getBytes("UTF-8"), keySize, ivSize, saltBytes, key, iv);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
			byte[] cipherBytes = cipher.doFinal(plaintext.getBytes("UTF-8"));

			byte[] sBytes = "Salted__".getBytes("UTF-8");
			byte[] b = new byte[sBytes.length + saltBytes.length + cipherBytes.length];
			System.arraycopy(sBytes, 0, b, 0, sBytes.length);
			System.arraycopy(saltBytes, 0, b, sBytes.length, saltBytes.length);
			System.arraycopy(cipherBytes, 0, b, sBytes.length + saltBytes.length, cipherBytes.length);

			byte[] base64b = Base64.encodeBase64(b);

			return new String(base64b);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}