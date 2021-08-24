package br.com.afonsompp.blockchain;

import br.com.afonsompp.blockchain.utils.StringUtils;
import br.com.afonsompp.transaction.Transaction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Block {

	private final Long timestamp;
	private final List<Transaction> transactions = new ArrayList<>();
	private String hash;
	private String previousHash;
	private String merkleRoot;
	private Integer nonce = 0;

	public Block(String previousHash) {
		this.previousHash = previousHash;
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

	public Long getTimestamp() {
		return this.timestamp;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public String calculateHash() {
		return StringUtils.applySHA256(
			previousHash + timestamp.toString() + nonce.toString() + merkleRoot);
	}

	public void mineBlock(Integer difficulty) {
		merkleRoot = StringUtils.getMerkleRoot(transactions);
		String target = StringUtils.getDifficultyString(difficulty);

		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();

		}
	}

	public Boolean addTransaction(Transaction transaction) {
		if (transaction == null)
			return false;
		if (!Objects.equals(previousHash, "0") && !transaction.process()) {
			return false;
		}
		transactions.add(transaction);
		return true;
	}
}
