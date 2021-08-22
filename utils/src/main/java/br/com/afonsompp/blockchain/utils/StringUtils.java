package br.com.afonsompp.blockchain.utils;

import br.com.afonsompp.transaction.Transaction;
import com.google.common.hash.Hashing;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

	public static String getMerkleRoot(List<Transaction> transactions) {
		int count = transactions.size();
		List<String> previousTreeLayer = new ArrayList<>();
		for (Transaction transaction : transactions) {
			previousTreeLayer.add(transaction.getId());
		}
		List<String> treeLayer = previousTreeLayer;
		while (count > 1) {
			treeLayer = new ArrayList<>();
			for (int i = 1; i < previousTreeLayer.size(); i++) {
				treeLayer.add(
					StringUtils.applySHA256(previousTreeLayer.get(i - 1) + previousTreeLayer.get(i)));
			}
			count = treeLayer.size();
			previousTreeLayer = treeLayer;
		}
		return (treeLayer.size() == 1) ? treeLayer.get(0) : "";
	}

	public static String getDifficultyString(int difficulty) {
		return "0".repeat(difficulty);
	}
}
