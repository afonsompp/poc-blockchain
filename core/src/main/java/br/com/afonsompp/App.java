package br.com.afonsompp;

import br.com.afonsompp.blockchain.Blockchain;

public class App {
	public static void main(String[] args) {
		Blockchain.addBlock("firstBlock");
		Blockchain.addBlock("secondBlock");

		for (var block : Blockchain.getBlockchain()) {
			System.out.println("Hash of block: " + block.getHash());
		}
	}
}
