/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.csii.loan.backfile;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AmortizationScheduleItem {
	public Date startDate;
	public Date dueDate;
	public BigDecimal principalAmount;
	public BigDecimal interestAmount;
	public BigDecimal startingBalance;
	public BigDecimal endingBalance;
	public BigDecimal chargeAmount;
	public Long currTerm;
	public BigDecimal prinOutStanding;
	public BigDecimal intOutStanding;
	public String termStatCD;
	public BigDecimal odpIntAmount;
	public BigDecimal odiIntAmount;

	public AmortizationScheduleItem() {
		this.principalAmount = BigDecimal.ZERO;
		this.interestAmount = BigDecimal.ZERO;
		this.chargeAmount = BigDecimal.ZERO;
		this.prinOutStanding = BigDecimal.ZERO;
		this.intOutStanding = BigDecimal.ZERO;
		this.odpIntAmount = BigDecimal.ZERO;
		this.odiIntAmount = BigDecimal.ZERO;
	}

	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return "{Due Date: " + sdf.format(this.dueDate) + ", Starting Balance: " + this.startingBalance
				+ ", Prncipal Amount: " + this.principalAmount + ",PrinOutStanding Amount" + this.prinOutStanding
				+ ", Interest Amount: " + this.interestAmount + ",IntOutStanding Amount" + this.intOutStanding
				+ ", Ending Balance: " + this.endingBalance + ",TermStatCD" + this.termStatCD + "}";
	}
}