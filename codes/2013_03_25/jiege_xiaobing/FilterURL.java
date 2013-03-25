package com.netease;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 抓取网页，过滤字符串，去重排序输出
 * @author Bingo
 * 2012.3.25
 */
public class FilterURL {
	public static void main(String[] args) {
		String fileName = "src/163-urlFilter.txt";
		String url = "http://www.163.com";
		//过滤*.163.com字符串
		String regex = "[a-z0-9]+\\.163\\.com";
		writeFileFromMap(fileName, getURLStr(url, regex));
	}

	/**
	 * 通过正则表达式抓取网页字符串
	 * @param strURL 网页URL
	 * @param regex 过滤用的正则表达式
	 * @return Map<String, String> 过滤的内容
	 */
	public static Map<String, String> getURLStr(String strURL, String regex) {
		Map<String, String> filterMap = new TreeMap<String, String>();
		InputStream in = null;
		String code = "GBK";
		int size = 4096;
		try {
			// 创建 URL
			URL url = new URL(strURL); 
			// 打开到这个URL的流
			in = url.openStream(); 
			// 复制字节到输出流
			byte[] buffer = new byte[size];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				String reads = new String(buffer, 0, bytesRead, code);
				//正则过滤
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(reads);
				while (matcher.find()) {
					String match = matcher.group();
					//TreeMap去重和排序
					filterMap.put(match, null);
				}
			}
			System.out.println("filterMap.size() = " + filterMap.size());
			return filterMap;
		} catch (IOException e) {
			System.err.println(e);
			return filterMap;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}

	/**
	 * 输出Map内容到本地文件
	 * @param fileName 文件路径
	 * @param content 内容
	 */
	public static <S, T> void writeFileFromMap(String fileName,
			Map<S, T> content) {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		String seprator = "-";
		for (Entry<S, T> e : content.entrySet()) {
			System.out.println(count + seprator + e.getKey());
			sb.append(count).append(seprator).append(e.getKey()).append("\n");
			count++;
		}
		//输出到本地文件
		File file = new File(fileName);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(sb.toString());
			writer.flush();
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}

}
