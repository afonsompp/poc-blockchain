package br.com.afonsompp;

import br.com.afonsompp.blockchain.Blockchain;
import br.com.afonsompp.blockchain.utils.StringUtils;

public class App {

	public static void main(String[] args) {

		Blockchain.addBlock("firstBlock");
		Blockchain.addBlock("secondBlock");

		// Demonstration of how work
		for (var block : Blockchain.getBlockchain()) {
			System.out.println("Try mining block");
			block.mineBlock(5);
			System.out.println(StringUtils.toJsonString(block));
			System.out.println("Block mined");
			System.out.println("----------------");
		}

		System.out.println(Blockchain.isChainValid());
	}
}
