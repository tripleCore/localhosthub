package com.tcll.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcll.core.exception.NicoException;
import com.tcll.core.exception.NicoExceptionEnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {

	private static Logger log = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * NIO way
	 */
	public static byte[] toByteArray(String filename) {

		File f = new File(filename);
		if (!f.exists()) {
			log.error("文件未找到！" + filename);
			throw new NicoException(NicoExceptionEnum.FILE_NOT_FOUND);
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			throw new NicoException(NicoExceptionEnum.FILE_READING_ERROR);
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				throw new NicoException(NicoExceptionEnum.FILE_READING_ERROR);
			}
			try {
				fs.close();
			} catch (IOException e) {
				throw new NicoException(NicoExceptionEnum.FILE_READING_ERROR);
			}
		}
	}

	/**
	 * 删除目录
	 *
	 * 
	 * @Date 2017/10/30 下午4:15
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	private static final String localpath = "F:\\cpms\\";
	private static final String encoding = "UTF-8";

	/**
	 * 模拟报文获取
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static String testXml(String xmlStr) {
		// String localpath =
		// SocketService.class.getResource("/").getPath().replaceAll("%20", " ");//
		// 排除中文空格
		// String localpath = PropertiesUtils.getString("filePath");
		String fileName = localpath + "TestJSON.txt";
//		String encoding = "UTF-8";
		File file = new File(fileName);
		Long filelength = file.length();
		if (filelength <= 0)
			return xmlStr;
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			// newXmlStr=
			String newXmlStr = new String(filecontent, encoding);
			log.info("修改后模拟请求的报文：" + newXmlStr);
			return newXmlStr;
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}

	public static String readAppointedLineNumber(int lineNumber) throws IOException {
		String fileName = localpath + "warring.txt";
//			String encoding = "UTF-8";
		File file = new File(fileName);

		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		String s = "";
		if (lineNumber <= 0 || lineNumber > getTotalLines(file)) {
			System.out.println("不在文件的行数范围(1至总行数)之内。");
//			System.exit(0);
		}
		int lines = 0;
		while (s != null) {
			lines++;
			s = reader.readLine();
			if ((lines - lineNumber) == 0) {
				System.out.println(s);
				reader.close();
				in.close();
				return s;
//				System.exit(0);
			}
		}
		reader.close();
		in.close();
		return s;
	}

	// 文件内容的总行数。
	static int getTotalLines(File file) throws IOException {
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		String s = reader.readLine();
		int lines = 0;
		while (s != null) {
			lines++;
			s = reader.readLine();
		}
		reader.close();
		in.close();
		return lines;
	}

	/**
	 * 白名单判断
	 * 
	 * @param curunitname
	 * @return Boolean
	 */
	public static Boolean whiteList(String curunitname) {
		// String localpath =
		// SocketService.class.getResource("/").getPath().replaceAll("%20", " ");//
		// 排除中文空格
		// String localpath = PropertiesUtils.getString("filePath");
		String fileName = localpath + "whiteList.txt";
		String encoding = "UTF-8";
		File file = new File(fileName);
		Long filelength = file.length();
		if (filelength <= 0)
			return false;
		FileInputStream in = null;
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try {
			in = new FileInputStream(file);
			read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
			bufferedReader = new BufferedReader(read);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				// 指定字符串判断处
				if (line.contains(curunitname)) {
					log.info("白名单 单位信息:" + line);
					return true;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				read.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 黑名单 判断
	 * 
	 * @param curunit
	 * @return
	 */
	public static Boolean blacklist(String curunit) {
		// String localpath =
		// SocketService.class.getResource("/").getPath().replaceAll("%20", " ");//
		// 排除中文空格
		// String localpath = PropertiesUtils.getString("filePath");
		String fileName = localpath + "blacklist.txt";
		String encoding = "UTF-8";
		File file = new File(fileName);
		Long filelength = file.length();
		if (filelength <= 0)
			return false;
		FileInputStream in = null;
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try {
			in = new FileInputStream(file);
			read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
			bufferedReader = new BufferedReader(read);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				// 指定字符串判断处
				if (line.contains(curunit)) {
					log.info("黑名单 单位信息:" + line);
					return true;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				read.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}