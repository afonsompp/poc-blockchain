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

		var genesisTransaction = new Transaction(coinbase.getPublicKey(), walletA.getPublicKey(), 100d, null);
		genesisTransaction.setId("0");
		genesisTransaction.generateSignature(coinbase.getPrivateKey());
		genesisTransaction.getOutputs()
			.add(new TransactionOutput(genesisTransaction.getRecipient(), genesisTransaction.getValue(),
				genesisTransaction.getId()));
		Transactions.getUTXOs().put(genesisTransaction.getOutputs().get(0).getId(),
			genesisTransaction.getOutputs().get(0));

		System.out.println("Creating and Mining Genesis block... ");
		Blockchain.addBlock(genesisTransaction);

		Blockchain.addBlock(walletA.sendFunds(walletB.getPublicKey(), 40d));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Blockchain.addBlock(walletA.sendFunds(walletB.getPublicKey(), 20d));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Blockchain.addBlock(walletA.sendFunds(walletB.getPublicKey(), 2d));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

	}
}
