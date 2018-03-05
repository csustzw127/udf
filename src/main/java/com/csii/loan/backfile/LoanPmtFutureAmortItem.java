/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.csii.loan.backfile;

import java.math.BigDecimal;
import java.util.Date;

public class LoanPmtFutureAmortItem {
	public Date startDate;
	public Date dueDate;
	public Date graceDueDate;
	public BigDecimal principalAmt;
	public BigDecimal intRestAmt;
	public BigDecimal chargeAmt;
	public BigDecimal startBalAmt;
	public BigDecimal endBalAmt;
	public long currTerm;
	public String termStatCD;
	public BigDecimal prinOutStanding;
	public BigDecimal intOutStanding;
	public BigDecimal odpIntAmount;
	public BigDecimal odiIntAmount;

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getGraceDueDate() {
		return this.graceDueDate;
	}

	public void setGraceDueDate(Date graceDueDate) {
		this.graceDueDate = graceDueDate;
	}

	public BigDecimal getPrincipalAmt() {
		return this.principalAmt;
	}

	public void setPrincipalAmt(BigDecimal principalAmt) {
		this.principalAmt = principalAmt;
	}

	public BigDecimal getIntRestAmt() {
		return this.intRestAmt;
	}

	public void setIntRestAmt(BigDecimal intRestAmt) {
		this.intRestAmt = intRestAmt;
	}

	public BigDecimal getChargeAmt() {
		return this.chargeAmt;
	}

	public void setChargeAmt(BigDecimal chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public BigDecimal getStartBalAmt() {
		return this.startBalAmt;
	}

	public void setStartBalAmt(BigDecimal startBalAmt) {
		this.startBalAmt = startBalAmt;
	}

	public BigDecimal getEndBalAmt() {
		return this.endBalAmt;
	}

	public void setEndBalAmt(BigDecimal endBalAmt) {
		this.endBalAmt = endBalAmt;
	}

	public long getCurrTerm() {
		return this.currTerm;
	}

	public void setCurrTerm(long currTerm) {
		this.currTerm = currTerm;
	}

	public String getTermStatCD() {
		return this.termStatCD;
	}

	public void setTermStatCD(String termStatCD) {
		this.termStatCD = termStatCD;
	}

	public BigDecimal getPrinOutStanding() {
		return this.prinOutStanding;
	}

	public void setPrinOutStanding(BigDecimal prinOutStanding) {
		this.prinOutStanding = prinOutStanding;
	}

	public BigDecimal getIntOutStanding() {
		return this.intOutStanding;
	}

	public void setIntOutStanding(BigDecimal intOutStanding) {
		this.intOutStanding = intOutStanding;
	}

	public BigDecimal getOdpIntAmount() {
		return this.odpIntAmount;
	}

	public void setOdpIntAmount(BigDecimal odpIntAmount) {
		this.odpIntAmount = odpIntAmount;
	}

	public BigDecimal getOdiIntAmount() {
		return this.odiIntAmount;
	}

	public void setOdiIntAmount(BigDecimal odiIntAmount) {
		this.odiIntAmount = odiIntAmount;
	}

	public String toString() {
		return "LoanPmtFutureAmortItem [startDate=" + this.startDate + ", dueDate=" + this.dueDate + ", graceDueDate="
				+ this.graceDueDate + ", principalAmt=" + this.principalAmt + ", intRestAmt=" + this.intRestAmt
				+ ", chargeAmt=" + this.chargeAmt + ", startBalAmt=" + this.startBalAmt + ", endBalAmt="
				+ this.endBalAmt + ", currTerm=" + this.currTerm + ", termStatCD=" + this.termStatCD + "]";
	}
}