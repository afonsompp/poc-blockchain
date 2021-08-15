package br.com.afonsompp.blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
	private static List<Block> blockchain = new ArrayList<>();

	public static void addBlock(Block block) {
		blockchain.add(block);
	}
}
