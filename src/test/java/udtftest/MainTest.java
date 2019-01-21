/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package udtftest;

import com.aliyun.odps.udf.UDFException;
import com.csii.loan.backfile.DateWidget;
import com.csii.loan.backfile.PmtPlanUDTF;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;

public class MainTest {
	@Test
	public void test() throws ParseException {
		PmtPlanUDTF udtf = new PmtPlanUDTF();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String acctnbr = "99990106600000000111547";
		BigDecimal currentBalance = new BigDecimal("7906.30000");
		BigDecimal outstandingPrincipal = new BigDecimal("0");
		BigDecimal currentCharge = new BigDecimal("0");
		BigDecimal outstandingCharge = new BigDecimal("0");
		Date nextDueDate = sdf.parse("2017-03-06");
		Long acctrcvbsOnMaturityDate = Long.valueOf(0L);
		Date datemat = sdf.parse("2017-07-06");
		Date disbursmentDate = sdf.parse("2017-01-06");
		String pmtcalperiodcd = "1M";
		Long pmtduedaynbr = Long.valueOf(6L);
		Date intEffDate = sdf.parse("2017-02-08");
		Date lastBilledDueDate = sdf.parse("2017-02-06");
		BigDecimal intEffAmt = new BigDecimal("15.03327");
		BigDecimal outstandingInterest = new BigDecimal("0");
		BigDecimal effIntRate = new BigDecimal("0.2146");
		Integer gracedays = Integer.valueOf(3);
		BigDecimal newintrate = new BigDecimal("0.2146");
		String daysmethcd = "30DM";
		BigDecimal interestMargin = BigDecimal.ZERO;
		Long effIntBase = Long.valueOf(360L);
		String noteBalPmtTypCd = "FBI";
		BigDecimal termPmtAmt = new BigDecimal("1667.10000");
		Object oddfreqnextduedate = null;
		String noteIntPmtTypCd = "FBI";
		Date contractdate = sdf.parse("2017-01-06");
		Date accrstartdate = sdf.parse("2017-01-06");
		Long currTerm = Long.valueOf(2L);
		Date interestFreeDate = gracedays == null ? contractdate
				: DateWidget.addDays(contractdate, (long) gracedays.intValue());
		Date firstDueDate = sdf.parse("2017-01-06");
		BigDecimal disbamt = new BigDecimal("10000");
		String allRelAcctInfo = "test1-0.2-1581.26-0-2000-9999-99990106-A0000-id1-name1-wd1|test2-0.8-6325.04-0-8000-0000-00000001-A0000-id2-name2-wd2";

		try {
			udtf.process(new Object[] { acctnbr, currentBalance, outstandingPrincipal, currentCharge, outstandingCharge,
					nextDueDate, acctrcvbsOnMaturityDate, datemat, disbursmentDate, pmtduedaynbr, pmtcalperiodcd,
					intEffDate, lastBilledDueDate, intEffAmt, outstandingInterest, effIntRate, interestFreeDate,
					newintrate, daysmethcd, effIntBase, interestMargin, noteBalPmtTypCd, termPmtAmt, oddfreqnextduedate,
					noteIntPmtTypCd, contractdate, accrstartdate, currTerm, "NDUE", "1", "V",
					Long.valueOf(gracedays.longValue()), firstDueDate, disbamt, allRelAcctInfo, "9999", "99990106",
					"A00000", "name", "id", "wd" });
		} catch (UDFException arg35) {
			arg35.printStackTrace();
		} catch (IOException arg36) {
			arg36.printStackTrace();
		}

	}

	@Test
	public void test2() throws ParseException {
		PmtPlanUDTF udtf = new PmtPlanUDTF();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String acctnbr = "99990106600000000112547";
		BigDecimal currentBalance = new BigDecimal("10000");
		BigDecimal outstandingPrincipal = new BigDecimal("0");
		BigDecimal currentCharge = new BigDecimal("0");
		BigDecimal outstandingCharge = new BigDecimal("0");
		Date nextDueDate = sdf.parse("2017-02-06");
		byte acctrcvbsOnMaturityDate = 0;
		Date datemat = sdf.parse("2017-07-06");
		Date disbursmentDate = sdf.parse("2017-01-06");
		String pmtcalperiodcd = "1M";
		Integer pmtduedaynbr = Integer.valueOf(6);
		Date intEffDate = sdf.parse("2017-01-05");
		Object lastBilledDueDate = null;
		BigDecimal intEffAmt = new BigDecimal("0");
		BigDecimal outstandingInterest = new BigDecimal("0");
		BigDecimal effIntRate = new BigDecimal("0.2146");
		Integer gracedays = Integer.valueOf(3);
		BigDecimal newintrate = new BigDecimal("0.2146");
		String daysmethcd = "30DM";
		BigDecimal interestMargin = BigDecimal.ZERO;
		short effIntBase = 360;
		String noteBalPmtTypCd = "FBI";
		BigDecimal termPmtAmt = new BigDecimal("1772.53000");
		Object oddfreqnextduedate = null;
		String noteIntPmtTypCd = "FBI";
		Date contractdate = sdf.parse("2017-01-06");
		Date accrstartdate = sdf.parse("2017-01-06");
		Long currTerm = Long.valueOf(1L);
		Date interestFreeDate = gracedays == null ? contractdate
				: DateWidget.addDays(contractdate, (long) gracedays.intValue());
		Date firstDueDate = sdf.parse("2017-01-06");
		BigDecimal disbamt = new BigDecimal("10000");
		String allRelAcctInfo = "test1-0.2-2000-0-2000|test2-0.8-8000-0-8000";

		try {
			udtf.process(new Object[] { acctnbr, currentBalance, outstandingPrincipal, currentCharge, outstandingCharge,
					nextDueDate, Integer.valueOf(acctrcvbsOnMaturityDate), datemat, disbursmentDate, pmtduedaynbr,
					pmtcalperiodcd, intEffDate, lastBilledDueDate, intEffAmt, outstandingInterest, effIntRate,
					interestFreeDate, newintrate, daysmethcd, Integer.valueOf(effIntBase), interestMargin,
					noteBalPmtTypCd, termPmtAmt, oddfreqnextduedate, noteIntPmtTypCd, contractdate, accrstartdate,
					currTerm, "NDUE", "1", "V", Long.valueOf(gracedays.longValue()), firstDueDate, disbamt,
					allRelAcctInfo });
		} catch (UDFException arg35) {
			arg35.printStackTrace();
		} catch (IOException arg36) {
			arg36.printStackTrace();
		}

	}

	@Test
	public void test3() {
		new Date(System.currentTimeMillis());
		Object s = null;
	}

	@Test
	public void test4() throws ParseException {
		PmtPlanUDTF udtf = new PmtPlanUDTF();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String acctnbr = "99990106610000001560492";
		BigDecimal currentBalance = new BigDecimal("10000");
		BigDecimal outstandingPrincipal = new BigDecimal("0");
		BigDecimal currentCharge = new BigDecimal("0");
		BigDecimal outstandingCharge = new BigDecimal("0");
		String nextDueDate = "2018-02-03";
		Long acctrcvbsOnMaturityDate = Long.valueOf(0L);
		String datemat = "2018-09-03";
		String disbursmentDate = "2018-01-03";
		Long pmtduedaynbr = Long.valueOf(3L);
		String pmtcalperiodcd = "1M";
		String intEffDate = "2018-01-02";
		Object lastBilledDueDate = null;
		BigDecimal intEffAmt = new BigDecimal("0");
		BigDecimal outstandingInterest = new BigDecimal("0");
		BigDecimal effIntRate = new BigDecimal("0.18");
		Integer gracedays = Integer.valueOf(3);
		BigDecimal newintrate = new BigDecimal("0.18");
		String daysmethcd = "ACT";
		BigDecimal interestMargin = BigDecimal.ZERO;
		Long effIntBase = Long.valueOf(360L);
		String noteBalPmtTypCd = "FB";
		BigDecimal termPmtAmt = new BigDecimal("1250");
		Object oddfreqnextduedate = null;
		String noteIntPmtTypCd = "VINT";
		Date contractdate = sdf.parse("2018-01-03");
		String accrstartdate = "2018-01-03";
		Long currTerm = Long.valueOf(1L);
		Date interestFreeDate = gracedays == null ? contractdate
				: DateWidget.addDays(contractdate, (long) gracedays.intValue());
		String firstDueDate = "2018-02-03";
		BigDecimal disbamt = new BigDecimal("10000");
		String allRelAcctInfo = "test1-0.2-2000-0-2000-9999-99990106-A0000-id1-name1-wd1-1|test2-0.8-8000-0-8000-0000-00000001-A0000-id2-name2-wd2-0";

		try {
			udtf.process(new Object[] { acctnbr, currentBalance, outstandingPrincipal, currentCharge, outstandingCharge,
					nextDueDate, acctrcvbsOnMaturityDate, datemat, disbursmentDate, pmtduedaynbr, pmtcalperiodcd,
					intEffDate, lastBilledDueDate, intEffAmt, outstandingInterest, effIntRate,
					sdf.format(interestFreeDate), newintrate, daysmethcd, effIntBase, interestMargin, noteBalPmtTypCd,
					termPmtAmt, oddfreqnextduedate, noteIntPmtTypCd, sdf.format(contractdate), accrstartdate, currTerm,
					"NDUE", "1", "V", Long.valueOf(gracedays.longValue()), firstDueDate, disbamt, allRelAcctInfo,
					"9999", "99990106", "A00000", "name", "id", "wd" });
		} catch (UDFException arg35) {
			arg35.printStackTrace();
		} catch (IOException arg36) {
			arg36.printStackTrace();
		}

	}

	@Test
	public void test5() throws ParseException {
		PmtPlanUDTF udtf = new PmtPlanUDTF();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String acctnbr = "99990106610000000001669";
		BigDecimal currentBalance = new BigDecimal("25000");
		BigDecimal outstandingPrincipal = new BigDecimal("3308.26");
		BigDecimal currentCharge = new BigDecimal("0");
		BigDecimal outstandingCharge = new BigDecimal("0");
		String nextDueDate = "2017-05-20";
		Long acctrcvbsOnMaturityDate = Long.valueOf(0L);
		String datemat = "2019-01-12";
		String disbursmentDate = "2017-2-20";
		Long pmtduedaynbr = Long.valueOf(20L);
		String pmtcalperiodcd = "1M";
		String intEffDate = "2017-04-19";
		String lastBilledDueDate = "2017-04-20";
		BigDecimal intEffAmt = new BigDecimal("2806.43");
		BigDecimal outstandingInterest = new BigDecimal("2806.43");
		BigDecimal effIntRate = new BigDecimal("0.75");
		Integer gracedays = Integer.valueOf(3);
		BigDecimal newintrate = new BigDecimal("0.75");
		String daysmethcd = "ACT";
		BigDecimal interestMargin = BigDecimal.ZERO;
		Long effIntBase = Long.valueOf(360L);
		String noteBalPmtTypCd = "FBI";
		BigDecimal termPmtAmt = new BigDecimal("2038.23");
		Object oddfreqnextduedate = null;
		String noteIntPmtTypCd = "FBI";
		Date contractdate = sdf.parse("2017-02-20");
		String accrstartdate = "2017-02-20";
		Long currTerm = Long.valueOf(3L);
		Date interestFreeDate = gracedays == null ? contractdate
				: DateWidget.addDays(contractdate, (long) gracedays.intValue());
		String firstDueDate = "2017-02-24";
		BigDecimal disbamt = new BigDecimal("25000");
		String allRelAcctInfo = "99990106600000000002669-0.3-7500-992.47-7500-99990106-9999-1000010101207007000-3000492664-大头-1131009-1|00010001600000000003669-0.7-17500-2315.79-17500-00010001-0001-1000010101207007000-3000492664-大头-1131009-0";

		try {
			udtf.process(new Object[] { acctnbr, currentBalance, outstandingPrincipal, currentCharge, outstandingCharge,
					nextDueDate, acctrcvbsOnMaturityDate, datemat, disbursmentDate, pmtduedaynbr, pmtcalperiodcd,
					intEffDate, lastBilledDueDate, intEffAmt, outstandingInterest, effIntRate,
					sdf.format(interestFreeDate), newintrate, daysmethcd, effIntBase, interestMargin, noteBalPmtTypCd,
					termPmtAmt, oddfreqnextduedate, noteIntPmtTypCd, sdf.format(contractdate), accrstartdate, currTerm,
					"NDUE", "1", "V", Long.valueOf(gracedays.longValue()), firstDueDate, disbamt, allRelAcctInfo,
					"99990106", "9999", "1000010101207007000", "3000492664", "大头", "1131009" });
		} catch (UDFException arg35) {
			arg35.printStackTrace();
		} catch (IOException arg36) {
			arg36.printStackTrace();
		}

	}
	
	@Test
	public void test6() throws ParseException {
		PmtPlanUDTF udtf = new PmtPlanUDTF();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String acctnbr = "3046";
		BigDecimal currentBalance = new BigDecimal("5800");
		BigDecimal outstandingPrincipal = new BigDecimal("3854.86");
		BigDecimal currentCharge = new BigDecimal("0");
		BigDecimal outstandingCharge = new BigDecimal("0");
		String nextDueDate = "2018-05-02";
		Long acctrcvbsOnMaturityDate = Long.valueOf(0L);
		String datemat = "2018-05-02";
		String disbursmentDate = "2018-05-02";
		Long pmtduedaynbr = Long.valueOf(2L);
		String pmtcalperiodcd = "1M";
		String intEffDate = "2018-04-01";
		String lastBilledDueDate = "2018-04-02";
		BigDecimal intEffAmt = new BigDecimal("66.72");
		BigDecimal outstandingInterest = new BigDecimal("66.72");
		BigDecimal effIntRate = new BigDecimal("0.085");
		Integer gracedays = Integer.valueOf(3);
		BigDecimal newintrate = new BigDecimal("0.085");
		String daysmethcd = "ACT";
		BigDecimal interestMargin = BigDecimal.ZERO;
		Long effIntBase = Long.valueOf(360L);
		String noteBalPmtTypCd = "FBI";
		BigDecimal termPmtAmt = new BigDecimal("1960.79");
		Object oddfreqnextduedate = null;
		String noteIntPmtTypCd = "FBI";
		Date contractdate = sdf.parse("2018-02-02");
		String accrstartdate = "2018-02-02";
		Long currTerm = Long.valueOf(3L);
		Date interestFreeDate = gracedays == null ? contractdate
				: DateWidget.addDays(contractdate, (long) gracedays.intValue());
		String firstDueDate = "2018-03-02";
		BigDecimal disbamt = new BigDecimal("5800");
		String allRelAcctInfo = "4049-0.1-580-385.48-580-99990106-9999-1000010101207007000-3000492664-大头-1131009-1|5049-0.9-5220-3469.38-5220-00010001-0001-1000010101207007000-3000492664-大头-1131009-0";

		try {
			udtf.process(new Object[] { acctnbr, currentBalance, outstandingPrincipal, currentCharge, outstandingCharge,
					nextDueDate, acctrcvbsOnMaturityDate, datemat, disbursmentDate, pmtduedaynbr, pmtcalperiodcd,
					intEffDate, lastBilledDueDate, intEffAmt, outstandingInterest, effIntRate,
					sdf.format(interestFreeDate), newintrate, daysmethcd, effIntBase, interestMargin, noteBalPmtTypCd,
					termPmtAmt, oddfreqnextduedate, noteIntPmtTypCd, sdf.format(contractdate), accrstartdate, currTerm,
					"NDUE", "1", "V", Long.valueOf(gracedays.longValue()), firstDueDate, disbamt, allRelAcctInfo,
					"99990106", "9999", "1000010101207007000", "3000492664", "大头", "1131009","1" });
		} catch (UDFException arg35) {
			arg35.printStackTrace();
		} catch (IOException arg36) {
			arg36.printStackTrace();
		}

	}
}