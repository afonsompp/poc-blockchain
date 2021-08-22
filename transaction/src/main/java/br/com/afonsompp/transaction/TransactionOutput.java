package br.com.afonsompp.transaction;

import br.com.afonsompp.blockchain.utils.StringUtils;

import java.security.PublicKey;

public class TransactionOutput {

	private String id;
	private PublicKey reciepient;
	private Double value;
	private String parentTransactionId;

	public TransactionOutput(PublicKey reciepient, Double value, String parentTransactionId) {
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		this.id = StringUtils.applySHA256(StringUtils.getStringFromKey(reciepient)
										  + value + parentTransactionId);
	}

	public String getId() {
		return id;
	}

	public PublicKey getReciepient() {
		return reciepient;
	}

	public Double getValue() {
		return value;
	}

	public String getParentTransactionId() {
		return parentTransactionId;
	}

	public boolean isMine(PublicKey publicKey) {
		return (publicKey == reciepient);
	}
}
