/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.csii.loan.backfile;

import com.csii.loan.backfile.Acctgrp;
import com.csii.loan.backfile.AmortizationScheduleItem;
import com.csii.loan.backfile.CalPeriodEnum;
import com.csii.loan.backfile.DateWidget;
import com.csii.loan.backfile.LoanPmtFutureAmortItem;
import com.csii.loan.backfile.UnilInfo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AmortizationScheduleGenerator {
	private String acctnbr;
	private BigDecimal currentBalance;
	private BigDecimal outstandingPrincipal;
	private BigDecimal currentCharge;
	private BigDecimal outstandingCharge;
	private Date nextDueDate;
	private int acctrcvbsOnMaturityDate;
	private Date datemat;
	private Date disbursmentDate;
	private Integer pmtduedaynbr;
	private String pmtcalperiodcd;
	private Date intEffDate;
	private Date lastBilledDueDate;
	private BigDecimal intEffAmt;
	private BigDecimal outstandingInterest;
	private BigDecimal effIntRate;
	private Date interestFreeDate;
	private BigDecimal newintrate;
	private String daysmethcd;
	private int effIntBase;
	private BigDecimal interestMargin;
	private String noteBalPmtTypCd;
	private BigDecimal termPmtAmt;
	private Date oddfreqnextduedate;
	private String noteIntPmtTypCd;
	private Date contractdate;
	private Date accrstartdate;
	private Long currTerm;
	private String calcbaltypcd;
	private int existingReceivables;
	private BigDecimal lastestBalance;

	public AmortizationScheduleGenerator(String acctnbr, BigDecimal currentBalance, BigDecimal outstandingPrincipal,
			BigDecimal currentCharge, BigDecimal outstandingCharge, Date nextDueDate, int acctrcvbsOnMaturityDate,
			Date datemat, Date disbursmentDate, Integer pmtduedaynbr, String pmtcalperiodcd, Date intEffDate,
			Date lastBilledDueDate, BigDecimal intEffAmt, BigDecimal outstandingInterest, BigDecimal effIntRate,
			Date interestFreeDate, BigDecimal newintrate, String daysmethcd, int effIntBase, BigDecimal interestMargin,
			String noteBalPmtTypCd, BigDecimal termPmtAmt, Date oddfreqnextduedate, String noteIntPmtTypCd,
			Date contractdate, Date accrstartdate, Long currTerm, String calcbaltypcd) {
		this.acctnbr = acctnbr;
		this.currentBalance = currentBalance;
		this.outstandingPrincipal = outstandingPrincipal;
		this.currentCharge = currentCharge;
		this.outstandingCharge = outstandingCharge;
		this.nextDueDate = nextDueDate;
		this.acctrcvbsOnMaturityDate = acctrcvbsOnMaturityDate;
		this.datemat = datemat;
		this.disbursmentDate = disbursmentDate;
		this.pmtduedaynbr = pmtduedaynbr;
		this.pmtcalperiodcd = pmtcalperiodcd;
		this.intEffDate = intEffDate;
		this.lastBilledDueDate = lastBilledDueDate;
		this.intEffAmt = intEffAmt;
		this.outstandingInterest = outstandingInterest;
		this.effIntRate = effIntRate;
		this.interestFreeDate = interestFreeDate;
		this.newintrate = newintrate;
		this.daysmethcd = daysmethcd;
		this.effIntBase = effIntBase;
		this.interestMargin = interestMargin;
		this.noteBalPmtTypCd = noteBalPmtTypCd;
		this.termPmtAmt = termPmtAmt;
		this.oddfreqnextduedate = oddfreqnextduedate;
		this.noteIntPmtTypCd = noteIntPmtTypCd;
		this.contractdate = contractdate;
		this.accrstartdate = accrstartdate;
		this.currTerm = currTerm;
		this.calcbaltypcd = calcbaltypcd;
	}

	public List<AmortizationScheduleItem> generate() {
		ArrayList amortSched = new ArrayList();
		this.processExistingReceivables(amortSched);
		if (this.allReceivablesHaveBeenGenerated()) {
			this.fillStartingAndEndingBalance(amortSched);
			return amortSched;
		} else {
			this.calcFutureReceivables(amortSched);
			this.fillStartingAndEndingBalance(amortSched);
			return amortSched;
		}
	}

	private void fillStartingAndEndingBalance(List<AmortizationScheduleItem> amortSched) {
		BigDecimal schedBalance = BigDecimal.ZERO;

		for (int i = 0; i < amortSched.size(); ++i) {
			AmortizationScheduleItem item = (AmortizationScheduleItem) amortSched.get(i);
			schedBalance = this.currentBalance;
			if (i != 0 || this.existingReceivables <= 0) {
				if (i == this.existingReceivables) {
					this.lastestBalance = this.currentBalance;
					item.startingBalance = this.lastestBalance.subtract(this.outstandingPrincipal);
					item.endingBalance = schedBalance.compareTo(item.startingBalance) == -1
							? schedBalance.subtract(item.principalAmount)
							: item.startingBalance.subtract(item.principalAmount);
				} else {
					item.startingBalance = ((AmortizationScheduleItem) amortSched.get(i - 1)).endingBalance;
					item.endingBalance = item.startingBalance.subtract(item.principalAmount);
				}
			}

			item.startingBalance = item.startingBalance.setScale(2, RoundingMode.HALF_UP);
			item.endingBalance = item.endingBalance.setScale(2, RoundingMode.HALF_UP);
		}

	}

	private boolean allReceivablesHaveBeenGenerated() {
		return this.lastBilledDueDate != null
				&& (this.lastBilledDueDate.equals(this.datemat) || this.lastBilledDueDate.after(this.datemat));
	}

	private void calcFutureReceivables(List<AmortizationScheduleItem> amortSched) {
		BigDecimal accruingBalance;
		if ("NDUE".equals(this.calcbaltypcd)) {
			accruingBalance = this.currentBalance.subtract(this.outstandingPrincipal);
		} else {
			accruingBalance = this.currentBalance;
		}

		if (accruingBalance.compareTo(BigDecimal.ZERO) > 0) {
			Long currTerm = this.currTerm;
			Date dueDate = this.calcInitialDueDate();

			for (Date fromDate = this.calcInitialFromDate(); (dueDate.before(this.datemat)
					|| dueDate.equals(this.datemat))
					&& accruingBalance.compareTo(BigDecimal.ZERO) > 0; currTerm = Long
							.valueOf(currTerm.longValue() + 1L)) {
				BigDecimal periodInterest = this.calcPeriodInterest(accruingBalance, fromDate, dueDate);
				BigDecimal periodInterestRound = periodInterest.setScale(2, RoundingMode.HALF_UP);
				this.interestMargin = periodInterest.subtract(periodInterestRound);
				this.interestMargin = BigDecimal.ZERO;
				BigDecimal periodPrincipal = this.calcPeriodPrincipal(accruingBalance, fromDate, dueDate,
						periodInterestRound);
				BigDecimal periodPrincipalRound = periodPrincipal.setScale(2, RoundingMode.HALF_UP);
				Date startDate = null;
				if (!amortSched.isEmpty()) {
					startDate = ((AmortizationScheduleItem) amortSched.get(amortSched.size() - 1)).dueDate;
				} else {
					startDate = this.contractdate;
					if (this.accrstartdate != null) {
						startDate = this.accrstartdate;
					}
				}

				AmortizationScheduleItem amortSchedItem = new AmortizationScheduleItem();
				amortSchedItem.dueDate = dueDate;
				amortSchedItem.principalAmount = periodPrincipalRound;
				amortSchedItem.interestAmount = periodInterestRound;
				amortSchedItem.chargeAmount = BigDecimal.ZERO;
				amortSchedItem.currTerm = currTerm;
				amortSchedItem.startDate = startDate;
				amortSched.add(amortSchedItem);
				accruingBalance = accruingBalance.subtract(periodPrincipalRound);
				accruingBalance = accruingBalance.setScale(2, RoundingMode.HALF_UP);
				fromDate = dueDate;
				dueDate = this.calcNextDueDate(this.pmtcalperiodcd, dueDate);
			}

		}
	}

	private Date calcInitialDueDate() {
		String pmtCalPeriodCd = this.pmtcalperiodcd;
		Date dueDate = this.calcNextRcvbDueDate();
		Date disbursementDate = this.getDisbursmentDate();

		while (true) {
			do {
				do {
					if (!dueDate.before(disbursementDate) && !dueDate.equals(disbursementDate)) {
						if (dueDate.after(this.datemat)) {
							dueDate = this.datemat;
						}

						return dueDate;
					}

					dueDate = DateWidget.calcDateByCalPeriod(dueDate, pmtCalPeriodCd, 1);
				} while (this.pmtduedaynbr == null);
			} while (!this.pmtcalperiodcd.equals(CalPeriodEnum.MNTH.getCode())
					&& !this.pmtcalperiodcd.equals(CalPeriodEnum.QUAT.getCode())
					&& !this.pmtcalperiodcd.equals(CalPeriodEnum.SQUT.getCode())
					&& !this.pmtcalperiodcd.equals(CalPeriodEnum.SEMA.getCode())
					&& !this.pmtcalperiodcd.equals(CalPeriodEnum.ANNU.getCode()));

			int pmtDueDayNbr = this.pmtduedaynbr.intValue();
			dueDate = DateWidget.adjustDateByDay(dueDate, pmtDueDayNbr);
		}
	}

	private Date calcNextRcvbDueDate() {
		return this.nextDueDate.after(this.datemat) && !this.maturityDateReceivablesExists() ? this.datemat
				: this.nextDueDate;
	}

	private boolean maturityDateReceivablesExists() {
		return this.acctrcvbsOnMaturityDate > 0;
	}

	private Date getDisbursmentDate() {
		return this.disbursmentDate;
	}

	private Date calcInitialFromDate() {
		Date intStartDate = this.intEffDate;
		Date fromDate = this.lastBilledDueDate == null ? DateWidget.addDays(intStartDate, 1L) : this.lastBilledDueDate;
		return fromDate;
	}

	private BigDecimal calcPeriodInterest(BigDecimal accruingBalance, Date fromDate, Date dueDate) {
		BigDecimal periodInterest = BigDecimal.ZERO;
		Date intStartDate = this.intEffDate;
		intStartDate = DateWidget.addDays(intStartDate, 1L);
		if (!fromDate.after(intStartDate) && !intStartDate.after(dueDate)) {
			BigDecimal latestIntBalAmt = this.intEffAmt;
			periodInterest = this.calcInterest(accruingBalance, intStartDate, dueDate);
			periodInterest = latestIntBalAmt.add(periodInterest).subtract(this.outstandingInterest);
		} else {
			periodInterest = this.calcInterest(accruingBalance, fromDate, dueDate);
		}

		return periodInterest;
	}

	private BigDecimal calcInterest(BigDecimal accruingBalance, Date fromDate, Date dueDate) {
		BigDecimal periodInterest = BigDecimal.ZERO;
		BigDecimal intRate = this.effIntRate;
		if (!this.interestFreeDate.after(fromDate)) {
			if (intRate.compareTo(BigDecimal.ZERO) == 0) {
				intRate = this.newintrate;
			}
		} else if (this.interestFreeDate.after(fromDate) && !this.interestFreeDate.after(dueDate)) {
			intRate = this.newintrate;
			fromDate = this.interestFreeDate;
		}

		int periodDays;
		if ("30DM".equals(this.daysmethcd)) {
			periodDays = get30DayBetween(fromDate, dueDate);
		} else {
			periodDays = DateWidget.calcTermDays(fromDate, dueDate);
		}

		periodInterest = accruingBalance.multiply(intRate).multiply(new BigDecimal(periodDays))
				.divide(new BigDecimal(this.effIntBase), 5, RoundingMode.HALF_UP);
		periodInterest = periodInterest.add(this.interestMargin);
		this.interestMargin = BigDecimal.ZERO;
		return periodInterest;
	}

	public static int get30DayBetween(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		int startDay = startCal.get(5);
		int endDay = endCal.get(5);
		return startDate.compareTo(endDate) == 0 ? 0
				: endDay - startDay + (endCal.get(2) - startCal.get(2)) * 30 + (endCal.get(1) - startCal.get(1)) * 360;
	}

	private BigDecimal calcPeriodPrincipal(BigDecimal accruingBalance, Date fromDate, Date dueDate,
			BigDecimal periodInterest) {
		BigDecimal periodPrincipal = BigDecimal.ZERO;
		if ("FBI".equals(this.noteBalPmtTypCd)) {
			periodPrincipal = this.termPmtAmt.subtract(periodInterest);
		} else if ("FB".equals(this.noteBalPmtTypCd)) {
			if (this.oddfreqnextduedate == null) {
				periodPrincipal = this.termPmtAmt;
			} else if (dueDate.before(this.oddfreqnextduedate)) {
				periodPrincipal = BigDecimal.ZERO;
			} else if (dueDate.equals(this.oddfreqnextduedate)) {
				periodPrincipal = this.termPmtAmt;
				this.oddfreqnextduedate = DateWidget.calcDateByCalPeriod(dueDate, this.pmtcalperiodcd, 1);
			}
		} else if (!this.datemat.equals(dueDate) && "VINT".equals(this.noteIntPmtTypCd)
				&& (this.noteBalPmtTypCd == null || this.noteBalPmtTypCd.equals(""))) {
			periodPrincipal = BigDecimal.ZERO;
		} else if (this.datemat.equals(dueDate) && "VINT".equals(this.noteIntPmtTypCd)
				&& (this.noteBalPmtTypCd == null || this.noteBalPmtTypCd.equals(""))) {
			periodPrincipal = accruingBalance;
		}

		if (periodPrincipal.compareTo(accruingBalance) > 0 || dueDate.equals(this.datemat)) {
			periodPrincipal = accruingBalance;
		}

		if (periodPrincipal.compareTo(BigDecimal.ZERO) < 0) {
			periodPrincipal = BigDecimal.ZERO;
		}

		return periodPrincipal;
	}

	private Date calcNextDueDate(String pmtCalPeriodCd, Date dueDate) {
		Date nextDueDate = DateWidget.calcDateByCalPeriod(dueDate, pmtCalPeriodCd, 1);
		if (this.pmtduedaynbr != null && (this.pmtcalperiodcd.equals(CalPeriodEnum.MNTH.getCode())
				|| this.pmtcalperiodcd.equals(CalPeriodEnum.QUAT.getCode())
				|| this.pmtcalperiodcd.equals(CalPeriodEnum.SQUT.getCode())
				|| this.pmtcalperiodcd.equals(CalPeriodEnum.SEMA.getCode())
				|| this.pmtcalperiodcd.equals(CalPeriodEnum.ANNU.getCode()))) {
			int pmtDueDayNbr = this.pmtduedaynbr.intValue();
			nextDueDate = DateWidget.adjustDateByDay(nextDueDate, pmtDueDayNbr);
		}

		if (nextDueDate.after(this.datemat)) {
			nextDueDate = this.datemat;
		}

		if (CalPeriodEnum.MNTH.getCode().equals(pmtCalPeriodCd) && !nextDueDate.equals(this.datemat)
				&& DateWidget.areInSameNatrualMonth(nextDueDate, this.datemat)) {
			nextDueDate = this.datemat;
		}

		return nextDueDate;
	}

	private void processExistingReceivables(List<AmortizationScheduleItem> amortSched) {
		if (this.lastBilledDueDate != null) {
			AmortizationScheduleItem amortSchedItem = new AmortizationScheduleItem();
			amortSchedItem.dueDate = this.lastBilledDueDate;
			amortSchedItem.currTerm = Long.valueOf(this.currTerm.longValue() - 1L);
			amortSched.add(amortSchedItem);
		}

	}

	public static void getFutureAmortItem(List<AmortizationScheduleItem> amortSched, List<Acctgrp> acctGrpList,
			Date nextDueDate, Map<String, List<LoanPmtFutureAmortItem>> loanPmtFutureAmortItemMap, String unilyn,
			String accttypcd, long gracedays, String acctNbr, Date firstduedate, BigDecimal disbAmt,
			BigDecimal currBalance, BigDecimal outstandingPrin) {
		ArrayList loanPmtFutureAmortItem = new ArrayList();
		BigDecimal duedateEndAmt = BigDecimal.ZERO;
		if ("1".equals(unilyn) && "V".equals(accttypcd) || "0".equals(unilyn) && "R".equals(accttypcd)) {
			Iterator arg27 = amortSched.iterator();

			while (arg27.hasNext()) {
				AmortizationScheduleItem arg25 = (AmortizationScheduleItem) arg27.next();
				if (!arg25.dueDate.before(nextDueDate)) {
					LoanPmtFutureAmortItem arg29 = new LoanPmtFutureAmortItem();
					Date arg31 = DateWidget.addDays(arg25.dueDate, gracedays);
					arg29.setStartDate(arg25.startDate);
					arg29.setDueDate(arg25.dueDate);
					arg29.setGraceDueDate(arg31);
					arg29.setChargeAmt(arg25.chargeAmount.setScale(2, RoundingMode.HALF_UP));
					arg29.setCurrTerm(arg25.currTerm.longValue());
					arg29.setStartBalAmt(arg25.startingBalance.setScale(2, RoundingMode.HALF_UP));
					arg29.setPrincipalAmt(arg25.principalAmount.setScale(2, RoundingMode.HALF_UP));
					arg29.setIntRestAmt(arg25.interestAmount.setScale(2, RoundingMode.HALF_UP));
					arg29.setEndBalAmt(arg25.endingBalance.setScale(2, RoundingMode.HALF_UP));
					arg29.setTermStatCD("ACT");
					loanPmtFutureAmortItem.add(arg29);
				}
			}

			loanPmtFutureAmortItemMap.put(acctNbr, loanPmtFutureAmortItem);
		} else if ("1".equals(unilyn) && "R".equals(accttypcd)) {
			ArrayList partInfo = new ArrayList();
			Iterator item = acctGrpList.iterator();

			while (item.hasNext()) {
				Acctgrp i = (Acctgrp) item.next();
				UnilInfo loanPmtHistAmortInfo = new UnilInfo(i.getRelacctnbr(), i.getBalpct());
				loanPmtHistAmortInfo.setOwnyn(i.getOwnyn());
				partInfo.add(loanPmtHistAmortInfo);
			}

			for (int arg26 = 0; arg26 < amortSched.size(); ++arg26) {
				AmortizationScheduleItem arg28 = (AmortizationScheduleItem) amortSched.get(arg26);
				if (!arg28.dueDate.before(nextDueDate)) {
					LoanPmtFutureAmortItem arg30 = new LoanPmtFutureAmortItem();
					BigDecimal principalAmt = BigDecimal.ZERO;
					BigDecimal intRestAmt = BigDecimal.ZERO;
					BigDecimal startBalAmt = BigDecimal.ZERO;
					BigDecimal endBalAmt = BigDecimal.ZERO;
					if ("1".equals(unilyn) && "R".equals(accttypcd)) {
						List graceDueDate;
						UnilInfo outstandingPrincipal;
						Iterator arg24;
						if (arg28.principalAmount.compareTo(BigDecimal.ZERO) == 1) {
							graceDueDate = unilBalance(partInfo, (String) null, (String) null, arg28.principalAmount,
									(Date) null, (Integer) null, (Date) null, (String) null);
							if (graceDueDate != null && !graceDueDate.isEmpty()) {
								arg24 = graceDueDate.iterator();

								while (arg24.hasNext()) {
									outstandingPrincipal = (UnilInfo) arg24.next();
									if (acctNbr.equals(outstandingPrincipal.getAcctNbr())) {
										principalAmt = outstandingPrincipal.getOccurrenceAmt();
										break;
									}
								}
							}
						}

						if (arg28.interestAmount.compareTo(BigDecimal.ZERO) == 1) {
							graceDueDate = unilBalance(partInfo, (String) null, (String) null, arg28.interestAmount,
									(Date) null, (Integer) null, (Date) null, (String) null);
							if (graceDueDate != null && !graceDueDate.isEmpty()) {
								arg24 = graceDueDate.iterator();

								while (arg24.hasNext()) {
									outstandingPrincipal = (UnilInfo) arg24.next();
									if (acctNbr.equals(outstandingPrincipal.getAcctNbr())) {
										intRestAmt = outstandingPrincipal.getOccurrenceAmt();
										break;
									}
								}
							}
						}

						if (arg28.dueDate.equals(firstduedate)) {
							startBalAmt = disbAmt;
						} else if (arg28.dueDate.equals(nextDueDate) && !arg28.dueDate.equals(firstduedate)) {
							startBalAmt = currBalance.subtract(outstandingPrin);
						} else if (arg26 == amortSched.size() - 1) {
							startBalAmt = duedateEndAmt;
							principalAmt = duedateEndAmt;
						} else {
							startBalAmt = duedateEndAmt;
						}

						endBalAmt = startBalAmt.subtract(principalAmt);
						duedateEndAmt = endBalAmt;
					}

					Date arg32 = DateWidget.addDays(arg28.dueDate, gracedays);
					arg30.setStartDate(arg28.startDate);
					arg30.setDueDate(arg28.dueDate);
					arg30.setGraceDueDate(arg32);
					arg30.setChargeAmt(arg28.chargeAmount.setScale(2, RoundingMode.HALF_UP));
					arg30.setCurrTerm(arg28.currTerm.longValue());
					arg30.setStartBalAmt(startBalAmt);
					arg30.setPrincipalAmt(principalAmt);
					arg30.setIntRestAmt(intRestAmt);
					arg30.setEndBalAmt(endBalAmt);
					arg30.setTermStatCD("ACT");
					loanPmtFutureAmortItem.add(arg30);
				}
			}

			loanPmtFutureAmortItemMap.put(acctNbr, loanPmtFutureAmortItem);
		}

	}

	public static List<UnilInfo> unilBalance(List<UnilInfo> partInfo, String balCatCd, String balTypCd, BigDecimal amt,
			Date throughDate, Integer nbrOfDecimals, Date postDate, String ownyn) {
		if (amt.compareTo(BigDecimal.ZERO) == 0) {
			Iterator arg16 = partInfo.iterator();

			while (arg16.hasNext()) {
				UnilInfo arg15 = (UnilInfo) arg16.next();
				arg15.setOccurrenceAmt(BigDecimal.ZERO);
			}

			return partInfo;
		} else {
			Collections.sort(partInfo, new Comparator<UnilInfo>() {
				public int compare(UnilInfo a, UnilInfo b) {
					return a.getOwnyn().compareTo(b.getOwnyn()) > 0 ? -1 : 1;
				}

			});
			boolean flag = false;
			if (nbrOfDecimals == null) {
				nbrOfDecimals = Integer.valueOf(2);
			}

			BigDecimal realOccurrenceAmt = BigDecimal.ZERO;
			int loopCtrl;
			BigDecimal totalAmt;
			UnilInfo compareAmt;
			Iterator arg12;
			BigDecimal occurrenceAmt;
			if (amt.compareTo(BigDecimal.ZERO) == 1) {
				loopCtrl = 0;
				totalAmt = BigDecimal.ZERO;
				arg12 = partInfo.iterator();

				while (arg12.hasNext()) {
					compareAmt = (UnilInfo) arg12.next();
					++loopCtrl;
					occurrenceAmt = BigDecimal.ZERO;
					if (loopCtrl == partInfo.size()) {
						occurrenceAmt = amt.subtract(totalAmt);
					} else {
						occurrenceAmt = amt.multiply(compareAmt.getBalPct());
						totalAmt = totalAmt.add(occurrenceAmt);
					}

					compareAmt.setOccurrenceAmt(occurrenceAmt);
					if (occurrenceAmt.setScale(2, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) == 0) {
						flag = true;
						break;
					}

					compareAmt.setOccurrenceAmt(occurrenceAmt);
				}
			}

			if (flag) {
				loopCtrl = (new Random()).nextInt(partInfo.size());
				((UnilInfo) partInfo.get(loopCtrl)).setOccurrenceAmt(amt.abs());

				for (int arg17 = 0; arg17 < partInfo.size(); ++arg17) {
					if (arg17 != loopCtrl) {
						((UnilInfo) partInfo.get(arg17)).setOccurrenceAmt(BigDecimal.ZERO);
					}
				}
			}

			loopCtrl = 0;
			totalAmt = BigDecimal.ZERO;
			arg12 = partInfo.iterator();

			while (arg12.hasNext()) {
				compareAmt = (UnilInfo) arg12.next();
				++loopCtrl;
				occurrenceAmt = compareAmt.getOccurrenceAmt();
				realOccurrenceAmt = realOccurrenceAmt.add(occurrenceAmt);
				if (loopCtrl == partInfo.size()) {
					BigDecimal endAmt = amt.abs().subtract(totalAmt);
					if ("1".equals(compareAmt.getOwnyn())) {
						compareAmt.setOccurrenceAmt(endAmt.setScale(nbrOfDecimals.intValue(), RoundingMode.FLOOR));
					} else {
						compareAmt.setOccurrenceAmt(endAmt.setScale(nbrOfDecimals.intValue(), RoundingMode.HALF_UP));
					}
				} else {
					if ("1".equals(compareAmt.getOwnyn())) {
						compareAmt
								.setOccurrenceAmt(occurrenceAmt.setScale(nbrOfDecimals.intValue(), RoundingMode.FLOOR));
					} else {
						compareAmt.setOccurrenceAmt(
								occurrenceAmt.setScale(nbrOfDecimals.intValue(), RoundingMode.HALF_UP));
					}

					totalAmt = totalAmt.add(compareAmt.getOccurrenceAmt());
				}
			}

			BigDecimal arg18 = realOccurrenceAmt.subtract(amt.abs()).setScale(2, RoundingMode.CEILING);
			arg18.compareTo(BigDecimal.ZERO);
			return partInfo;
		}
	}
}