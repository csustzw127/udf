/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.csii.loan.backfile;

import com.aliyun.odps.udf.UDFException;
import com.aliyun.odps.udf.UDTF;
import com.aliyun.odps.udf.annotation.Resolve;
import com.csii.loan.backfile.Acctgrp;
import com.csii.loan.backfile.AmortizationScheduleGenerator;
import com.csii.loan.backfile.DateWidget;
import com.csii.loan.backfile.LoanPmtFutureAmortItem;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

@Resolve({
		"string,decimal,decimal,decimal,decimal,string,bigint,string,string,bigint,string,string,string,decimal,decimal,decimal,string,decimal,string,bigint,decimal,string,decimal,string,string,string,string,bigint,string,string,string,bigint,string,decimal,string,string,string,string,string,string,string->string,string,string,string,string,string,string,decimal,string,string,string,decimal,decimal,decimal,decimal,decimal,bigint,string" })
public class PmtPlanUDTF extends UDTF {
	public void process(Object[] args) throws UDFException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String acctnbr = (String) args[0];
		BigDecimal currentBalance = (BigDecimal) args[1];
		BigDecimal outstandingPrincipal = (BigDecimal) args[2];
		BigDecimal currentCharge = (BigDecimal) args[3];
		BigDecimal outstandingCharge = (BigDecimal) args[4];
		String strnextDueDate = (String) args[5];
		int acctrcvbsOnMaturityDate = ((Long) args[6]).intValue();
		String strdatemat = (String) args[7];
		String strdisbursmentDate = (String) args[8];
		Integer pmtduedaynbr = Integer.valueOf(((Long) args[9]).intValue());
		String pmtcalperiodcd = (String) args[10];
		String strintEffDate = (String) args[11];
		String strlastBilledDueDate = (String) args[12];
		BigDecimal intEffAmt = (BigDecimal) args[13];
		BigDecimal outstandingInterest = (BigDecimal) args[14];
		BigDecimal effIntRate = (BigDecimal) args[15];
		String strinterestFreeDate = (String) args[16];
		BigDecimal newintrate = (BigDecimal) args[17];
		String daysmethcd = (String) args[18];
		int effIntBase = ((Long) args[19]).intValue();
		BigDecimal interestMargin = (BigDecimal) args[20];
		String noteBalPmtTypCd = (String) args[21];
		BigDecimal termPmtAmt = (BigDecimal) args[22];
		String stroddfreqnextduedate = (String) args[23];
		String noteIntPmtTypCd = (String) args[24];
		String strcontractdate = (String) args[25];
		String straccrstartdate = (String) args[26];
		Long currTerm = (Long) args[27];
		String calcbaltypcd = (String) args[28];
		String strfirstDueDate = (String) args[32];

		Date nextDueDate;
		Date datemat;
		Date disbursmentDate;
		Date intEffDate;
		Date lastBilledDueDate;
		Date interestFreeDate;
		Date oddfreqnextduedate;
		Date contractdate;
		Date accrstartdate;
		Date firstDueDate;
		try {
			nextDueDate = sdf.parse(strnextDueDate);
			datemat = sdf.parse(strdatemat);
			disbursmentDate = sdf.parse(strdisbursmentDate);
			intEffDate = sdf.parse(strintEffDate);
			lastBilledDueDate = strlastBilledDueDate == null ? null : sdf.parse(strlastBilledDueDate);
			interestFreeDate = sdf.parse(strinterestFreeDate);
			oddfreqnextduedate = stroddfreqnextduedate == null ? null : sdf.parse(stroddfreqnextduedate);
			contractdate = sdf.parse(strcontractdate);
			accrstartdate = sdf.parse(straccrstartdate);
			firstDueDate = sdf.parse(strfirstDueDate);
		} catch (ParseException arg84) {
			throw new RuntimeException(arg84);
		}

		AmortizationScheduleGenerator generator = new AmortizationScheduleGenerator(acctnbr, currentBalance,
				outstandingPrincipal, currentCharge, outstandingCharge, nextDueDate, acctrcvbsOnMaturityDate, datemat,
				disbursmentDate, pmtduedaynbr, pmtcalperiodcd, intEffDate, lastBilledDueDate, intEffAmt,
				outstandingInterest, effIntRate, interestFreeDate, newintrate, daysmethcd, effIntBase, interestMargin,
				noteBalPmtTypCd, termPmtAmt, oddfreqnextduedate, noteIntPmtTypCd, contractdate, accrstartdate, currTerm,
				calcbaltypcd);
		List amors = generator.generate();
		String unilyn = (String) args[29];
		String accttypcd = (String) args[30];
		HashMap loanPmtFutureAmortItemMap = new HashMap();
		long gracedays = ((Long) args[31]).longValue();
		BigDecimal disbAmt = (BigDecimal) args[33];
		String allRelAcctInfo = (String) args[34];
		String branchNbr = (String) args[35];
		String bankOrgNbr = (String) args[36];
		String soldProdID = (String) args[37];
		String custID = (String) args[38];
		String custName = (String) args[39];
		String channalID = (String) args[40];
		ArrayList acctGrpList = new ArrayList();
		HashMap allRealAcctInfoMap = new HashMap();
		String rtnbranchNbr;
		String rtnbankOrgNbr;
		String rtnsoldProdID;
		String rtncustID;
		String rtncustName;
		String rtnchannalID;
		Entry arg86;
		Acctgrp arg90;
		if ("1".equals(unilyn) && "V".equals(accttypcd)) {
			String[] futureIter = allRelAcctInfo.split("\\|");

			String loanTermHist;
			for (int futureEntry = 0; futureEntry < futureIter.length; ++futureEntry) {
				String[] loanNbr = futureIter[futureEntry].split("-");
				loanTermHist = loanNbr[0];
				BigDecimal futureTermInfo = new BigDecimal(loanNbr[1]);
				BigDecimal relPrin = new BigDecimal(loanNbr[2]);
				BigDecimal map2 = new BigDecimal(loanNbr[3]);
				BigDecimal relAcctMap = new BigDecimal(loanNbr[4]);
				rtnbranchNbr = loanNbr[5];
				rtnbankOrgNbr = loanNbr[6];
				rtnsoldProdID = loanNbr[7];
				rtncustID = loanNbr[8];
				rtncustName = loanNbr[9];
				rtnchannalID = loanNbr[10];
				String currBalance = loanNbr[11];
				HashMap graceDueDate = new HashMap();
				graceDueDate.put("relPrin", relPrin);
				graceDueDate.put("relOutstandingPrin", map2);
				graceDueDate.put("balpct", futureTermInfo);
				graceDueDate.put("relDisbAmt", relAcctMap);
				graceDueDate.put("branchNbr", rtnbranchNbr);
				graceDueDate.put("bankOrgNbr", rtnbankOrgNbr);
				graceDueDate.put("soldProdID", rtnsoldProdID);
				graceDueDate.put("custID", rtncustID);
				graceDueDate.put("custName", rtncustName);
				graceDueDate.put("channalID", rtnchannalID);
				graceDueDate.put("ownyn", currBalance);
				allRealAcctInfoMap.put(loanTermHist, graceDueDate);
			}

			Iterator arg88 = allRealAcctInfoMap.entrySet().iterator();

			while (arg88.hasNext()) {
				arg86 = (Entry) arg88.next();
				arg90 = new Acctgrp();
				arg90.setAcctgrptypcd("R");
				arg90.setAcctnbr(acctnbr);
				arg90.setRelacctnbr((String) arg86.getKey());
				arg90.setBalpct((BigDecimal) ((HashMap) arg86.getValue()).get("balpct"));
				arg90.setOwnyn((String) ((HashMap) arg86.getValue()).get("ownyn"));
				acctGrpList.add(arg90);
			}

			AmortizationScheduleGenerator.getFutureAmortItem(amors, acctGrpList, nextDueDate, loanPmtFutureAmortItemMap,
					unilyn, accttypcd, gracedays, acctnbr, firstDueDate, disbAmt, currentBalance, outstandingPrincipal);
			if (!acctGrpList.isEmpty()) {
				arg88 = acctGrpList.iterator();

				while (arg88.hasNext()) {
					Acctgrp arg87 = (Acctgrp) arg88.next();
					loanTermHist = arg87.getRelacctnbr();
					HashMap arg91 = (HashMap) allRealAcctInfoMap.get(loanTermHist);
					AmortizationScheduleGenerator.getFutureAmortItem(amors, acctGrpList, nextDueDate,
							loanPmtFutureAmortItemMap, unilyn, arg87.getAcctgrptypcd(), gracedays, loanTermHist,
							firstDueDate, (BigDecimal) arg91.get("relDisbAmt"), (BigDecimal) arg91.get("relPrin"),
							(BigDecimal) arg91.get("relOutstandingPrin"));
				}
			}
		} else if ("0".equals(unilyn) && "R".equals(accttypcd)) {
			AmortizationScheduleGenerator.getFutureAmortItem(amors, (List) null, nextDueDate, loanPmtFutureAmortItemMap,
					unilyn, accttypcd, gracedays, acctnbr, firstDueDate, disbAmt, currentBalance, outstandingPrincipal);
		}

		if (!loanPmtFutureAmortItemMap.isEmpty()) {
			Iterator arg85 = loanPmtFutureAmortItemMap.entrySet().iterator();

			while (arg85.hasNext()) {
				arg86 = (Entry) arg85.next();
				String arg89 = (String) arg86.getKey();
				if (!acctGrpList.isEmpty()) {
					Iterator arg93 = acctGrpList.iterator();

					while (arg93.hasNext()) {
						arg90 = (Acctgrp) arg93.next();
						arg89.equals(arg90.getRelacctnbr());
					}
				}

				if (branchNbr == null) {
					;
				}

				List arg92 = (List) arg86.getValue();
				Iterator arg95 = arg92.iterator();

				while (arg95.hasNext()) {
					LoanPmtFutureAmortItem arg94 = (LoanPmtFutureAmortItem) arg95.next();
					new HashMap();
					HashMap arg96 = (HashMap) allRealAcctInfoMap.get(arg89);
					BigDecimal arg97;
					if (arg96 != null) {
						rtnbranchNbr = (String) arg96.get("branchNbr");
						rtnbankOrgNbr = (String) arg96.get("bankOrgNbr");
						rtnsoldProdID = (String) arg96.get("soldProdID");
						rtncustID = (String) arg96.get("custID");
						rtncustName = (String) arg96.get("custName");
						rtnchannalID = (String) arg96.get("channalID");
						arg97 = (BigDecimal) arg96.get("relPrin");
					} else {
						rtnbranchNbr = branchNbr;
						rtnbankOrgNbr = bankOrgNbr;
						rtnsoldProdID = soldProdID;
						rtncustID = custID;
						rtncustName = custName;
						rtnchannalID = channalID;
						arg97 = currentBalance;
					}

					Date arg98 = DateWidget.addDays(arg94.dueDate, gracedays);
					Date dueDate = arg94.dueDate;
					BigDecimal principalAmt = arg94.principalAmt.setScale(2, RoundingMode.HALF_UP);
					BigDecimal intRestAmt = arg94.intRestAmt.setScale(2, RoundingMode.HALF_UP);
					BigDecimal chargeAmt = arg94.chargeAmt.setScale(2, RoundingMode.HALF_UP);
					BigDecimal startBalAmt = arg94.startBalAmt.setScale(2, RoundingMode.HALF_UP);
					BigDecimal endBalAmt = arg94.endBalAmt.setScale(2, RoundingMode.HALF_UP);
					long cterm = arg94.currTerm;
					arg97 = arg97.setScale(2, RoundingMode.HALF_UP);
					Date startDate = arg94.startDate;
					this.forward(new Object[] { rtnbranchNbr, rtnbankOrgNbr, rtnsoldProdID, rtncustID, rtncustName,
							arg89, arg89, arg97, sdf.format(startDate), sdf.format(dueDate), sdf.format(arg98),
							principalAmt, intRestAmt, chargeAmt, startBalAmt, endBalAmt, Long.valueOf(cterm),
							rtnchannalID });
				}
			}
		}

	}
}