

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterURL {
	public static void main(String[] args) {
		String fileName = "src/163-urlFilter.txt";
		String url = "http://www.163.com";
		writeFileFromMap(fileName, getURLstr(url));

	}

	public static TreeMap<String, String> getURLstr(String strUrl) {
		TreeMap<String, String> filterMap = new TreeMap<String, String>();
		InputStream in = null;
		try {
			URL url = new URL(strUrl); // 创建 URL
			in = url.openStream(); // 打开到这个URL的流
			// 复制字节到输出流
			byte[] buffer = new byte[4096];
			int bytes_read;
			while ((bytes_read = in.read(buffer)) != -1) {
				String reads = new String(buffer, 0, bytes_read, "GBK");
				String regex = "[a-z0-9]+\\.163\\.com";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(reads);
				while (matcher.find()) {
					String match = matcher.group();
					filterMap.put(match, "");
				}
			}
			in.close();
			System.out.println("filterMap.size()=" + filterMap.size());
			return filterMap;
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("Usage: java GetURL <URL> [<filename>]");
			return filterMap;
		}
	}

	public static <S, T> void writeFileFromMap(String fileName,
			Map<S, T> content) {
		StringBuilder sb = new StringBuilder("");
		int count = 0;
		for (Entry<S, T> e : content.entrySet()) {
			System.out.println(count + "-" + e.getKey());
			sb.append(count).append("-").append(e.getKey()).append("\n");
			count++;
		}
		File file = new File(fileName);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(sb.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
