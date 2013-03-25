package org.blackqa;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * calculate gathering rate
 * @author jinfeng
 * @version 0.2
 */
public class GatheringRate {
	private static long textLength = 0;

	public static void main(String[] args) throws IOException {
		String filename = "result_text.txt";
		rate(singleByte(filename), doubleByte(filename));
	}

	/**
	 * calculate single byte probability
	 * @param filename
	 * @return Map<String, Integer>
	 * @throws IOException
	 */
	private static Map<String, Integer> singleByte(String filename)
			throws IOException {
		char[] cbuf = new char[1];
		StringBuffer sb = new StringBuffer();

		FileInputStream fis = new FileInputStream(filename);
		InputStreamReader fr = new InputStreamReader(fis, "UTF-8");

		while (fr.read(cbuf) != -1) {
			// clear white space, CR, LF
			if (cbuf[0] == 32 || cbuf[0] == 13 || cbuf[0] == 10)
				continue;
			sb.append(cbuf);
		}

		Map<String, Integer> map = new HashMap<String, Integer>();
		String tmp = null;
		long length = sb.length();

		for (int i = 0; i < length; i++) {
			tmp = sb.substring(i, i + 1);
			if (!map.containsKey(tmp.trim()))
				map.put(tmp, 1);
			else
				map.put(tmp, map.get(tmp) + 1);
		}

		return map;
	}

	/**
	 * calculate double byte probability
	 * @param filename
	 * @return Map<String, Integer>
	 * @throws IOException
	 */
	private static Map<String, Integer> doubleByte(String filename)
			throws IOException {
		char[] cbuf = new char[1];
		StringBuffer sb = new StringBuffer();

		FileInputStream fis = new FileInputStream(filename);
		InputStreamReader fr = new InputStreamReader(fis, "UTF-8");

		while (fr.read(cbuf) != -1) {
			// clear white space, CR, LF
			if (cbuf[0] == 32 || cbuf[0] == 13 || cbuf[0] == 10)
				continue;
			sb.append(cbuf);
		}

		textLength = sb.length();

		Map<String, Integer> map = new HashMap<String, Integer>();
		String tmp = null;
		for (int i = 0; i < textLength - 1; i++) {
			tmp = sb.substring(i, i + 2);
			if (!map.containsKey(tmp.trim()))
				map.put(tmp, 1);
			else
				map.put(tmp, map.get(tmp) + 1);
		}

		return map;
	}

	/**
	 * calculate gathering rate
	 * @param singleMap
	 * @param doubleMap
	 */
	private static void rate(Map<String, Integer> singleMap,
			Map<String, Integer> doubleMap) {
		TreeMap<String, Double> mapResult = new TreeMap<String, Double>();
		Iterator<String> iterator = doubleMap.keySet().iterator();

		while (iterator.hasNext()) {
			String doubleKey = (String) iterator.next();
			Integer doubleNum = (Integer) doubleMap.get(doubleKey);
			Integer singleOne = (Integer) singleMap.get(doubleKey.substring(0, 1));
			Integer singleTwo = (Integer) singleMap.get(doubleKey.substring(1, 2));

			double rate = (double) doubleNum * (double) textLength
					/ (singleOne * singleTwo);
			mapResult.put(doubleKey, rate);
		}

		TreeMap<String, Double> keyfreqs = mapResult;
		ArrayList<Entry<String, Double>> listRate = new ArrayList<Entry<String, Double>>(
				keyfreqs.entrySet());

		Collections.sort(listRate, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				return (o2.getValue() > o1.getValue() ? 0 : 1);
			}
		});

		for (Entry<String, Double> entryRate : listRate) {
			System.out.println(entryRate.getKey() + " : " + entryRate.getValue());
		}
	}

}
