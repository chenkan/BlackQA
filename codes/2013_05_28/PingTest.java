import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;


public class PingTest extends Thread {
	
	public static AtomicInteger successNum = new AtomicInteger(0);
	
	private String host;
	
	public PingTest(String ip){
		this.host = ip;
	}
	
	public void run(){
		try {
			/*int timeOut = 4000; 
			InetAddress address = InetAddress.getByName(host);
			if (address.isReachable(timeOut)) {
				System.out.println(host + " OK");
				successNum.getAndIncrement();
			} else {
				System.out.println(host + " LOSS");
			}
			Thread.sleep(10);*/
			Runtime r = Runtime.getRuntime();
			//创建一个ping命令进程
			Process p = r.exec("cmd /c ping -n 1 -w 100 " + host);
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			//System.out.println(sb.toString());
			if (sb.indexOf("TTL") != -1){
				System.out.println(host + " OK");
				successNum.getAndIncrement();
			}else {
				System.out.println(host + " LOSS");
			}
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		long begin = System.currentTimeMillis();
		
		String hostPrefix = "192.168.144.";
		int hostNum = 252;
		PingTest[] pt = new PingTest[hostNum];
		
		for(int i  = 0; i < hostNum; i++){
			int index = i + 2;
			pt[i] = new PingTest(hostPrefix + index);
			pt[i].start();
		}
		
		for(int i  = 0; i < hostNum; i++){
			pt[i].join();
		}
		
		long duration = System.currentTimeMillis() - begin;
		System.out.println("Duration: " + duration / 1000.0);
		System.out.println("Success: " + PingTest.successNum.get());
	}

}
