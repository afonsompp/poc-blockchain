package br.com.afonsompp.blockchain;

import br.com.afonsompp.blockchain.utils.StringUtils;
import br.com.afonsompp.transaction.Transaction;
import br.com.afonsompp.transaction.TransactionInput;
import br.com.afonsompp.transaction.TransactionOutput;

import java.util.*;

public class Blockchain {

	private static List<Block> blockchain = new ArrayList<>();
	private static Integer difficulty = 7;
	private static Transaction genesisTransaction;

	private Blockchain() {}

	public static List<Block> getBlockchain() {
		return blockchain;
	}

	public static void addBlock(Transaction transaction) {
		if (blockchain.isEmpty()) {
			genesisTransaction = transaction;
			var block = new Block("0");
			block.addTransaction(transaction);
			block.mineBlock(difficulty);
			blockchain.add(block);

			System.out.println(StringUtils.toJsonString(block));
			return;
		}
		var previousBlock = blockchain.get(blockchain.size() - 1);
		var block = new Block(previousBlock.getHash());
		block.addTransaction(transaction);
		previousBlock.mineBlock(difficulty);
		blockchain.add(block);
		System.out.println(StringUtils.toJsonString(block));
	}

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		Map<String, TransactionOutput> tempUTXOs = new HashMap<>();
		tempUTXOs.put(genesisTransaction.getOutputs().get(0).getId(),
			genesisTransaction.getOutputs().get(0));

		if (blockchain.size() <= 1)
			return true;

		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);

			if (!currentBlock.getPreviousHash().equals(previousBlock.getHash()))
				return false;

			TransactionOutput tempOutput;
			for (int t = 0; t < currentBlock.getTransactions().size(); t++) {
				Transaction currentTransaction = currentBlock.getTransactions().get(t);

				if (!currentTransaction.verifySignature()) {
					return false;
				}
				if (!currentTransaction.getInputsValue().equals(currentTransaction.getOutputsValue())) {
					return false;
				}

				for (TransactionInput input : currentTransaction.getInputs()) {
					tempOutput = tempUTXOs.get(input.getTransactionOutputId());

					if (tempOutput == null) {
						return false;
					}

					if (!Objects.equals(input.getUTXO().getValue(), tempOutput.getValue())) {
						return false;
					}

					tempUTXOs.remove(input.getTransactionOutputId());
				}

				for (TransactionOutput output : currentTransaction.getOutputs()) {
					tempUTXOs.put(output.getId(), output);
				}

				if (currentTransaction.getOutputs().get(0).getReciepient()
					!= currentTransaction.getReciepient()) {
					System.out.println(
						"#Transaction(" + t + ") output reciepient is not who it should be");
					return false;
				}
				if (currentTransaction.getOutputs().get(1).getReciepient()
					!= currentTransaction.getSender()) {
					System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
					return false;
				}

			}
		}
		return true;
	}
}
