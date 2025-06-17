package com.company.Model;

import sun.security.provider.DSAPublicKeyImpl;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Block implements Serializable {

	private byte[] prevHash;
	private byte[] currHash;
	private String timeStamp;
	private byte[] minedBy;
	private Integer ledgerId = 1;
	private Integer miningPoints = 0;
	private Double luck = 0.0;

	private ArrayList<Transaction> transactionLedger = new ArrayList<>();

	// This constructor is used when we retrieve it from the db
	public Block(byte[] prevHash, byte[] currHash, String timeStamp, byte[] minedBy, Integer ledgerId, Integer miningPoints, Double luck, ArrayList<Transaction> transactionLedger) {
		this.prevHash = prevHash;
		this.currHash = currHash;
		this.timeStamp = timeStamp;
		this.minedBy = minedBy;
		this.ledgerId = ledgerId;
		this.transactionLedger = transactionLedger;
		this.miningPoints = miningPoints;
		this.luck = luck;
	}
	// This constructor is used when we initiate it after retrieve.
	public Block(LinkedList<Block> currentBlockChain) {
		Block lastBlock = currentBlockChain.getLast();
		prevHash = lastBlock.getCurrHash();
		ledgerId = lastBlock.getLedgerId() + 1;
		luck = Math.random() * 1000000;
	}
	// This constructor is used only for creating the first block in the blockchain
	public Block() {
		prevHash = new byte[]{0};
	}

	public Boolean isVerified(Signature signing) throws InvalidKeyException, SignatureException {
		signing.initVerify(new DSAPublicKeyImpl(this.minedBy));
		signing.update(this.toString().getBytes());
		return signing.verify(this.currHash);
	}
}
