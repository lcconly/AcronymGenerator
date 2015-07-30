
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CiteSeerx_search {
	public static String write_file_name="result.txt";
	public static String read_file_name="b.txt";
	public static String leibie;
	public static String num;
	//static String result_last;
	public static String matches="<h3>[ \n]*<a class=\"[ a-zA-Z_\"=/?0-9\\.&;]*>(([,:a-zA-Z \\-\n])|(<em>)|(</em>))*</a>[ \n]*</h3>";
	public static Callable<String> call = new Callable<String>() {
		public String call() {
			StringBuffer temp = new StringBuffer();
			try {
				String url = "http://citeseerx.ist.psu.edu/search?q="+leibie+
						"&t=doc&sort=rlv&start="+num;
				HttpURLConnection uc = (HttpURLConnection)new URL(url).
						openConnection();
				uc.setConnectTimeout(10000);
				uc.setDoOutput(true);
				uc.setRequestMethod("GET");
				uc.setUseCaches(false);
				DataOutputStream out = new DataOutputStream(uc.getOutputStream());

				// 要传的参数
				String s = URLEncoder.encode("ra", "GB2312") + "=" +
                       URLEncoder.encode(leibie, "GB2312");
				s += "&" + URLEncoder.encode("keyword", "GB2312") + "=" +
                    URLEncoder.encode(num, "GB2312");
				// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
				out.writeBytes(s);
				out.flush();
				out.close();
				InputStream in = new BufferedInputStream(uc.getInputStream());
				Reader rd = new InputStreamReader(in, "Gb2312");
				int c = 0;
				while ((c = rd.read()) != -1) {
					temp.append((char) c);
				}

				//System.out.println(temp.toString());
				in.close();

			} catch (Exception e) {
				e.printStackTrace();
				//return temp.toString();
			}
			//result_last=temp.toString();
			return temp.toString();
		}

	};
	@SuppressWarnings({ "resource", "static-access" })
	public static void main(String[] a) throws IOException{
		String result;
		File write_file=new File(write_file_name);
		if(!write_file.exists())
			try {
				write_file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		File read_file=new File(read_file_name);
		if(!read_file.exists())
			throw new FileNotFoundException();
		BufferedReader br=new BufferedReader(new FileReader(read_file));
        FileOutputStream out=new FileOutputStream(write_file,true);        
        PrintStream p=new PrintStream(out);
        FileOutputStream out_process=new FileOutputStream("process.txt",true);        
		PrintStream p_process=new PrintStream(out_process);
        String temp=null;
        ExecutorService exec = Executors.newFixedThreadPool(1);  
        //hashmap用来判断重复
        HashMap<String, Integer> aMap=new HashMap<String, Integer>();
		File read_file_del=new File(write_file_name);
		if(!read_file_del.exists())
			throw new FileNotFoundException();
		BufferedReader br_del=new BufferedReader(new FileReader(read_file_del));
		String temp_del=null;
		while((temp_del=br_del.readLine())!=null){
			aMap.put(temp_del, 0);
		}
        while((temp=br.readLine())!=null){
        	int tag=1;
        	int count=0;
        	for(int i=0;i<500;i+=10){
        		try {
					Thread.currentThread().sleep(1500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		System.out.println("ok  "+  temp+"  num:  "+i);
        		p_process.println("ok  "+  temp+"  num:  "+i);
        		//result=CiteSeerx_search.cc(temp,i+"");
        		leibie=temp;
        		num=i+"";
        		//页面加载超时处理
        		try {  
                    Future<String> future = exec.submit(call);  
                    result=future.get(1000 * 20, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒  
                    //System.out.println("任务成功返回:" + result);  
                } catch (TimeoutException ex) {  
                    System.out.println("处理超时啦....");  
                    //ex.printStackTrace();
                	exec = Executors.newFixedThreadPool(1);
                	continue;
                } catch (Exception e) {  
                    System.out.println("处理失败.");  
                    //e.printStackTrace();
                	exec = Executors.newFixedThreadPool(1);
                	continue;
                }  
        		System.out.println("get  "+  temp+"  num:  "+i);
				//result=result_last;
        		if(!result.contains("result_list")){
					tag=0;
					System.out.println("-----------------");
				}
				if(tag==0) break;
				System.out.println("start  "+  temp+"  num:  "+i);			
	        	Pattern pa=Pattern.compile(matches);
	        	Matcher m=pa.matcher(result);
	        	int tag_del=0;
	        	while(m.find()){
	        		//System.out.println("match");	
	        		//字符串替换规范化
	        		String new_re=m.group();
	        		String[] spirt=new_re.split("\n");
	        		spirt[3]=spirt[3].replaceAll("<em>","");
	        		spirt[3]=spirt[3].replaceAll("</em>","");
	        		for(int j=0;j<spirt[3].length();j++){
	        			if(spirt[3].charAt(j)!=' '){
	                    	spirt[3]=spirt[3].substring(j,spirt[3].length());
	                    	break;
	        			}
	        		}
	        		//System.out.println(spirt[3]);
	        		//处理重复的字符串
	        		tag_del=1;
	        		/*while((temp_del=br_del.readLine())!=null){
	        			if(temp_del.equals(spirt[3])){
	        				tag_del=0;
	        				break;
	        			}
	        		}*/
	        		if(aMap.containsKey(spirt[3]))
	        			tag_del=0;
	        		if(tag_del==1){
	        			p.println(spirt[3]);
	        			count=0;
	        			System.out.println(spirt[3]);
	        			aMap.put(spirt[3], 0);
	        		}
	        	}
	        	if(tag_del==0)
	        		count++;
	        	if(count>3){
	        		count=0;
	        		break;
	        	}
	        	System.out.println("end  "+  temp+"  num:  "+i);	
			}
        }
        out.close();
	}
}
