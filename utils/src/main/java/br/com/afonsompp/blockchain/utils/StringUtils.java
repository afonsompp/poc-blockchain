package br.com.afonsompp.blockchain.utils;

import com.google.common.hash.Hashing;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class StringUtils {

	private StringUtils() {}

	public static String applySHA256(String value) {
		return Hashing.sha256().hashString(value, StandardCharsets.UTF_8).toString();
	}

	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public static String toJsonString(Object object) {
		var gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(object);
	}

	public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
		try {
			var dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			dsa.update(input.getBytes());
			return dsa.sign();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			var ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
