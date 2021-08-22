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
	private List<TransactionInput> inputs;
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

	public Boolean process() {
		// Verify if signature is valid
		if (!verifySignature())
			return false;

		// get transactions to be processed
		for (var input : inputs)
			input.setUTXO(Transactions.getUTXOs().get(input.getTransactionOutputId()));

		// Verify if transaction is valid
		if (getInputsValue() < Transactions.getMinimumTransaction())
			return false;

		// generate transaction outputs
		// get value of inputs then the leftover change
		var leftOver = getInputsValue() - value;
		id = calculateHash();
		// send value to recipient
		outputs.add(new TransactionOutput(this.reciepient, value, id));
		// send the left over 'change' back to sender
		outputs.add(new TransactionOutput(this.sender, leftOver, id));

		// add outputs to unspent list
		for (TransactionOutput output : outputs)
			Transactions.getUTXOs().put(output.getId(), output);

		for (TransactionInput input : inputs)
			if (input.getUTXO() != null)
				Transactions.getUTXOs().remove(input.getUTXO().getId());

		return true;
	}

	private Double getInputsValue() {
		Double total = 0D;
		for (var input : inputs) {
			if (input.getUTXO() == null)
				continue;
			total += input.getUTXO().getValue();
		}
		return total;
	}

	private Double getOutputsValue() {
		Double total = 0D;
		for (var output : outputs)
			total += output.getValue();
		return total;
	}
}
