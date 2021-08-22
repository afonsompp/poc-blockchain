package br.com.afonsompp.transaction;

import br.com.afonsompp.blockchain.utils.StringUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

	private static Integer sequence = 0;
	private String id;
	private PublicKey sender;
	private PublicKey reciepient;
	private Double value;
	private byte[] signature;
	private List<TransactionInput> inputs = new ArrayList<>();
	private List<TransactionOutput> outputs = new ArrayList<>();

	public Transaction(PublicKey from, PublicKey to, Double value, List<TransactionInput> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
	}

	private String calculateHash() {
		sequence++;
		return StringUtils.applySHA256(
			StringUtils.getStringFromKey(sender) +
			StringUtils.getStringFromKey(reciepient) +
			value + sequence
		);
	}

	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(reciepient)
					  + value;
		signature = StringUtils.applyECDSASig(privateKey, data);
	}

	public boolean verifySignature() {
		String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(reciepient)
					  + value;
		return StringUtils.verifyECDSASig(sender, data, signature);
	}
}
