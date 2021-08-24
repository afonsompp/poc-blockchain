package br.com.afonsompp.transaction;

import br.com.afonsompp.blockchain.utils.StringUtils;

import java.security.PublicKey;

public class TransactionOutput {

	private final String id;
	private final PublicKey recipient;
	private final Double value;

	public TransactionOutput(PublicKey recipient, Double value, String parentTransactionId) {
		this.recipient = recipient;
		this.value = value;
		this.id = StringUtils.applySHA256(StringUtils.getStringFromKey(recipient)
										  + value + parentTransactionId);
	}

	public String getId() {
		return id;
	}

	public PublicKey getRecipient() {
		return recipient;
	}

	public Double getValue() {
		return value;
	}

	public boolean isMine(PublicKey publicKey) {
		return (publicKey == recipient);
	}
}
