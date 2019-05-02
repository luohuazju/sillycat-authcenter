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

	public static String privateKeyString = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJBCDhpmfCNwexsE53RYaRo7RFmbVzIRZb6WQzwcbuaSDRqRbkUE6P/arWIBxajesrRjjZM9YKpP95Ds/+vBjtF3suK7x4+i1DiAMxV9FI+H3oPvA/XnnemTmydnC6Fub9DTgrAXjho+drYVw+fhUulpEQjjtL3+9ufZcfPngEatAgMBAAECgYBZJAg09hlFiO75Tjl74lnn9LGSsXPbI3b8ozuevG8nGR7xyOYbziHwX/99rVynsh05bL8COg4uj8WKD3lBBFUsWR3I/5L5NAuGyvXaRjch1HzLXZTA7UCGknigfUHpKzQ2aN64jyLPJXR0M8v6hMADEOq62ZU/bM9NpQ7mLA/aAQJBANwJ5asKPA1typOqSnbm++lgzS41CI7GbXOUbzA9nccYLra/gS8KbQhSqkoknyS8/9aLePzLxJEh/IHJ5rXya4ECQQCn1ZrKLf0oEn5+6O9CHzBPumT7lZ6zpyVTQQqlh2HHiPshbUWdp0If1bzwohhd2VG5oKap4DM5yfR8vbHa5+EtAkEAx9LFG3sD5j4ahddUIH7LrrkWHCxVRTO2GgxmnSyXqEKxyYxISOvy8GgB9askSzzIKZInoYt7S0HOCh/MwNcSAQJANh9N8VDvSFLec/KN5VbFb5B+LBtNuEV4Zeadlk+YGo5z3Y2tW2+qhTWZ3keHcAw6rI+/7xwbqNEVhHxvtFX+aQI/D/Ork0M4oRF7YqidzQGJilaJXaAui31xV7wjlwMKahlfjcW3ZaVKX9/OMUOVGO347P8ktbYc4qv6ZgUpZPAH";

	public static String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQQg4aZnwjcHsbBOd0WGkaO0RZm1cyEWW+lkM8HG7mkg0akW5FBOj/2q1iAcWo3rK0Y42TPWCqT/eQ7P/rwY7Rd7Liu8ePotQ4gDMVfRSPh96D7wP1553pk5snZwuhbm/Q04KwF44aPna2FcPn4VLpaREI47S9/vbn2XHz54BGrQIDAQAB";

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
