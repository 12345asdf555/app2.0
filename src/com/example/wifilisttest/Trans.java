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
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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
        
        try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
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
	           exitBy2Click();    
	       }  
	    return false;  
	}  private static Boolean isExit = false;  
	  
	private void exitBy2Click() {  
	    Timer tExit = null;  
	    if (isExit == false) {  
	        isExit = true;    
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false; 
	            }  
	        }, 20); 
	  
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
                    String str1 = str.substring(4, 12);
                    //String str1 = data.substring(4, 12);
                    
                    Integer k=Integer.valueOf(str1, 16) * 24;
                    str1=Integer.toString(k);
                    
                    pwTwo.setText("待采集数据"+ str1 +"条");
                    pwTwo.postInvalidate();
                    
                    
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        	Button startBtn = (Button) findViewById(R.id.btn_start);
                            startBtn.setEnabled(true);
                        }
                    });
                    
                    
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Intent i2 = new Intent(Trans.this ,Buff.class);
                	startActivity(i2); 
                }
		}
	};      	
    

		public Runnable a = new Runnable() {
			private String l = "";

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
                    	
                        socket = new Socket("192.168.1.8", 1001);
                        
                        
                        boolean j=socket.isConnected();
                        if(j){
                       
                        }
                        else{
                        	
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
                        /*is = socket.getInputStream();
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
                        outputStream.flush();*/
                           
                        //接收消息
                        // 步骤1：创建输入流对象InputStream
                        is = socket.getInputStream();
                        // 步骤2：创建输入流读取器对象 并传入输入流对象
                        // 该对象作用：获取服务器返回的数据
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                        
                        
                        byte[] datas = new byte[13]; 
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
                        
                        String strc = str.substring(4, 12);
                        Integer num=Integer.valueOf(strc, 16) * 24 * 28 ;
                        
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
							
                            socket = new Socket("192.168.1.8", 1001);
                            
                            
                            boolean j1=socket.isConnected();
                            
                            System.out.println(socket.isConnected());
                        	
                            outputStream = socket.getOutputStream();

                            outputStream.write(bb1);
                            
                            outputStream.flush();
                               
                            is = socket.getInputStream();
                        	DataInputStream input = new DataInputStream(is);   
                        	
                        	//br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            
                            String strdata = "";
                            byte[] datas1 = new byte[num]; 
                            
                            int rolldata;
                            int readBytes = 0;
                            while (readBytes < num) {  
                                int read = is.read(datas1, readBytes, num - readBytes);  
                                System.out.println(read);  
                                if (read == -1) {  
                                    break;  
                                }  
                                readBytes += read;  
                                
                                rolldata=Math.round(((float) readBytes / num ) * 360);
                                pwTwo.setProgress1(rolldata);
                                wheelProgress+=rolldata;
                                
                                /*try {
									Thread.sleep(1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}*/
                                
                            } 
                         
                            //dyte数组转换成string
                            String strdate = new String(datas1, "ISO-8859-1");
                            
                            //读取txt数据
                            String releasepath = getApplicationContext().getFilesDir().getAbsolutePath()+"/date.txt";
                            File file = new File(releasepath);
                            if(file.exists()){
                            	FileInputStream inputStream = new FileInputStream(releasepath);
        	                	int length = inputStream.available();
        	                	byte[] bytes = new byte[length];  
        	                    ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();  
        	                    while (inputStream.read(bytes) != -1 && length!=0) {  
        	                        arrayOutputStream.write(bytes, 0, bytes.length);
        	                    }  
        	                    inputStream.close();  
        	                    arrayOutputStream.close();
        	                    
        	                    l  = new String(bytes,"ISO-8859-1");
        	                    
                            }
    	                	
                            
                            //数据写进txt中
                            try {
                        		//String releasepath = getApplicationContext().getFilesDir().getAbsolutePath()+"/date.txt";
                        		FileOutputStream outputStream = new FileOutputStream(releasepath);
                        		
                        		/*Date date = new Date();
                            	String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                        		String count = Integer.toString(num/28);
                        		strdate = strdate + "&&" + nowTime + "&&" + count + "&&";
                        		l = l + "&&" + strdate;
                        		datas1 = l.getBytes("ISO-8859-1");*/
                        		
                        		strdate = l + strdate;
                        		datas1 = strdate.getBytes("ISO-8859-1");
                        		
                        		
                        		//写入txt
                        		outputStream.write(datas1);
                                outputStream.flush();  
                                outputStream.close();
                        		
        					} catch (FileNotFoundException e) {
        						// TODO Auto-generated catch block
        						e.printStackTrace();
        					} catch (IOException e) {
        						// TODO Auto-generated catch block
        						e.printStackTrace();
        					}
                            
                            //is.read(datas1);
                            
                            /*for(int i=0;i<datas1.length;i++){
                            	
                            	//�ж�Ϊ���ֻ�����ĸ����Ϊ��ĸ+256ȡ����
                            	if(datas1[i]<0){
                            		String r = Integer.toHexString(datas1[i]+256);
                            		String rr=r.toUpperCase();
                                	System.out.print(rr);
                                	//���ֲ�Ϊ��λ��
                                	if(rr.length()==1){
                            			rr='0'+rr;
                                	}
                                	//strdataΪ�ܽ�������
                            		strdata += rr;
                            		
                            	}
                            	else{
                            		String r = Integer.toHexString(datas1[i]);
                            		String rr=r.toUpperCase();
                                	System.out.print(rr);
                                	if(rr.length()==1)
                            			rr='0'+rr;
                            		strdata+=rr;	
                            		
                            	}
                            }
                            response=strdata;*/

                            //处理数据
                            /*SQLiteDatabase db = sqlHelper.getWritableDatabase();
                            String inSql;
                            int mid=1;
                            wheelProgress=0;
                            for(int i=0;i<datas1.length;i+=28){
                            	
                            	try{
                            		
                            		byte[] eleby = new byte[2];
                                	System.arraycopy(datas1, i+2, eleby, 0, 2);
                                	int eleint = (int)eleby[0]*256 + (int)eleby[1];
                                	String electricity = Integer.valueOf(eleint).toString();
                                	String electricity = Integer.valueOf((int)datas1[i+2]*256 + (int)datas1[i+3]).toString();
                                	
                                	byte[] volby = new byte[2];
                                	System.arraycopy(datas1, i+4, volby, 0, 2); 
                                	int volint = (int)volby[0]*256 + (int)volby[1];
                                	String voltage = Integer.valueOf(volint).toString();
                                	String voltage = Integer.valueOf((int)datas1[i+4]*256 + (int)datas1[i+5]).toString();
                                	
                                	byte[] senby = new byte[2];
                                	System.arraycopy(datas1, i+6, senby, 0, 2); 
                                	int senint = (int)senby[0]*256 + (int)senby[1];
                                	String sensor_Num = Integer.valueOf(senint).toString();
                                	String sensor_Num = Integer.valueOf((int)datas1[i+6]*256 + (int)datas1[i+7]).toString();
                                	
                                	byte[] macby = new byte[2];
                                	System.arraycopy(datas1, i+8, macby, 0, 2); 
                                	int macint = (int)macby[0]*256 + (int)macby[1];
                                	String machine_id = Integer.valueOf(macint).toString();
                                	String machine_id = Integer.valueOf((int)datas1[i+8]*256 + (int)datas1[i+9]).toString();
                                
                                	byte[] welby = new byte[2];
                                	System.arraycopy(datas1, i+10, welby, 0, 2); 
                                	int welint = (int)welby[0]*256 + (int)welby[1];
                                	String welder_id = Integer.valueOf(welint).toString();
                                	String welder_id = Integer.valueOf((int)datas1[i+10]*256 + (int)datas1[i+11]).toString();
                                	
                                	byte[] codby = new byte[4];
                                	System.arraycopy(datas1, i+12, codby, 0, 4); 
                                	int codint = (int)codby[0]*65536 + (int)codby[1]*4096 + (int)codby[2]*256 + (int)codby[3];
                                	String code = Integer.valueOf(codint).toString();
                                	String code = Integer.valueOf((int)datas1[i+12]*65536 + (int)datas1[i+13]*4096 + (int)datas1[i+14]*256 + (int)datas1[i+15]).toString();
                                	
                                	byte[] yeaby = new byte[1];
                                	System.arraycopy(datas1, i+16, yeaby, 0, 1); 
                                	int yeaint = (int)yeaby[0];
                                	String year = Integer.valueOf(yeaint).toString();
                                	String year = Integer.valueOf((int)datas1[i+16]).toString();
                                	
                                	byte[] monby = new byte[1];
                                	System.arraycopy(datas1, i+17, monby, 0, 1); 
                                	int monint = (int)monby[0];
                                	String month = Integer.valueOf(monint).toString();
                                	String month = Integer.valueOf((int)datas1[i+17]).toString();
                                	
                                	byte[] dayby = new byte[1];
                                	System.arraycopy(datas1, i+18, dayby, 0, 1); 
                                	int dayint = (int)dayby[0];
                                	String day = Integer.valueOf(dayint).toString();
                                	String day = Integer.valueOf((int)datas1[i+18]).toString();
                                	
                                	byte[] houby = new byte[1];
                                	System.arraycopy(datas1, i+19, houby, 0, 1); 
                                	int houint = (int)houby[0];
                                	String hour = Integer.valueOf(houint).toString();
                                	String hour = Integer.valueOf((int)datas1[i+19]).toString();
                                	
                                	byte[] minby = new byte[1];
                                	System.arraycopy(datas1, i+20, minby, 0, 1); 
                                	int minint = (int)minby[0];
                                	String minute = Integer.valueOf(minint).toString();
                                	String minute = Integer.valueOf((int)datas1[i+20]).toString();
                                
                                	byte[] secby = new byte[1];
                                	System.arraycopy(datas1, i+21, secby, 0, 1); 
                                	int secint = (int)secby[0];
                                	String second = Integer.valueOf(secint).toString();
                                	String second = Integer.valueOf((int)datas1[i+21]).toString();
                                	
                                	byte[] staby = new byte[1];
                                	System.arraycopy(datas1, i+22, staby, 0, 1); 
                                	int staint = (int)staby[0];
                                	String status = Integer.valueOf(staint).toString();
                                	String status = Integer.valueOf((int)datas1[i+22]).toString();
                                	
                                	inSql = "insert into Tenghan(electricity,voltage,sensor_Num,machine_id,welder_id,code,year,month,day,hour,minute,second,status) "
                                    		+ "values('"+ electricity +"','" + voltage + "','" + sensor_Num + "'"
                                    				+ ","+ "'" + machine_id + "','" + welder_id + "','" + code + "'"
                                    						+ ",'" + year + "','" + month + "','" + day + "','" + hour + "'"
                                    								+ ",'" + minute + "','" + second + "','" + status + "')";
                                    db.execSQL(inSql);
                                    int mid1 = datas1.length/28;
                                    rolldata=Math.round(((float) mid / mid1 ) * 360);
                                    pwTwo.setProgress2(rolldata);
                                    wheelProgress+=rolldata;
                                    mid++;
                            		
                            	}catch(Exception e){
                            		e.printStackTrace();
                            	}
                            	
                            }*/
                            
                            
                            
                            //SQLiteDatabase db = sqlHelper.getWritableDatabase();
                            //String inSql;
                            
                            /*for(int i =0;i < stringArr.length;i++)
            		        {
                        	if(stringArr[i].length()>50){
                        		String electricity = Integer.valueOf(stringArr[i].subSequence(4, 8).toString(),16).toString();
                        		if(electricity.length()!=4){
                        			int count=4-electricity.length();
                        			for(int i1=0;i1<count;i1++){
                        				electricity="0"+electricity;
                        			}
                        		}
                                String voltage = Integer.valueOf(stringArr[i].subSequence(8, 12).toString(),16).toString();
                                if(voltage.length()!=4){
                        			int count=4-voltage.length();
                        			for(int i1=0;i1<count;i1++){
                        				voltage="0"+voltage;
                        			}
                        		}
                                String sensor_Num = Integer.valueOf(stringArr[i].subSequence(12, 16).toString(),16).toString();
                                if(sensor_Num.length()!=4){
                        			int count=4-sensor_Num.length();
                        			for(int i1=0;i1<count;i1++){
                        				sensor_Num="0"+sensor_Num;
                        			}
                        		}
                                String machine_id = Integer.valueOf(stringArr[i].subSequence(16, 20).toString(),16).toString();
                                if(machine_id.length()!=4){
                        			int count=4-machine_id.length();
                        			for(int i1=0;i1<count;i1++){
                        				machine_id="0"+machine_id;
                        			}
                        		}
                                String welder_id = Integer.valueOf(stringArr[i].subSequence(20, 24).toString(),16).toString();
                                if(welder_id.length()!=4){
                        			int count=4-welder_id.length();
                        			for(int i1=0;i1<count;i1++){
                        				welder_id="0"+welder_id;
                        			}
                        		}
                                String code = stringArr[i].subSequence(24, 32).toString();
                                String year = Integer.valueOf(stringArr[i].subSequence(32, 34).toString(),16).toString();
                                if(year.length()!=2){
                        			int count=2-year.length();
                        			for(int i1=0;i1<count;i1++){
                        				year="0"+year;
                        			}
                        		}
                                String month = Integer.valueOf(stringArr[i].subSequence(34, 36).toString(),16).toString();
                                if(month.length()!=2){
                        			int count=2-month.length();
                        			for(int i1=0;i1<count;i1++){
                        				month="0"+month;
                        			}
                        		}
                                String day = Integer.valueOf(stringArr[i].subSequence(36, 38).toString(),16).toString();
                                if(day.length()!=2){
                        			int count=2-day.length();
                        			for(int i1=0;i1<count;i1++){
                        				day="0"+day;
                        			}
                        		}
                                String hour = Integer.valueOf(stringArr[i].subSequence(38, 40).toString(),16).toString();
                                if(hour.length()!=2){
                        			int count=2-hour.length();
                        			for(int i1=0;i1<count;i1++){
                        				hour="0"+hour;
                        			}
                        		}
                                String minute = Integer.valueOf(stringArr[i].subSequence(40, 42).toString(),16).toString();
                                if(minute.length()!=2){
                        			int count=2-minute.length();
                        			for(int i1=0;i1<count;i1++){
                        				minute="0"+minute;
                        			}
                        		}
                                String second = Integer.valueOf(stringArr[i].subSequence(42, 44).toString(),16).toString();
                                if(second.length()!=2){
                        			int count=2-second.length();
                        			for(int i1=0;i1<count;i1++){
                        				second="0"+second;
                        			}
                        		}
                                String status = stringArr[i].subSequence(44, 46).toString();
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
                                                
                                rolldata=Math.round(((float) mid / stringArr.length ) * 360);
                                pwTwo.setProgress(rolldata);
                                wheelProgress+=rolldata;
                                mid++;
                                
                        	
                        	}else{
                        		rolldata=Math.round(((float) mid / stringArr.length ) * 360);
                                pwTwo.setProgress(rolldata);
                                wheelProgress+=rolldata;
                                mid++;
                        	}
            		        }*/
                            	
                        }
                        
                        String b2="FE165555555555555555550EFD";
					    byte[] bb2=new byte[b2.length()/2];

						for (int i2 = 0; i2 < bb2.length; i2++)
						{
							String tstr1=b2.substring(i2*2, i2*2+2);
							Integer k=Integer.valueOf(tstr1, 16);
							bb2[i2]=(byte)k.byteValue();
						}
						
                        socket = new Socket("192.168.1.8", 1001);
                        
                        
                        boolean j2=socket.isConnected();
                        
                        System.out.println(socket.isConnected());
                    	
                        outputStream = socket.getOutputStream();

                        outputStream.write(bb2);
                        
                        outputStream.flush();
                           
                        is = socket.getInputStream();
                        
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                        
                        socket.close();
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                        
                        
                       /* }
                        else{
                        	
                        }*/
                        
                       /* if(str.length()!=36)
                        {
                            //����д�����ݿ�
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
                        
                      /*//д�����ݿ�
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
                        

					
					
					
					
					
					/*		
                     	String s="2";
                     	
                         // ����Socket���� & ָ������˵�IP �� �˿ں�
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
	                        	 
	                        	 Toast.makeText(getApplicationContext(), "���������ӳɹ�", Toast.LENGTH_LONG).show();
	                        	 
	                        	 // �жϿͻ��˺ͷ������Ƿ����ӳɹ�
	                             System.out.println(socket.isConnected());
	                             
	                           //������Ϣ
	                             // ����1����Socket ������������OutputStream
	                             // �ö������ã���������
	                             try {
									outputStream = socket.getOutputStream();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	
	                             // ����2��д����Ҫ���͵����ݵ������������
	                             try {
									outputStream.write((s+"\n").getBytes("utf-8"));
								} catch (UnsupportedEncodingException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	                             // �ر�ע�⣺���ݵĽ�β���ϻ��з��ſ��÷������˵�readline()ֹͣ����
	
	                             // ����3���������ݵ������
	                             try {
									outputStream.flush();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	                             
	                             
	                             //������Ϣ
	                             // ����1����������������InputStream
	                             try {
									is = socket.getInputStream();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	                             // ����2��������������ȡ������ ����������������
	                             // �ö������ã���ȡ���������ص�����
	                             isr = new InputStreamReader(is);
	                             br = new BufferedReader(isr);
	
	                             // ����3��ͨ����������ȡ������ ���շ��������͹���������
	                             // ���͹������ݴ���
	                             try {
									response = br.readLine();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	                             String str = response;
	
	
	                             // ����4:֪ͨ���߳�,�����յ���Ϣ��ʾ������
	                             Message msg = Message.obtain();
	                             msg.what = 0;
	                             
	                             if(str.length()!=36)
	                             {
	    	                            //����д�����ݿ�
	    	                            SQLiteDatabase db = sqlHelper.getWritableDatabase();
	    	                            String inSql;
	    	                            stringArr = str.split(" ");
	    	                            total=stringArr.length;
	    	                            pwTwo.setText("δ�ɼ�"+stringArr.length/6+"��");
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

