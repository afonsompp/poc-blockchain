package br.com.afonsompp;

import com.google.gson.GsonBuilder;
import br.com.afonsompp.blockchain.Blockchain;

public class App {

	public static void main(String[] args) {
		var gson = new GsonBuilder().setPrettyPrinting().create();
		Blockchain.addBlock("firstBlock");
		Blockchain.addBlock("secondBlock");

		// Demonstration of how work
		for (var block : Blockchain.getBlockchain()) {
			System.out.println("Try mining block");
			block.mineBlock(5);
			System.out.println(gson.toJson(block));
			System.out.println("Block mined");
			System.out.println("----------------");
		}

		System.out.println(Blockchain.isChainValid());
	}
}
