package br.com.afonsompp.transaction;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet {

	private final PrivateKey privateKey;
	private final PublicKey publicKey;

	public Map<String, TransactionOutput> UTXOs = new HashMap<>();

	public Wallet() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			keyGen.initialize(ecSpec, random);
			KeyPair keyPair = keyGen.generateKeyPair();
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public float getBalance() {
		float total = 0;
		for (var item : Transactions.getUTXOs().entrySet()) {
			var utxo = item.getValue();
			if (utxo.isMine(publicKey)) {
				UTXOs.put(utxo.getId(), utxo);
				total += utxo.getValue();
			}
		}
		return total;
	}

	public Transaction sendFunds(PublicKey recipient, Double value) {
		// gather balance and check funds
		if (getBalance() < value) {
			return null;
		}
		List<TransactionInput> inputs = new ArrayList<>();
		float total = 0;
		for (var item : UTXOs.entrySet()) {
			var utxo = item.getValue();
			total += utxo.getValue();
			inputs.add(new TransactionInput(utxo.getId()));
			if (total > value)
				break;
		}

		Transaction newTransaction = new Transaction(publicKey, recipient, value, inputs);
		newTransaction.generateSignature(privateKey);

		for (TransactionInput input : inputs) {
			UTXOs.remove(input.getTransactionOutputId());
		}
		return newTransaction;
	}
}
