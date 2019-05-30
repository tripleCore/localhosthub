package com.tcll.generator.utils;

import java.util.List;
import java.util.Map;
/**
 * 集合工具类
 */
public class CollectionKits {

	/**
	 * Map是否为空
	 * @param map 集合
	 * @return 是否为空
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * 数组是否为空
	 * @param array 数组
	 * @return 是否为空
	 */
	public static <T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * 截取数组的部分
	 * @param list 被截取的数组
	 * @param start 开始位置（包含）
	 * @param end 结束位置（不包含）
	 * @return 截取后的数组，当开始位置超过最大时，返回null
	 */
	public static <T> List<T> sub(List<T> list, int start, int end) {
		if(list == null || list.isEmpty()) {
			return null;
		}

		if(start < 0) {
			start = 0;
		}
		if(end < 0) {
			end = 0;
		}

		if(start > end) {
			int tmp = start;
			start = end;
			end = tmp;
		}

		final int size = list.size();
		if(end > size) {
			if(start >= size) {
				return null;
			}
			end = size;
		}

		return list.subList(start, end);
	}
}
