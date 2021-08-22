package br.com.afonsompp;

import br.com.afonsompp.blockchain.utils.StringUtils;
import br.com.afonsompp.transaction.Transaction;
import br.com.afonsompp.transaction.Wallet;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class App {

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());

		var walletA = new Wallet();
		var walletB = new Wallet();

		System.out.println("Private and public keys:");
		System.out.println(StringUtils.getStringFromKey(walletA.getPublicKey()));
		System.out.println(StringUtils.getStringFromKey(walletA.getPrivateKey()));

		Transaction transaction = new Transaction(walletA.getPublicKey(), walletB.getPublicKey(), 5D,
			null);
		transaction.generateSignature(walletA.getPrivateKey());

		System.out.println("Is signature verified");
		System.out.println(transaction.verifySignature());
	}
}
