package com.example.wifilisttest;

import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.example.wifilisttest.MySQLiteOpenHelper;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.example.wifilisttest.R;
import com.example.wifilisttest.ProgressWheel;

public class Trans2 extends Activity {

	private SocketChannel socketChannel = null;
	private ProgressWheel pwTwo;
	boolean wheelRunning;
	int wheelProgress = 0, pieProgress = 0;
	int progress = 0;
	
	
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
	int i=0;
	int count=0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans2);
        sqlHelper = new MySQLiteOpenHelper(this, "StuDatabase.db", null, 2);
        pwTwo = (ProgressWheel) findViewById(R.id.progress_bar_two2);
        Button startBtn1 = (Button) findViewById(R.id.btn_start2);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
       
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        
        
        String sql = "select count(*) from Tenghan";  
        Cursor cursor = db.rawQuery(sql, null);  
        cursor.moveToFirst();  
        long count = cursor.getLong(0);  
        
        
        /*Cursor c = db.query("Tenghan", null, null, null, null, null, null, null);
        	if (c.moveToFirst()) {
        		do {
        			String strValue= c.getString(12);
        			if(strValue.equals("3")){
    					count++;
        			}
        			//count++;
        		}while(c.moveToNext());
        }
        	String cou=Integer.toString(count);*/
        	pwTwo.setText("待上传数据"+count+"条");
        	if(count!=0){
        		startBtn1.setEnabled(true);
        	}
        	/*pwTwo.postInvalidate();*/

        
        
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
         	
        
		startBtn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!wheelRunning) {
					wheelProgress = 0;
					pwTwo.resetCount();
					new Thread(j).start();
				}
			}
		});
    }
    
    @Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //调用双击退出函数  
	       }  
	    return false;  
	}  
	/** 
	 * 双击退出函数 
	 */  
	private static Boolean isExit = false;  
	  
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
    
    public boolean onOptionsItemSelected(MenuItem item){
    	if(item.getItemId() == android.R.id.home){
    	    finish();
    	}
		return true;
    }
                    	
		public Runnable j = new Runnable() {
			public void run() {
				int u=0;
				wheelRunning = true;
				boolean w=false;
		        try{
				while (wheelProgress < 360){

            	
					
					/*try{

                	String b="FE225555555555555555550EFD";
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
                    
                    if(str3.equals("FE23FD")){*/
                    	
                    	
                    	/* // 创建Socket对象 & 指定服务端的IP 及 端口号
                        try {
							socket = new Socket("10.1.12.168", 5555);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                        
                        boolean j=socket.isConnected();
                        if(j){
                        	Toast.makeText(getApplicationContext(), "服务器连接成功", Toast.LENGTH_LONG).show();
                        }
                        else{
                        	Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_LONG).show();
                        }
                        
                        // 判断客户端和服务器是否连接成功
                        System.out.println(socket.isConnected());*/
                    	
            	
                    String i="0";
                    String o="FE24";
                    String p="00000000FD";
                    String l="";
                    int b=0;
                    SQLiteDatabase db = sqlHelper.getWritableDatabase();
                    String inSql;
                    Cursor c = db.query("Tenghan", null, null, null, null, null, null, null);
		           	if (c.moveToFirst()) {
		                 do {
 			                    String electricity = c.getString(c.getColumnIndex("electricity"));
 			                    if(electricity.length()!=4){
 			                    	int a=4-electricity.length();
 			                    	for(int ii=0;ii<a;ii++){
 			                    		electricity=i+electricity;
 			                    	}
 			                    }
 			                   String voltage = c.getString(c.getColumnIndex("voltage"));
 			                   if(voltage.length()!=4){
			                    	int a=4-voltage.length();
			                    	for(int ii=0;ii<a;ii++){
			                    		voltage=i+voltage;
			                    	}
			                    }
 			                   String sensor_Num = c.getString(c.getColumnIndex("sensor_Num"));
 			                   if(sensor_Num.length()!=4){
   			                    	int a=4-sensor_Num.length();
   			                    	for(int ii=0;ii<a;ii++){
   			                    		sensor_Num=i+sensor_Num;
   			                    	}
   			                    }
 			                   String machine_id = c.getString(c.getColumnIndex("machine_id"));
 			                   if(machine_id.length()!=4){
			                    	int a=4-machine_id.length();
			                    	for(int ii=0;ii<a;ii++){
			                    		machine_id=i+machine_id;
			                    	}
			                    }
 			                   String welder_id = c.getString(c.getColumnIndex("welder_id"));
 			                   if(welder_id.length()!=4){
			                    	int a=4-welder_id.length();
			                    	for(int ii=0;ii<a;ii++){
			                    		welder_id=i+welder_id;
			                    	}
			                    }
 			                   String code = c.getString(c.getColumnIndex("code"));
 			                   if(code.length()!=8){
			                    	int a=8-code.length();
			                    	for(int ii=0;ii<a;ii++){
			                    		code=i+code;
			                    	}
			                    }
 			                   String year = c.getString(c.getColumnIndex("year"));
 			                   if(year.length()!=2){
			                    	int a=2-year.length();
			                    	for(int ii=0;ii<a;ii++){
			                    		year=i+year;
			                    	}
			                    }
 			                   String month = c.getString(c.getColumnIndex("month"));
 			                   if(month.length()!=2){
			                    	int a=2-month.length();
			                    	for(int ii=0;ii<a;ii++){
			                    		month=i+month;
			                    	}
			                    }
 			                   String day = c.getString(c.getColumnIndex("day"));
 			                   if(day.length()!=2){
			                    	int a=2-day.length();
			                    	for(int ii=0;ii<a;ii++){
			                    		day=i+day;
			                    	}
			                    }
 			                   String hour = c.getString(c.getColumnIndex("hour"));
 			                   if(hour.length()!=2){
			                    	int a=2-hour.length();
			                    	for(int ii=0;ii<a;ii++){
			                    		hour=i+hour;
			                    	}
			                    }
 			                   String minute = c.getString(c.getColumnIndex("minute"));
 			                   if(minute.length()!=2){
			                    	int a=2-minute.length();
			                    	for(int ii=0;ii<a;ii++){
			                    		minute=i+minute;
			                    	}
			                    }
 			                   String second = c.getString(c.getColumnIndex("second"));
 			                   if(second.length()!=2){
			                    	int a=2-second.length();
			                    	for(int ii=0;ii<a;ii++){
			                    		second=i+second;
			                    	}
			                    }
 			                   String status = c.getString(c.getColumnIndex("status"));
 			                   if(status.length()!=2){
			                    	int a=2-status.length();
			                    	for(int ii=0;ii<a;ii++){
			                    		status=i+status;
			                    	}
			                    }
		                  
		
		                        l = l + o + electricity + voltage + sensor_Num 
		                        		+ machine_id + welder_id + code + year 
		                        		+ month + day + hour + minute + second + status + p;
		                        
		                        /*String sql = "update Tenghan set status = 01";   
		                        //执行SQL   
		                        db.execSQL(sql);*/

		                        b++;
			                    int d=Math.round(((float) b / c.getCount() ) * 360);
                                pwTwo.setProgress(d);
                                wheelProgress=d;   
                                
                                Thread.sleep(20);
                                
		                        //pwTwo.incrementProgress();
		            			//wheelProgress++;
		                        
                            
                            System.out.println(l);   
                        } while (c.moveToNext());
                    }
                    c.close();
                    
                    
                    try {    
 		            	if(socketChannel==null){
 		            		socketChannel = SocketChannel.open(); 
     		                SocketAddress socketAddress = new InetSocketAddress("192.168.21.100", 5555);    
     		                socketChannel.connect(socketAddress);
 		            	}
 		            	
 		                SendAndReceiveUtil.sendData(socketChannel, l); 
 		                    
 		                /*String msg = SendAndReceiveUtil.receiveData(socketChannel);    
 		                if(msg != null) 
 		                	System.out.println(msg);*/

 		            
 		            } catch (Exception ex) {    
 		                ex.printStackTrace();  
 		            }
                    
                    
                    
                    /*byte[] bb3=new byte[l.length()/2];
					for (int i1 = 0; i1 < bb3.length; i1++)
					{
						String tstr1=l.substring(i1*2, i1*2+2);
						Integer k=Integer.valueOf(tstr1, 16);
						bb3[i1]=(byte)k.byteValue();
					}
                    
                    try {
                    	//发送消息
                        // 步骤1：从Socket 获得输出流对象OutputStream
                        // 该对象作用：发送数据
                        outputStream = socket.getOutputStream();

                        // 步骤2：写入需要发送的数据到输出流对象中
                        outputStream.write(bb3);
                        // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                        // 步骤3：发送数据到服务端
                        outputStream.flush();
                        
                        response = l;

     		           
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                /*} catch (IOException e) {
                    e.printStackTrace();
                }*/
            
                    try {
    					Thread.sleep(1000);
    				} catch (InterruptedException p2) {
    					p2.printStackTrace();
    				}
    				
    				Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_LONG).show();
    	            Intent i2 = new Intent(Trans2.this ,Ok2.class);
    	            startActivity(i2); 
                    
                   /* try{

                    	String bb1="FE255555555555555555550EFD";
					    byte[] bbb1=new byte[bb1.length()/2];

						for (int i3 = 0; i3 < bbb1.length; i3++)
						{
							String tstr1=bb1.substring(i3*2, i3*2+2);
							Integer k=Integer.valueOf(tstr1, 16);
							bbb1[i3]=(byte)k.byteValue();
						}
                    	
                    	
                        // 创建Socket对象 & 指定服务端的IP 及 端口号
                        socket = new Socket("192.168.1.8", 1001);
                        
                        
                        
                        boolean j33=socket.isConnected();
                        if(j33){
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
                        outputStream.write(bbb1);
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
                        String datab1 = br.readLine();
                        int lenb1 = datab1.length();
                        
                        
                        //发送消息
                        // 步骤1：从Socket 获得输出流对象OutputStream
                        // 该对象作用：发送数据
                        outputStream = socket.getOutputStream();

                        // 步骤2：写入需要发送的数据到输出流对象中
                        outputStream.write(bbb1);
                        // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                        // 步骤3：发送数据到服务端
                        outputStream.flush();
                           
                        //接收消息
                        // 步骤1：创建输入流对象InputStream
                        is = socket.getInputStream();
                        // 步骤2：创建输入流读取器对象 并传入输入流对象
                        // 该对象作用：获取服务器返回的数据
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                        
                        
                        byte[] datasb1 = new byte[lenb1]; 
                        is.read(datasb1);
                        String strb1 = "";
                        for(int i2=0;i2<datasb1.length;i2++){
                        	if(datasb1[i2]<0){
                        		String r = Integer.toHexString(datasb1[i2]+256);
                        		String rr=r.toUpperCase();
                            	System.out.print(rr);
                            	if(rr.length()==1)
                        			rr='0'+rr;
                        		strb1+=rr;
                        		System.out.print(strb1);
                        	}
                        	else{
                        		String r = Integer.toHexString(datasb1[i2]);
                            	System.out.print(r);
                            	if(r.length()==1)
                        			r='0'+r;
                        		strb1+=r;
                        		System.out.print(strb1);
                        	}
                        }
                        String strb2 = strb1.substring(0, 4);
                        String strb3 = strb1.substring(24, 26);
                        String strb4 = strb2+strb3;
                        
                        if(strb4.equals("FE16FD")){
                        	socket.close();
                        }
                    
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
				
				
					
					
            	
                /*// 创建Socket对象 & 指定服务端的IP 及 端口号
                try {
                	WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
               	 	WifiInfo wifiInfo = wifiManager.getConnectionInfo();
					wifiInfo.getIpAddress();
					socket = new Socket("192.168.8.112", 5555);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
			        w=socket.isConnected();
			        if(w){
			           Toast.makeText(getApplicationContext(), "服务器连接成功", Toast.LENGTH_LONG).show();
			                	
			           System.out.println(socket.isConnected());
			                    
			           String q=null;
			           String k=null;
			           String l=null;
			           int h=0;
			                    
			           SQLiteDatabase db = sqlHelper.getWritableDatabase();
			           Cursor c = db.query("Tenghan", null, null, null, null, null, null);
			           if (c.moveToFirst()) {
			                do {
			                    String electricity = c.getString(c.getColumnIndex("electricity"));
			                    String voltage = c.getString(c.getColumnIndex("voltage"));
			                    String sensor_Num = c.getString(c.getColumnIndex("sensor_Num"));
			                    String welder_id = c.getString(c.getColumnIndex("welder_id"));
			                    String machine_id = c.getString(c.getColumnIndex("machine_id"));
			                    String status = c.getString(c.getColumnIndex("status"));
			
			                    if(h==0){
			                       q = " ";
			                       k = electricity + q + voltage + q + sensor_Num + q 
			                           + welder_id + q +machine_id + q +status;
			                       l = k ;
			                       h++;
			                           
			                       b++;
			                       int d=Math.round(((float) b / c.getCount() ) * 360);
                                   pwTwo.setProgress(d);
                                   wheelProgress=d;
			                       
			                       
			                       int a = c.getCount();
			                       b++;
			                       int d=Math.round(((float) b / a ) * 360);
                                   pwTwo.setProgress(d);
                                
			                        pwTwo.incrementProgress();
			                        wheelProgress++;
			            		    
			                                
			                        }
			                     else{
			                        q = " ";
			                        k = electricity + q + voltage + q + sensor_Num + q 
				                         + welder_id + q +machine_id + q +status;
			                        l = l + q + k;
			                                
			                        b++;
				                       int d=Math.round(((float) b / c.getCount() ) * 360);
	                                   pwTwo.setProgress(d);
	                                   wheelProgress=d;   
			                        
			                        
			                        pwTwo.incrementProgress();
			            			wheelProgress++;
					        		u++;
			            					
			                             }
			                            System.out.println(l);   
			                        } while (c.moveToNext());
			                    }
			                    c.close();
			                    
			                    try {
			                    	//发送消息
			                        // 步骤1：从Socket 获得输出流对象OutputStream
			                        // 该对象作用：发送数据
			                        outputStream = socket.getOutputStream();
			
			                        // 步骤2：写入需要发送的数据到输出流对象中
			                        outputStream.write((l+"\n").getBytes("utf-8"));
			                        // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞
			
			                        // 步骤3：发送数据到服务端
			                        outputStream.flush();
			                        
			                        response = l;
			
			                        // 步骤4:通知主线程,将接收的消息显示到界面
			                        Message msg = Message.obtain();
			                        msg.what = 0;
			     		           
			                    } catch (IOException e) {
			                        e.printStackTrace();
			                    }
			                	
			                }
			                else{
			                	Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_LONG).show();
			                }*/
			             }
	                }
		        catch(Exception e){
                	if(u==0){
	                	Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_LONG).show();
	                	Intent i2 = new Intent(Trans2.this ,Buff.class);
	                	startActivity(i2); 
                	}
		        }
	             
	                
				wheelRunning = false;
				
				

			}
		};      	
              
    };

