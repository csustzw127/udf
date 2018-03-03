/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.csii.loan.backfile;

import java.math.BigDecimal;

public class Acctgrp {
	private BigDecimal balpct;
	private BigDecimal partamt;
	private String acctnbr;
	private String acctgrptypcd;
	private String relacctnbr;
	private String ownyn;

	public BigDecimal getBalpct() {
		return this.balpct;
	}

	public String getOwnyn() {
		return this.ownyn;
	}

	public void setOwnyn(String ownyn) {
		this.ownyn = ownyn;
	}

	public void setBalpct(BigDecimal balpct) {
		this.balpct = balpct;
	}

	public BigDecimal getPartamt() {
		return this.partamt;
	}

	public void setPartamt(BigDecimal partamt) {
		this.partamt = partamt;
	}

	public String getAcctnbr() {
		return this.acctnbr;
	}

	public void setAcctnbr(String acctnbr) {
		this.acctnbr = acctnbr;
	}

	public String getAcctgrptypcd() {
		return this.acctgrptypcd;
	}

	public void setAcctgrptypcd(String acctgrptypcd) {
		this.acctgrptypcd = acctgrptypcd;
	}

	public String getRelacctnbr() {
		return this.relacctnbr;
	}

	public void setRelacctnbr(String relacctnbr) {
		this.relacctnbr = relacctnbr;
	}
}