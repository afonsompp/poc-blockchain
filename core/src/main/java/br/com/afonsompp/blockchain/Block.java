package br.com.afonsompp.blockchain;

import br.com.afonsompp.blockchain.utils.StringUtils;

import java.time.Instant;

public class Block {

	private String hash;
	private String previousHash;
	private String data;
	private Long timestamp;
	private Integer nonce = 0;

	public Block(String previusHash, String data) {
		this.previousHash = previusHash;
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

	public void setPreviusHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String getData() {
		return this.data;
	}

	public Long getTimestamp() {
		return this.timestamp;
	}

	public String calculateHash() {
		return StringUtils.applySHA256(previousHash + timestamp.toString() + nonce.toString() + data);
	}

	public void mineBlock(Integer difficulty) {
		String target = "0".repeat(difficulty);

		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();

		}
	}

}
