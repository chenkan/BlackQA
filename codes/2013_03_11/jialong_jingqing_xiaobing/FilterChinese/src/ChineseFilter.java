import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 
 =beginCoding Game - 汉语词汇提取phase task 1 读入一个文本，进行粗加工（剔除所有非汉字） 2
 * 统计单字节汉字的出现概率（出现次数/文本字符数）使用Hash作为字典 3 统计双字节汉字的出现概率（出现次数/文本字符数）使用Hash作为词典 4
 * 统计双字节汉字的凝聚度，并排序及格式化输出 5 根据结果，讨论算法可改进点
 * 参考文献http://www.matrix67.com/blog/archives/5044<<数学之美>> =end
 * 
 */
public class ChineseFilter {
	static DecimalFormat dcmFmt = new DecimalFormat("0.00");

	public static void main(String[] args) {
		String text = readAndFilterFile("text.txt");
		HashMap<Character, Double> singleHashMap = statSingleChinese(text);
		HashMap<String, Double> doubleHashMap = statDoubleChinese(text);
		printRelationBySort(singleHashMap, doubleHashMap);
	}

	public static String readAndFilterFile(String fileName) {
		File file = new File(fileName);
		String lines = "";
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = input.readLine()) != null) {
				lines += line;
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 过滤非汉字（正则表达式是[^\u4e00-\u9fa5]）
		lines = lines.replaceAll("[^\u4e00-\u9fa5]", "");
		// System.out.println(lines);
		return lines;
	}

	public static HashMap<Character, Double> statSingleChinese(String text) {
		HashMap<Character, Double> singleHashMap = new HashMap<Character, Double>();
		int sum = text.length();
		for (int i = 0; i < sum; i++) {
			char key = text.charAt(i);
			if (singleHashMap.containsKey(text.charAt(i))) {
				singleHashMap.put(key, singleHashMap.get(key) + 1);
			} else {
				singleHashMap.put(key, 1.0);
			}
		}
		for (Entry<Character, Double> entry : singleHashMap.entrySet()) {
			// System.out.println(entry.getKey() + "-" + entry.getValue());
			entry.setValue(entry.getValue() / sum);
			// System.out.println(entry.getKey() + "-" + entry.getValue());
		}
		return singleHashMap;
	}

	public static HashMap<String, Double> statDoubleChinese(String text) {
		HashMap<String, Double> doubleHashMap = new HashMap<String, Double>();
		int sum = text.length();
		for (int i = 0; i < sum - 1; i++) {
			String key = text.substring(i, i + 2);
			if (doubleHashMap.containsKey(key)) {
				doubleHashMap.put(key, doubleHashMap.get(key) + 1);
			} else {
				doubleHashMap.put(key, 1.0);
			}
		}
		for (Entry<String, Double> entry : doubleHashMap.entrySet()) {
			// System.out.println(entry.getKey() + "-" + entry.getValue());
			entry.setValue(entry.getValue() / sum);
			// System.out.println(entry.getKey() + "-" + entry.getValue());
		}
		return doubleHashMap;
	}

	public static void printRelationBySort(
			HashMap<Character, Double> singleHashMap,
			HashMap<String, Double> doubleHashMap) {
		HashMap<String, Double> allHashMap = new HashMap<String, Double>();
		for (Entry<String, Double> entry : doubleHashMap.entrySet()) {
			double pDouble = entry.getValue();
			double p1 = singleHashMap.get(entry.getKey().charAt(0));
			double p2 = singleHashMap.get(entry.getKey().charAt(1));
			double pAll = pDouble / (p1 * p2);
			allHashMap.put(entry.getKey(), pAll);
//			System.out.println(entry.getKey() + "-" + dcmFmt.format(pAll));
		}

		ByValueComparator bvc = new ByValueComparator(allHashMap);
		TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
		sorted_map.putAll(allHashMap);
		for (String name : sorted_map.keySet()) {
			System.out.println(name + "--" + dcmFmt.format(allHashMap.get(name)));
		}
	}

	// 实现HashMap按Velue顺序排序
	static class ByValueComparator implements Comparator<String> {
		HashMap<String, Double> base_map;

		public ByValueComparator(HashMap<String, Double> base_map) {
			this.base_map = base_map;
		}

		public int compare(String arg0, String arg1) {
			if (!base_map.containsKey(arg0) || !base_map.containsKey(arg1)) {
				return 0;
			}

			if (base_map.get(arg0) > base_map.get(arg1)) {
				return 1;
			} else if (base_map.get(arg0) == base_map.get(arg1)) {
				return 0;
			} else {
				return -1;
			}
		}
	}
}
