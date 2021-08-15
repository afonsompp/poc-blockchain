package br.com.afonsompp;

import br.com.afonsompp.blockchain.Block;
import br.com.afonsompp.blockchain.Blockchain;

public class App {
	public static void main(String[] args) {
		Block genesisBlock = new Block("0", "First block");
		Blockchain.addBlock(genesisBlock);
		Blockchain.addBlock(new Block(genesisBlock.getHash(), "Second block"));
		System.out.println(Blockchain.isChainValid());
	}
}
