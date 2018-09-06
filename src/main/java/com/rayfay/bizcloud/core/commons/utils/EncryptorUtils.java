package com.rayfay.bizcloud.core.commons.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * Created by shenfu on 2017/7/18.
 */
public class EncryptorUtils {

	private static final String ENCRYPTOR_KEY = ">:E!@refRG%@4Gr1;=pf";
	private static final StandardPBEStringEncryptor encryptor;

	static {
		encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(ENCRYPTOR_KEY);
	}

	public static String getEncrypt(String message) {
		return encryptor.encrypt(message);
	}

	public static String decrypt(String encryptedMessage) {
		return encryptor.decrypt(encryptedMessage);
	}
}
