package com.sillycat.authcenter.common.jwt;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTJavaWithPublicPrivateKey {

	public static String privateKeyString = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALLl3YRnsI8ZMlBF9F/naLo7KjZP6VRwU+KtqDyRwHBUNn2PUhU1RaoODiLrWcFJ7awF6LzNZ3K5rfCrrt31LJ6pEgMt1g+09ShlFoycU9DGk2URBDc3ZWiGm3iR19UyFzpjVuGfbQvsr3rYfvpHKsnamk3WEu3ZGzm64bY905JtAgMBAAECgYAuF2UkOObXZ4F8Bxn4H5Hu8VFl3t7Z33rtWxqOSGsRRdEoNmXKiwgg0TA5NPPSBe8TNA6Lnkw51bcH2+PY0dMlu0CpsomEFOMZo016jf+rMmldXZiRccSdNrmMrSU1HXBQGgcIZ09BvGjV1Xcvukqu4hcw2Cx2tR1arfz8LhJMwQJBAN8qKn9Fnc1huDIR6U0043nWLioMTp/l0M+CyYPe69A7FuS+vyF83ZSYDh50bByPGpIpBimTP8/der0/M7RDshECQQDNOFLlEPGG61nn7Ah9KZzeDW/NsgOS7xb5iYYfm1Tlot2r0ZMe5Yl7+EePxV76GcOeKfJXC2TQIJgFU6NI+36dAkEAtrG6YL8JVN4vAS6QpFgr8c5ZtKqmo1hs/bTAbGjO/IWjVFij+DJU5BUnWd9NsoOk6QsUtGyLzQwwM0XOekEBQQJAYPWflMKwmsJPtBf82sXya6eFj3Xv4lg8TqH/UKefMPAGM8vM6uggUQY5KWBjQ18w4WWILkAf3YXIzZt6plzMsQJBAK3EmpqWbr6uzMUKG9NKdpPHpbjSFpY1IZ5pe9HBcJloEAdqJTx3uvdyLHYwBOfkZrpIA5glBjpgeEuOgJMOixE=";

	public static String publicKeyString ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCy5d2EZ7CPGTJQRfRf52i6Oyo2T+lUcFPirag8kcBwVDZ9j1IVNUWqDg4i61nBSe2sBei8zWdyua3wq67d9SyeqRIDLdYPtPUoZRaMnFPQxpNlEQQ3N2Vohpt4kdfVMhc6Y1bhn20L7K962H76RyrJ2ppN1hLt2Rs5uuG2PdOSbQIDAQAB";

	public static void main(String[] args) throws Exception {

		PrivateKey privateKey = RSACoder.convertPrivateKey(privateKeyString);

		PublicKey publicKey = RSACoder.convertPublicKey(publicKeyString);
		
		String token = generateToken(privateKey);
		//String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJleGFtcGxlLm9yZyIsImF1ZCI6ImV4YW1wbGUuY29tIiwiaWF0IjoxMzU2OTk5NTI0LCJuYmYiOjEzNTcwMDAwMDB9.acVlIL5dWDJbWotfoSkyJVMgRhe2K_w2_VHz0uqghiCayMufqwxTzrYjLVG2lLsIgdvapUePwAXluuH6biY8mCUhD8_Ly6svvH_vAehg-qrT-TzBJVuBsn_Z7g23Lh2s8A7rxyXve4ab-GhlWKh5pTuXHiPaTUdzUeBH6WqhGWs";
		System.out.println("Generated Token:\n" + token);
		System.out.println("----------------");
		verifyToken(token, publicKey);
	}

	public static String generateToken(PrivateKey privateKey) {
		String token = null;
		try {
			Map<String, Object> claims = new HashMap<String, Object>();

			// put your information into claim
			claims.put("id", "yiyikang");
			claims.put("email", "yiyikangrachel@gmail.com");
			claims.put("role", "user");
			claims.put("created", new Date());

			token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.RS512, privateKey).compact();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	private static Claims verifyToken(String token, PublicKey publicKey) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();

			System.out.println("Verify token content:");
			System.out.println(claims.get("id"));
			System.out.println(claims.get("role"));
			System.out.println(claims.get("created"));

		} catch (Exception e) {

			claims = null;
		}
		return claims;
	}
}
