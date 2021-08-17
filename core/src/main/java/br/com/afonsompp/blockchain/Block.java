package br.com.afonsompp.blockchain;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

import com.google.common.hash.Hashing;

public class Block {

	private String hash;
	private String previousHash;
	private String data;
	private Long timestamp;
	private Integer nonce = 0;

	public Block(String previousHash, String data) {
		this.previousHash = previousHash;
		this.data = data;
		this.timestamp = Instant.now().toEpochMilli();
		this.hash = calculateHash();
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return this.previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String getData() {
		return this.data;
	}

	public Long getTimestamp() {
		return this.timestamp;
	}

	public String calculateHash() {

		return Hashing.sha256()
			.hashString(previousHash + timestamp.toString() + nonce.toString() + data,
				StandardCharsets.UTF_8)
			.toString();
	}

	public void mineBlock(Integer difficulty) {
		String target = "0".repeat(difficulty);

		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();

		}
	}

}
