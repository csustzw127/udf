/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.csii.loan.backfile;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ListUtils {
	public static <T> void sort(List<T> list, final boolean isAsc, final String... sortnameArr) {
		Collections.sort(list, new Comparator<T>() {
			public int compare(T a, T b) {
				int ret = 0;

				try {
					for (int e = 0; e < sortnameArr.length; ++e) {
						ret = ListUtils.compareObject(sortnameArr[e], isAsc, a, b);
						if (ret != 0) {
							break;
						}
					}
				} catch (Exception arg4) {
					arg4.printStackTrace();
				}

				return ret;
			}
		});
	}

	public static <T> void sort(List<T> list, final String[] sortnameArr, final boolean[] typeArr) {
		if (sortnameArr.length != typeArr.length) {
			throw new RuntimeException("属性数组元素个数和升降序数组元素个数不相等");
		} else {
			Collections.sort(list, new Comparator<T>() {
				public int compare(T a, T b) {
					int ret = 0;

					try {
						for (int e = 0; e < sortnameArr.length; ++e) {
							ret = ListUtils.compareObject(sortnameArr[e], typeArr[e], a, b);
							if (ret != 0) {
								break;
							}
						}
					} catch (Exception arg4) {
						arg4.printStackTrace();
					}

					return ret;
				}
			});
		}
	}

	private static <T> int compareObject(String sortname, boolean isAsc, T a, T b) throws Exception {
		Object value1 = forceGetFieldValue(a, sortname);
		Object value2 = forceGetFieldValue(b, sortname);
		String str1 = value1.toString();
		String str2 = value2.toString();
		if (value1 instanceof Number && value2 instanceof Number) {
			int time11 = Math.max(str1.length(), str2.length());
			str1 = addZero2Str((Number) value1, time11);
			str2 = addZero2Str((Number) value2, time11);
		} else if (value1 instanceof Date && value2 instanceof Date) {
			long time1 = ((Date) value1).getTime();
			long time2 = ((Date) value2).getTime();
			int maxlen = Long.toString(Math.max(time1, time2)).length();
			str1 = addZero2Str(Long.valueOf(time1), maxlen);
			str2 = addZero2Str(Long.valueOf(time2), maxlen);
		}

		int ret;
		if (isAsc) {
			ret = str1.compareTo(str2);
		} else {
			ret = str2.compareTo(str1);
		}

		return ret;
	}

	public static Object forceGetFieldValue(Object obj, String fieldName) throws Exception {
		Field field = obj.getClass().getDeclaredField(fieldName);
		Object object = null;
		boolean accessible = field.isAccessible();
		if (!accessible) {
			field.setAccessible(true);
			object = field.get(obj);
			field.setAccessible(accessible);
			return object;
		} else {
			object = field.get(obj);
			return object;
		}
	}

	public static String addZero2Str(Number numObj, int length) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMaximumIntegerDigits(length);
		nf.setMinimumIntegerDigits(length);
		return nf.format(numObj);
	}
}