package br.com.afonsompp.transaction;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {

	private PublicKey publicKey;
	private PrivateKey privateKey;

	public Wallet() {
		generateKeyPair();
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			var ecSpec = new ECGenParameterSpec("prime192v1");

			keyGen.initialize(ecSpec, random);
			KeyPair keyPair = keyGen.generateKeyPair();
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
