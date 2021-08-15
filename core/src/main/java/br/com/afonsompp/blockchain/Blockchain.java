package br.com.afonsompp.blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
	private static List<Block> blockchain = new ArrayList<>();

	public static void addBlock(Block block) {
		blockchain.add(block);
	}

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;

		if (blockchain.size() <= 1)
			return true;

		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);

			if (!currentBlock.getPreviusHash().equals(previousBlock.getHash()))
				return false;

		}

		return true;
	}
}
