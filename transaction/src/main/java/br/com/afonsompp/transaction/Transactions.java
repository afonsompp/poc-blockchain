package br.com.afonsompp.transaction;

import java.util.HashMap;
import java.util.Map;

public class Transactions {

	private static final Map<String, TransactionOutput> UTXOs = new HashMap<>();
	private static final Double minimumTransaction = 0.1D;

	public static Map<String, TransactionOutput> getUTXOs() {
		return UTXOs;
	}

	public static Double getMinimumTransaction() {
		return minimumTransaction;
	}
}
