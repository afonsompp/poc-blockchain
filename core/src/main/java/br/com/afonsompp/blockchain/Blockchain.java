package br.com.afonsompp.blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
	private static List<Block> blockchain = new ArrayList<>();

	private Blockchain() {}

	public static List<Block> getBlockchain() {
		return blockchain;
	}

	public static void addBlock(String data) {
		if (blockchain.isEmpty()) {
			blockchain.add(new Block("0", data));
			return;
		}
		var previousHash = blockchain.get(blockchain.size() - 1).calculateHash();
		blockchain.add(new Block(previousHash, data));
	}

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;

		if (blockchain.size() <= 1)
			return true;

		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);

			if (!currentBlock.getPreviousHash().equals(previousBlock.getHash()))
				return false;

		}

		return true;
	}
}
