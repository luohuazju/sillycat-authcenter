package com.sillycat.authcenter.common.jwt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSACoder {

	public static final String KEY_ALGORITHM = "RSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	public static void main(String[] args) throws Exception {
		RSACoder coder = new RSACoder();
		Map<String, Object> keyMap = coder.initKey();
		String privateKeyString = coder.getPrivateKey(keyMap);
		String publicKeyString = coder.getPublicKey(keyMap);
		System.out.println("--------------------------------");
		System.out.println("privateKey:" + privateKeyString);
		System.out.println("");
		System.out.println("");
		System.out.println("publicKey:" + publicKeyString);
		System.out.println("--------------------------------");
	}

	public static PrivateKey convertPrivateKey(String privateKeyString) throws Exception {
		// decrypt the private key via base64
		byte[] keyBytes = decryptBASE64(privateKeyString);
		// construct PKCS8EncodedKeySpec object
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM RSA
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// convert to key object from private key string
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		return priKey;
	}

	public static PublicKey convertPublicKey(String publicKeyString) throws Exception {
		byte[] keyBytes = decryptBASE64(publicKeyString);
		// construct X509EncodedKeySpec object
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// convert to public key from public key string
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		return pubKey;
	}
	
	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.getDecoder().decode(key);
	}

	public String encryptBASE64(byte[] key) throws Exception {
		return Base64.getEncoder().encodeToString(key);
	}

	public String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return encryptBASE64(key.getEncoded());
	}

	public String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return encryptBASE64(key.getEncoded());
	}

	public Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// public key
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// private key
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

}
