package com.frank.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {
	private static SharedPreferences sp;

	/**
	 * 写入Boolean变量至SharedPreferences
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            存储节点的名称
	 * @param value
	 *            存储节点的值
	 */
	public static void putBoolean(Context context, String key, boolean value) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * 从SharedPreferences读取Boolean标识
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            存储节点的名称
	 * @param value
	 *            存储节点的默认值
	 * @return 默认或此节点读取到的值
	 */
	public static boolean getBoolean(Context context, String key,
									 boolean deFalue) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, deFalue);
	}

	/**
	 * 写入String变量至SharedPreferences
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            存储节点的名称
	 * @param value
	 *            存储节点的值
	 */
	public static void putString(Context context, String key, String value) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putString(key, value).commit();
	}

	/**
	 * 从SharedPreferences读取String标识
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            存储节点的名称
	 * @param value
	 *            存储节点的默认值
	 * @return 默认或此节点读取到的值
	 */
	public static String getString(Context context, String key, String deFalue) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getString(key, deFalue);
	}
	
	/**
	 * 写入int变量至SharedPreferences
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            存储节点的名称
	 * @param value
	 *            存储节点的值
	 */
	public static void putInt(Context context, String key, int value) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putInt(key, value).commit();
	}

	/**
	 * 从SharedPreferences读取int标识
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            存储节点的名称
	 * @param value
	 *            存储节点的默认值
	 * @return 默认或此节点读取到的值
	 */
	public static int getInt(Context context, String key, int deFalue) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getInt(key, deFalue);
	}
	
	

	/**
	 * 删除某个key的节点
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            要删除节点的名称
	 */
	public static void remove(Context context, String key) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().remove(key).commit();

	}

}
