package br.com.afonsompp.blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
	private static List<Block> blockchain = new ArrayList<>();

	private static Integer difficulty = 5;

	private Blockchain() {}

	public static List<Block> getBlockchain() {
		return blockchain;
	}

	public static void addBlock(String data) {
		if (blockchain.isEmpty()) {
			var block = new Block("0", data);
			block.mineBlock(difficulty);
			blockchain.add(block);
			return;
		}
		var previousBlock = blockchain.get(blockchain.size() - 1);
		previousBlock.mineBlock(difficulty);
		blockchain.add(new Block(previousBlock.getHash(), data));
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
