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

public class ClassTwo {

	private static int textLength = 0;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filename = "e://result_text.txt";
		//singleByte(filename);
		//doubleByte(filename);
		rate(singleByte(filename), doubleByte(filename));
	}
	
	private static Map singleByte(String filename) throws IOException{
		char[] cbuf = new char[1];
		StringBuffer sb = new StringBuffer();
		
		FileInputStream fis = new FileInputStream(filename); 
		InputStreamReader fr = new InputStreamReader(fis, "UTF-8"); 
		
		while(fr.read(cbuf) != -1){
			if (cbuf[0] == 32 || cbuf[0] == 13 || cbuf[0] == 10)
				continue;
			sb.append(cbuf);
		}
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		String tmp = null;
		for(int i = 0; i < sb.length(); i++){
			tmp = sb.substring(i, i+1);
			
			if(!map.containsKey(tmp.trim()))
				map.put(tmp, 1);
			else
				map.put(tmp, map.get(tmp) + 1);
		   			
			
		}
		
		/*Iterator iterator_1 = map.keySet().iterator();    
        while (iterator_1.hasNext()) {    
        	String key = (String)iterator_1.next();    
        	System.out.println(key +  ": " + map.get(key));    
        }*/
        
        return map;

	}
	
	private static Map doubleByte(String filename) throws IOException{
		char[] cbuf = new char[1];
		StringBuffer sb = new StringBuffer();
		
		/*File file = new File(filename);
		FileReader fr = new FileReader(file);*/
		FileInputStream fis = new FileInputStream(filename); 
		InputStreamReader fr = new InputStreamReader(fis, "UTF-8"); 
		
		while(fr.read(cbuf) != -1){
			if (cbuf[0] == 32 || cbuf[0] == 13 || cbuf[0] == 10)
				continue;
			sb.append(cbuf);
		}
		
		textLength = sb.length();
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		String tmp = null;
		for(int i = 0; i < sb.length() - 1; i++){
			/*System.out.print(sb.charAt(i));
			if(i+1 < sb.length())
				System.out.println(sb.charAt(i+1));*/

			tmp = sb.substring(i, i+2);
			//System.out.println(tmp.trim());
			
			if(!map.containsKey(tmp.trim()))
				map.put(tmp, 1);
			else
				map.put(tmp, map.get(tmp) + 1);   			
			
		}
		
		/*Iterator iterator_1 = map.keySet().iterator();    
        while (iterator_1.hasNext()) {    
        	String key = (String)iterator_1.next();    
        	System.out.println(key +  ": " + map.get(key));    
        }*/
        
        return map;

	}
	
	private static void rate(Map singleMap, Map doubleMap){
		
		TreeMap<String, Double> mapResult = new TreeMap<String, Double>();
		
		Iterator iterator_1 = doubleMap.keySet().iterator();
		
        while (iterator_1.hasNext()) {   
        	String doubleKey = (String)iterator_1.next();    
        	Integer doubleNum = (Integer) doubleMap.get(doubleKey);
        	
        	Integer singleOne = (Integer)singleMap.get(doubleKey.substring(0, 1));
        	Integer singleTwo = (Integer)singleMap.get(doubleKey.substring(1, 2));
        	
        	double rate = (double)doubleNum * (double)textLength / (singleOne * singleTwo);
        	
        	mapResult.put(doubleKey, rate);

        }
        
        /*Iterator iterator_2 = mapResult.keySet().iterator();    
        while (iterator_2.hasNext()) {    
        	String key = (String)iterator_2.next();    
        	System.out.println(key +  ": " + mapResult.get(key));    
        }*/
        
        //sort
        TreeMap<String, Double> keyfreqs = mapResult;

		ArrayList<Entry<String, Double>> l = new ArrayList<Entry<String, Double>>(
				keyfreqs.entrySet());

		Collections.sort(l, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				return (o2.getValue() > o1.getValue() ? 0 : 1);
			}

		});

		for (Entry<String, Double> e : l) {
			System.out.println(e.getKey() + " : " + e.getValue());
		}
        
	}
	
}
