package br.com.afonsompp.blockchain.utils;

import com.google.common.hash.Hashing;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.security.Key;
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

}
