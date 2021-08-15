package br.com.afonsompp;

import br.com.afonsompp.blockchain.Block;

public class App {
	public static void main(String[] args) {
		Block genesisBlock = new Block("0", "First block");

		System.out.println(genesisBlock.getHash());
	}
}
