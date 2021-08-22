package br.com.afonsompp;

import br.com.afonsompp.blockchain.Blockchain;
import br.com.afonsompp.transaction.Transaction;
import br.com.afonsompp.transaction.TransactionOutput;
import br.com.afonsompp.transaction.Transactions;
import br.com.afonsompp.transaction.Wallet;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class App {

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());

		var walletA = new Wallet();
		var walletB = new Wallet();
		var coinbase = new Wallet();

		var genTransac = new Transaction(coinbase.getPublicKey(), walletA.getPublicKey(), 100d, null);
		genTransac.setId("0");
		genTransac.generateSignature(coinbase.getPrivateKey());
		genTransac.getOutputs()
			.add(new TransactionOutput(genTransac.getReciepient(), genTransac.getValue(), genTransac.getId()));
		Transactions.getUTXOs().put(genTransac.getOutputs().get(0).getId(), genTransac.getOutputs().get(0));

		System.out.println("Creating and Mining Genesis block... ");
		Blockchain.addBlock(genTransac);

		Blockchain.addBlock(walletA.sendFunds(walletB.getPublicKey(), 40d));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Blockchain.addBlock(walletA.sendFunds(walletA.getPublicKey(), 20d));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Blockchain.addBlock(walletA.sendFunds(walletA.getPublicKey(), 2d));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

	}
}
