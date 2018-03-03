/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.csii.loan.backfile;

import java.math.BigDecimal;

public class UnilInfo {
	private String acctNbr;
	private BigDecimal balPct;
	private BigDecimal occurrenceAmt;
	private String ownyn;

	public UnilInfo(String acctNbr, BigDecimal balPct) {
		this.acctNbr = acctNbr;
		this.balPct = balPct;
	}

	public String getAcctNbr() {
		return this.acctNbr;
	}

	public void setAcctNbr(String acctNbr) {
		this.acctNbr = acctNbr;
	}

	public BigDecimal getBalPct() {
		return this.balPct;
	}

	public void setBalPct(BigDecimal balPct) {
		this.balPct = balPct;
	}

	public BigDecimal getOccurrenceAmt() {
		return this.occurrenceAmt;
	}

	public void setOccurrenceAmt(BigDecimal occurrenceAmt) {
		this.occurrenceAmt = occurrenceAmt;
	}

	public String getOwnyn() {
		return this.ownyn;
	}

	public void setOwnyn(String ownyn) {
		this.ownyn = ownyn;
	}

	public String toString() {
		return "{acctNbr=" + this.acctNbr + ",balpct=" + this.balPct + ",occurrenceAmt" + this.occurrenceAmt + "}";
	}
}