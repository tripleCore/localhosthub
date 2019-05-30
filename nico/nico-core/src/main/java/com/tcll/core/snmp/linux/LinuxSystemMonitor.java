package com.tcll.core.snmp.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public final class LinuxSystemMonitor {
	/**
	 * get memory info meminfo里面包含的就是内存的信息
	 * 
	 * @param filePath
	 *            文件路径 Ex:"/proc/meminfo"
	 * @return Map<Object, String>
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static Map<Object, String> getMemInfo(String filePath) throws IOException, InterruptedException {
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		Map<Object, String> map = new HashMap<>();
		String str = null;
		StringTokenizer token = null;
		while ((str = br.readLine()) != null) {
			token = new StringTokenizer(str);
			if (!token.hasMoreTokens())
				continue;

			str = token.nextToken();
			if (!token.hasMoreTokens())
				continue;
			if (str.equalsIgnoreCase("MemTotal:"))
				map.put("MemTotal", token.nextToken());
			else if (str.equalsIgnoreCase("MemFree:"))
				map.put("MemFree", token.nextToken());
			else if (str.equalsIgnoreCase("SwapTotal:"))
				map.put("SwapTotal", token.nextToken());
			else if (str.equalsIgnoreCase("SwapFree:"))
				map.put("SwapFree", token.nextToken());
		}
		br.close();
		return map;
	}

	/**
	 * get cpu info
	 * 
	 * @param filePath
	 *            文件路径 Ex:"/proc/stat"
	 * @return float efficiency
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static float getCpuInfo(String filePath) throws IOException, InterruptedException {
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		StringTokenizer token = new StringTokenizer(br.readLine());
		token.nextToken();
		br.close();
		int user1 = Integer.parseInt(token.nextToken());
		int nice1 = Integer.parseInt(token.nextToken());
		int sys1 = Integer.parseInt(token.nextToken());
		int idle1 = Integer.parseInt(token.nextToken());

		Thread.sleep(1000);

		br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		token = new StringTokenizer(br.readLine());
		token.nextToken();
		br.close();
		int user2 = Integer.parseInt(token.nextToken());
		int nice2 = Integer.parseInt(token.nextToken());
		int sys2 = Integer.parseInt(token.nextToken());
		int idle2 = Integer.parseInt(token.nextToken());
		return (float) ((user2 + sys2 + nice2) - (user1 + sys1 + nice1))
				/ (float) ((user2 + nice2 + sys2 + idle2) - (user1 + nice1 + sys1 + idle1));
	}

	/**
	 * 要获取LINUX的CPU温度（如果文件存在）
	 */
	public static void getTemperature() {
		// InputStreamReader(new FileInputStream（new
		// File("/proc/acpi/thermal_zone/THM/temperature")）
	}

	public static void main(String[] args) {
		try {
			System.out.println(getCpuInfo("C:\\Users\\caocc\\Desktop\\stat.txt"));
			System.out.println(getMemInfo("C:\\Users\\caocc\\Desktop\\meminfo.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}