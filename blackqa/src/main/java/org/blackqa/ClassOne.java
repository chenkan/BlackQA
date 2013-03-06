package org.blackqa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class ClassOne {

	public static void main(String[] args) throws Exception {
		download();

	}

	public static void select(String temp, HashSet<String> has) {

		String regex = "[a-z0-9]+\\.163\\.com";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(temp);

		int i = 0;
		while (m.find()) {
			has.add(m.group());
		}

		ArrayList<String> list = new ArrayList<String>();
		list.addAll(has);

		Collections.sort(list);

		for (String asd : list)
			System.out.println(i++ + " - " + asd);

	}

	public static void download() throws Exception {

		HttpClient httpclient = new DefaultHttpClient();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet("http://www.163.com/");
			//System.out.println("executing request " + httpget.getURI());
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			//System.out.println("--------------------------------------");
			//System.out.println(response.getStatusLine());
			if (entity != null) {
				String temp = EntityUtils.toString(entity);
				//System.out.println("Response content: " + temp);
				HashSet<String> has = new HashSet<String>();
				
				select(temp, has);
			}
			//System.out.println("------------------------------------");
		} finally {
			// 关闭连接,释放资源
			httpclient.getConnectionManager().shutdown();
		}

	}

}
