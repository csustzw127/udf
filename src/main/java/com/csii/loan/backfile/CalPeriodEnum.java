/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.csii.loan.backfile;

public enum CalPeriodEnum {
	DAY("1D", "每天"), WEEK("1W", "每周"), MNTH("1M", "每月"), QUAT("3M", "每三月"), SQUT("1Q", "每季"), SEMA("1H",
			"每半年"), ANNU("1Y", "每年"), MATU("MATU", "一次性"), OTH("OTH", "不定期");

	private final String code;
	private final String desc;

	private CalPeriodEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return this.code;
	}

	public String getDesc() {
		return this.desc;
	}
}