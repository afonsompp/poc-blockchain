package br.com.afonsompp.blockchain;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import com.google.common.hash.Hashing;

public class Block {
	private String hash;
	private String previusHash;
	private String data;
	private Long timestamp;

	public Block(String previusHash, String data) {
		this.previusHash = previusHash;
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

	public String getPreviusHash() {
		return this.previusHash;
	}

	public void setPreviusHash(String previusHash) {
		this.previusHash = previusHash;
	}

	public String getData() {
		return this.data;
	}

	public Long getTimestamp() {
		return this.timestamp;
	}

	public String calculateHash() {

		return Hashing.sha256()
				.hashString(previusHash + timestamp.toString() + data, StandardCharsets.UTF_8)
				.toString();
	}

}
