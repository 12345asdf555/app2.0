package com.example.wifilisttest;

import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Sampler.Value;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.example.wifilisttest.MySQLiteOpenHelper;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.example.wifilisttest.R;
import com.example.wifilisttest.ProgressWheel;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class Trans extends Activity {

	private ProgressWheel pwTwo;
	boolean wheelRunning;
	long wheelProgress = 0;
	int pieProgress = 0;
	int progress = 0;
	int i;
	int total;
	String [] stringArr; 
	
	
	
	public int getI() {
		return i;
	}



	public void setI(int i) {
		this.i = i;
	}





	public int getTotal() {
		return total;
	}



	public void setTotal(int total) {
		this.total = total;
	}





	public WifiManager wifiManager;
	private Fragment1 fragement1;
	/**
     * 主 变量
     */

    // 主线程Handler
    // 用于将从服务器获取的消息显示出来
    private Handler mMainHandler;

    // Socket变量
    private Socket socket;

    // 线程池
    // 为了方便展示,此处直接采用线程池进行线程管理,而没有一个个开线程
    private ExecutorService mThreadPool;
    

    /**
     * 接收服务器消息 变量
     */
    // 输入流对象
    InputStream is;

    // 输入流读取器对象
    InputStreamReader isr ;
    BufferedReader br ;

    // 接收服务器发送过来的消息
    String response;


    /**
     * 发送消息到服务器 变量
     */
    // 输出流对象
    OutputStream outputStream;

    /**
     * 按钮 变量
     */

    
    protected SQLiteDatabase db;
    
    private MySQLiteOpenHelper sqlHelper;
    
    boolean flag = false;
	int now=0;
	int all=0;

    public void onCreate(Bundle savedInstanceState) {
    	ProgressWheel ProgressWheel = new ProgressWheel(this);  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans);
        sqlHelper = new MySQLiteOpenHelper(this, "StuDatabase.db", null, 2);
        ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
        
        pwTwo = (ProgressWheel) findViewById(R.id.progress_bar_two);
        
        new Thread(r).start();
       
        /**
         * 初始化操作
         */

/*
        *//**
         * 创建客户端 & 服务器的连接
         *//*
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 利用线程池直接开启一个线程 & 执行该线程
                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {*/
         	
        Button startBtn = (Button) findViewById(R.id.btn_start);
		startBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!wheelRunning) {
					wheelProgress = 0;
					pwTwo.resetCount();
					new Thread(a).start();
				}
			}
		});
    }
    
    
    
    public boolean onOptionsItemSelected(MenuItem item){
    	if(item.getItemId() == android.R.id.home){
    	    finish();
    	}
		return true;
    }
    
    
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //调用双击退出函数  
	       }  
	    return false;  
	}  private static Boolean isExit = false;  
	  
	private void exitBy2Click() {  
	    Timer tExit = null;  
	    if (isExit == false) {  
	        isExit = true; // 准备退出    
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false; // 取消退出  
	            }  
	        }, 20); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  
	  
	    } else {  
	        finish();  
	        System.exit(0);  
	    }  
	}
    
    
    public Runnable r = new Runnable() {
		public void run() {
				try {
					
                	String b="FE115555555555555555550EFD";
				    byte[] bb=new byte[b.length()/2];

					for (int i = 0; i < bb.length; i++)
					{
						String tstr1=b.substring(i*2, i*2+2);
						Integer k=Integer.valueOf(tstr1, 16);
						bb[i]=(byte)k.byteValue();
					}
                	
                    // 创建Socket对象 & 指定服务端的IP 及 端口号
                    socket = new Socket("192.168.1.8", 1001);
                    
                    
                    boolean j=socket.isConnected();
                    if(j){
                    	Toast.makeText(getApplicationContext(), "服务器连接成功", Toast.LENGTH_LONG).show();
                    }
                    else{
                    	Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_LONG).show();
                    }
                    
                    // 判断客户端和服务器是否连接成功
                    System.out.println(socket.isConnected());
                    
                  //发送消息
                    // 步骤1：从Socket 获得输出流对象OutputStream
                    // 该对象作用：发送数据
                    outputStream = socket.getOutputStream();

                    // 步骤2：写入需要发送的数据到输出流对象中
                    outputStream.write(bb);
                    // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                    // 步骤3：发送数据到服务端
                    outputStream.flush();
                       
                    //接收消息
                    // 步骤1：创建输入流对象InputStream
                    is = socket.getInputStream();
                    // 步骤2：创建输入流读取器对象 并传入输入流对象
                    // 该对象作用：获取服务器返回的数据
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

                    
                    //得到长度
                    String data = br.readLine();
                    int len = data.length();
                    
                    
                    //发送消息
                    // 步骤1：从Socket 获得输出流对象OutputStream
                    // 该对象作用：发送数据
                    outputStream = socket.getOutputStream();

                    // 步骤2：写入需要发送的数据到输出流对象中
                    outputStream.write(bb);
                    // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                    // 步骤3：发送数据到服务端
                    outputStream.flush();
                       
                    //接收消息
                    // 步骤1：创建输入流对象InputStream
                    is = socket.getInputStream();
                    // 步骤2：创建输入流读取器对象 并传入输入流对象
                    // 该对象作用：获取服务器返回的数据
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    
                    
                    byte[] datas = new byte[len]; 
                    is.read(datas);
                    String str = "";
                    for(int i=0;i<datas.length;i++){
                    	if(datas[i]<0){
                    		String r = Integer.toHexString(datas[i]+256);
                    		String rr=r.toUpperCase();
                        	System.out.print(rr);
                        	if(rr.length()==1)
                    			rr='0'+rr;
                    		str+=rr;
                    		System.out.print(str);
                    	}
                    	else{
                    		String r = Integer.toHexString(datas[i]);
                        	System.out.print(r);
                        	if(r.length()==1)
                    			r='0'+r;
                    		str+=r;
                    		System.out.print(str);
                    	}
                    }
                    String str1 = str.substring(5, 13);
                    pwTwo.setText("待采集数据"+ str1 +"条");
                    
                    pwTwo.postInvalidate();
                    
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Intent i2 = new Intent(Trans.this ,Buff.class);
                	startActivity(i2); 
                }
		}
	};      	
    

		public Runnable a = new Runnable() {
			public void run() {
				int u=0;
				int o=0;
				wheelRunning = true;
				while (wheelProgress < 360){
					try {
                    	String b="FE115555555555555555550EFD";
					    byte[] bb=new byte[b.length()/2];

						for (int i = 0; i < bb.length; i++)
						{
							String tstr1=b.substring(i*2, i*2+2);
							Integer k=Integer.valueOf(tstr1, 16);
							bb[i]=(byte)k.byteValue();
						}
                    	
                        // 创建Socket对象 & 指定服务端的IP 及 端口号
                        socket = new Socket("192.168.1.8", 1001);
                        
                        
                        boolean j=socket.isConnected();
                        if(j){
                        	Toast.makeText(getApplicationContext(), "服务器连接成功", Toast.LENGTH_LONG).show();
                        }
                        else{
                        	Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_LONG).show();
                        }
                        
                        // 判断客户端和服务器是否连接成功
                        System.out.println(socket.isConnected());
                        
                      //发送消息
                        // 步骤1：从Socket 获得输出流对象OutputStream
                        // 该对象作用：发送数据
                        outputStream = socket.getOutputStream();

                        // 步骤2：写入需要发送的数据到输出流对象中
                        outputStream.write(bb);
                        // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                        // 步骤3：发送数据到服务端
                        outputStream.flush();
                           
                        //接收消息
                        // 步骤1：创建输入流对象InputStream
                        is = socket.getInputStream();
                        // 步骤2：创建输入流读取器对象 并传入输入流对象
                        // 该对象作用：获取服务器返回的数据
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

                        
                        //得到长度
                        String data = br.readLine();
                        int len = data.length();
                        
                        
                        //发送消息
                        // 步骤1：从Socket 获得输出流对象OutputStream
                        // 该对象作用：发送数据
                        outputStream = socket.getOutputStream();

                        // 步骤2：写入需要发送的数据到输出流对象中
                        outputStream.write(bb);
                        // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                        // 步骤3：发送数据到服务端
                        outputStream.flush();
                           
                        //接收消息
                        // 步骤1：创建输入流对象InputStream
                        is = socket.getInputStream();
                        // 步骤2：创建输入流读取器对象 并传入输入流对象
                        // 该对象作用：获取服务器返回的数据
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                        
                        
                        byte[] datas = new byte[len]; 
                        is.read(datas);
                        String str = "";
                        for(int i=0;i<datas.length;i++){
                        	if(datas[i]<0){
                        		String r = Integer.toHexString(datas[i]+256);
                        		String rr=r.toUpperCase();
                            	System.out.print(rr);
                            	if(rr.length()==1)
                        			rr='0'+rr;
                        		str+=rr;
                        		System.out.print(str);
                        	}
                        	else{
                        		String r = Integer.toHexString(datas[i]);
                            	System.out.print(r);
                            	if(r.length()==1)
                        			r='0'+r;
                        		str+=r;
                        		System.out.print(str);
                        	}
                        }
                        String str1 = str.substring(0, 4);
                        String str2 = str.substring(24, 26);
                        String str3 = str1+str2;
                        
                        //得到回复指令，发送新指令接收数据
                        if(str3.equals("FE12FD")){
                        	
                        	long time=System.currentTimeMillis();//获取系统时间的10位的时间戳

                            String  timestr=String.valueOf(time);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss",
                                    Locale.getDefault());
                            String time1 = sdf.format(time);
                            String codeb="";
                            String code2="";
                            for (int i = 0; i < time1.length()/2; i++)
							{
								String tstr1=time1.substring(i*2, i*2+2);
								Integer k=Integer.valueOf(tstr1);
								codeb=Integer.toHexString(k);
								codeb=codeb.toUpperCase();
								if(codeb.length()==1)
                        			codeb='0'+codeb;
								code2+=codeb;
							}
 
                            
                            String code1="FE13";
                            String code3="55555555FD";
                            
                        	
                        	String b1=code1+code2+code3;
						    byte[] bb1=new byte[b1.length()/2];

							for (int i = 0; i < bb1.length; i++)
							{
								String tstr1=b1.substring(i*2, i*2+2);
								Integer k=Integer.valueOf(tstr1, 16);
								bb1[i]=(byte)k.byteValue();
							}
							
							 // 创建Socket对象 & 指定服务端的IP 及 端口号
                            socket = new Socket("192.168.1.8", 1001);
                            
                            
                            boolean j1=socket.isConnected();
                            if(j1){
                            	Toast.makeText(getApplicationContext(), "服务器连接成功", Toast.LENGTH_LONG).show();
                            }
                            else{
                            	Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_LONG).show();
                            }
                            
                            // 判断客户端和服务器是否连接成功
                            System.out.println(socket.isConnected());
                        	
							//发送消息
                            // 步骤1：从Socket 获得输出流对象OutputStream
                            // 该对象作用：发送数据
                            outputStream = socket.getOutputStream();

                            // 步骤2：写入需要发送的数据到输出流对象中
                            outputStream.write(bb1);
                            // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                            // 步骤3：发送数据到服务端
                            outputStream.flush();
                               
                            //接收消息
                            // 步骤1：创建输入流对象InputStream
                            is = socket.getInputStream();
                            // 步骤2：创建输入流读取器对象 并传入输入流对象
                            // 该对象作用：获取服务器返回的数据
                            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

                            
                            //得到长度
                            String data1 = br.readLine();
                            int i3 = data1.length();
                            
                            
                            //发送消息
                            // 步骤1：从Socket 获得输出流对象OutputStream
                            // 该对象作用：发送数据
                            outputStream = socket.getOutputStream();

                            // 步骤2：写入需要发送的数据到输出流对象中
                            outputStream.write(bb1);
                            // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                            // 步骤3：发送数据到服务端
                            outputStream.flush();
                               
                            //接收消息
                            // 步骤1：创建输入流对象InputStream
                            is = socket.getInputStream();
                            // 步骤2：创建输入流读取器对象 并传入输入流对象
                            // 该对象作用：获取服务器返回的数据
                            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            
                            String strdata = "";
                            byte[] datas1 = new byte[i3]; 
                            is.read(datas1);
                            for(int i=0;i<datas1.length;i++){
                            	
                            	//判断为数字还是字母，若为字母+256取正数
                            	if(datas1[i]<0){
                            		String r = Integer.toHexString(datas1[i]+256);
                            		String rr=r.toUpperCase();
                                	System.out.print(rr);
                                	//数字补为两位数
                                	if(rr.length()==1){
                            			rr='0'+rr;
                                	}
                                	//strdata为总接收数据
                            		strdata += rr;
                            		
                            	}
                            	else{
                            		String r = Integer.toHexString(datas1[i]);
                                	System.out.print(r);
                                	if(r.length()==1)
                            			r='0'+r;
                            		strdata+=r;	
                            		
                            	}
                            }
                            
                            response=strdata;

                            SQLiteDatabase db = sqlHelper.getWritableDatabase();
                            String inSql;
                            String [] stringArr = strdata.split("FD");
                            int mid=1;
                            for(int i =0;i < stringArr.length;i++)
            		        {
                            	if(stringArr[i].length()>30){
                            		String electricity = stringArr[i].subSequence(5, 9).toString();
	                                String voltage = stringArr[i].subSequence(9, 13).toString();
	                                String sensor_Num = stringArr[i].subSequence(13, 17).toString();
	                                String machine_id = stringArr[i].subSequence(17, 21).toString();
	                                String welder_id = stringArr[i].subSequence(21, 25).toString();
	                                String code = stringArr[i].subSequence(25, 33).toString();
	                                String year = stringArr[i].subSequence(33, 35).toString();
	                                String month = stringArr[i].subSequence(35, 37).toString();
	                                String day = stringArr[i].subSequence(37, 39).toString();
	                                String hour = stringArr[i].subSequence(39, 41).toString();
	                                String minute = stringArr[i].subSequence(41, 43).toString();
	                                String second = stringArr[i].subSequence(43, 45).toString();
	                                String status = stringArr[i].subSequence(45, 47).toString();
	                                //String status = "01";
	
	                                inSql = "insert into Tenghan(electricity,voltage,sensor_Num,machine_id,welder_id,code,year,month,day,hour,minute,second,status) "
	                                		+ "values('"+ electricity +"','" + voltage + "','" + sensor_Num + "'"
	                                				+ ","+ "'" + machine_id + "','" + welder_id + "','" + code + "'"
	                                						+ ",'" + year + "','" + month + "','" + day + "','" + hour + "'"
	                                								+ ",'" + minute + "','" + second + "','" + status + "')";
	                                db.execSQL(inSql);
	                                
	                                try {
	                					Thread.sleep(200);
	                				} catch (InterruptedException p) {
	                					p.printStackTrace();
	                				}
	                                                
	                                int rolldata=Math.round(((float) mid / stringArr.length ) * 360);
                                    pwTwo.setProgress(rolldata);
                                    wheelProgress+=rolldata;
                                    mid++;
	                                
                            	}
                            	else{
                            		String b2="FE165555555555555555550EFD";
        						    byte[] bb2=new byte[b1.length()/2];

        							for (int i2 = 0; i2 < bb2.length; i++)
        							{
        								String tstr1=b2.substring(i2*2, i2*2+2);
        								Integer k=Integer.valueOf(tstr1, 16);
        								bb2[i]=(byte)k.byteValue();
        							}
        							
        							 // 创建Socket对象 & 指定服务端的IP 及 端口号
                                    socket = new Socket("192.168.1.8", 1001);
                                    
                                    
                                    boolean j2=socket.isConnected();
                                    if(j2){
                                    	Toast.makeText(getApplicationContext(), "服务器连接成功", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                    	Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_LONG).show();
                                    }
                                    
                                    // 判断客户端和服务器是否连接成功
                                    System.out.println(socket.isConnected());
                                	
        							//发送消息
                                    // 步骤1：从Socket 获得输出流对象OutputStream
                                    // 该对象作用：发送数据
                                    outputStream = socket.getOutputStream();

                                    // 步骤2：写入需要发送的数据到输出流对象中
                                    outputStream.write(bb2);
                                    // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                                    // 步骤3：发送数据到服务端
                                    outputStream.flush();
                                       
                                    //接收消息
                                    // 步骤1：创建输入流对象InputStream
                                    is = socket.getInputStream();
                                    // 步骤2：创建输入流读取器对象 并传入输入流对象
                                    // 该对象作用：获取服务器返回的数据
                                    br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

                            	}
                            	
            		        }
                            
                        }
                        else{
                        	
                        }
                        
                       /* if(str.length()!=36)
                        {
                            //数据写入数据库
                            SQLiteDatabase db = sqlHelper.getWritableDatabase();
                            String inSql;
                            String [] stringArr = str.split(" ");
            		        for(int i =0;i < stringArr.length;i=i+6)
            		        {
            		        	int electricity = Integer.parseInt(stringArr[i]);
                                int voltage = Integer.parseInt(stringArr[i+1]);
                                int sensor_Num = Integer.parseInt(stringArr[i+2]);
                                int welder_id = Integer.parseInt(stringArr[i+3]);
                                int machine_id = Integer.parseInt(stringArr[i+4]);
                                int status = Integer.parseInt(stringArr[i+5]);

                                inSql = "insert into Tenghan(electricity,voltage,sensor_Num,welder_id,machine_id,status) "
                                		+ "values('"+ electricity +"','" + voltage + "','" + sensor_Num + "',"
                                				+ "'" + welder_id + "','" + machine_id + "','" + status + "')";

                                        db.execSQL(inSql);
                                        
            		        }
                        }*/
                        
                      /*//写进数据库
            			if(strdatanew.length()>20){
            				SQLiteDatabase db = sqlHelper.getWritableDatabase();
                            String inSql;
            		        	int electricity = Integer.parseInt(strdatanew.subSequence(5, 9).toString());
                                int voltage = Integer.parseInt(strdatanew.subSequence(9, 13).toString());
                                int sensor_Num = Integer.parseInt(strdatanew.subSequence(13, 17).toString());
                                int machine_id = Integer.parseInt(strdatanew.subSequence(17, 21).toString());
                                int welder_id = Integer.parseInt(strdatanew.subSequence(21, 25).toString());
                                int code = Integer.parseInt(strdatanew.subSequence(25, 33).toString());
                                int year = Integer.parseInt(strdatanew.subSequence(33, 35).toString());
                                int month = Integer.parseInt(strdatanew.subSequence(35, 37).toString());
                                int day = Integer.parseInt(strdatanew.subSequence(37, 39).toString());
                                int hour = Integer.parseInt(strdatanew.subSequence(39, 41).toString());
                                int minute = Integer.parseInt(strdatanew.subSequence(41, 43).toString());
                                int second = Integer.parseInt(strdatanew.subSequence(43, 45).toString());
                                int status = Integer.parseInt(strdatanew.subSequence(45, 47).toString());

                                inSql = "insert into Tenghan(electricity,voltage,sensor_Num,welder_id,machine_id,status) "
                                		+ "values('"+ electricity +"','" + voltage + "','" + sensor_Num + "'"
                                				+ ","+ "'" + machine_id + "','" + welder_id + "','" + code + "'"
                                						+ ",'" + year + "','" + month + "','" + day + "','" + hour + "'"
                                								+ ",'" + minute + "','" + second + "','" + status + "')";
                                db.execSQL(inSql);
            			}*/
                        
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
					
					
					
					
					
					/*		
                     	String s="2";
                     	
                         // 创建Socket对象 & 指定服务端的IP 及 端口号
                         try {
                        	 WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
                        	 WifiInfo wifiInfo = wifiManager.getConnectionInfo();
 							 wifiInfo.getIpAddress();
							 socket = new Socket("192.168.8.112", 5555);
							

							
							boolean w=socket.isConnected();
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							Intent i2 = new Intent(Trans.this ,Buff.class);
     	                	startActivity(i2); 
     	                	u++;
     	                	break;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							Intent i2 = new Intent(Trans.this ,Buff.class);
     	                	startActivity(i2); 
     	                	u++;
     	                	break;
						}
                         
                         
                         try{
	                         boolean w=socket.isConnected();
	                         System.out.println(w);
	                         if(w){
	                        	 
	                        	 Toast.makeText(getApplicationContext(), "服务器连接成功", Toast.LENGTH_LONG).show();
	                        	 
	                        	 // 判断客户端和服务器是否连接成功
	                             System.out.println(socket.isConnected());
	                             
	                           //发送消息
	                             // 步骤1：从Socket 获得输出流对象OutputStream
	                             // 该对象作用：发送数据
	                             try {
									outputStream = socket.getOutputStream();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	
	                             // 步骤2：写入需要发送的数据到输出流对象中
	                             try {
									outputStream.write((s+"\n").getBytes("utf-8"));
								} catch (UnsupportedEncodingException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	                             // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞
	
	                             // 步骤3：发送数据到服务端
	                             try {
									outputStream.flush();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	                             
	                             
	                             //接收消息
	                             // 步骤1：创建输入流对象InputStream
	                             try {
									is = socket.getInputStream();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	                             // 步骤2：创建输入流读取器对象 并传入输入流对象
	                             // 该对象作用：获取服务器返回的数据
	                             isr = new InputStreamReader(is);
	                             br = new BufferedReader(isr);
	
	                             // 步骤3：通过输入流读取器对象 接收服务器发送过来的数据
	                             // 发送过来数据处理
	                             try {
									response = br.readLine();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	                             String str = response;
	
	
	                             // 步骤4:通知主线程,将接收的消息显示到界面
	                             Message msg = Message.obtain();
	                             msg.what = 0;
	                             
	                             if(str.length()!=36)
	                             {
	    	                            //数据写入数据库
	    	                            SQLiteDatabase db = sqlHelper.getWritableDatabase();
	    	                            String inSql;
	    	                            stringArr = str.split(" ");
	    	                            total=stringArr.length;
	    	                            pwTwo.setText("未采集"+stringArr.length/6+"条");
	    	            		        for(i =0;i < stringArr.length;i=i+6)
	    	            		        {
	    	                			    int electricity = Integer.parseInt(stringArr[i]);
	    	                                int voltage = Integer.parseInt(stringArr[i+1]);
	    	                                int sensor_Num = Integer.parseInt(stringArr[i+2]);
	    	                                int welder_id = Integer.parseInt(stringArr[i+3]);
	    	                                int machine_id = Integer.parseInt(stringArr[i+4]);
	    	                                int status = Integer.parseInt(stringArr[i+5]);
	    	
	    	                                inSql = "insert into Tenghan(electricity,voltage,sensor_Num,welder_id,machine_id,status) "
	    	                                		+ "values('"+ electricity +"','" + voltage + "','" + sensor_Num + "',"
	    	                                				+ "'" + welder_id + "','" + machine_id + "','" + status + "')";
	
	    	                                        db.execSQL(inSql);

	    	                                        i+=6;
	    	                                        int b=Math.round(((float) i / stringArr.length ) * 360);
	    	                                        pwTwo.setProgress(b);
	    	                                        wheelProgress+=b;
	  	                    					
	    	                    					   
	    	            		        }
	    	            		        
	                             }*/
					}
	                       
				wheelRunning = false;
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(u==0){
	                Intent i2 = new Intent(Trans.this ,Ok1.class);
	            	startActivity(i2); 
				}
			}
		};      	
              
                        
    /*                }
                });

            }
        });
*/

    }

