package com.example.wifilisttest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Loading extends Activity {

	//public Client client = new Client(this);
	
	public Trans2 trans2 = new Trans2();
	public String ipconfig = null;
	public Dialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		
		Button btnstudy_borrow=(Button)findViewById(R.id.button2);
        btnstudy_borrow.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent i2 = new Intent(Loading.this,MainActivity.class);
            	startActivity(i2); 
            }
        });
        
        Button btnstudy_find =(Button)findViewById(R.id.btn_return);
        btnstudy_find.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
            	
            	InputStream is = null;   
                try {  
                	String releasepath = getApplicationContext().getFilesDir().getAbsolutePath()+"/IPconfig.txt";
                	FileInputStream inputStream = new FileInputStream(releasepath);
                	byte[] bytes = new byte[100];  
                    ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();  
                    while (inputStream.read(bytes) != -1) {  
                        arrayOutputStream.write(bytes, 0, bytes.length);  
                    }  
                    inputStream.close();  
                    arrayOutputStream.close();
                    int count = 0;
                    for(int i = 0;i<bytes.length;i++){
                    	if(bytes[i] == 0){
                    		count++;
                    	}else{
                    		count=0;
                    	}
                    	
                    	if(count == 10){
                    		bytes = Arrays.copyOfRange(bytes, 0, i-9);
                    		break;
                    	}
                    }
                    ipconfig = new String(bytes,"UTF-8");
                    
                    
                    /*is = getResources().getAssets().open("IPconfig.txt");  
                    byte[] bytes = new byte[is.available()];  
                    is.read(bytes);  
                    String ipconfig1 = new String(bytes);  
                    String[] ipconfig2 = ipconfig1.split(",");
                    ipconfig = ipconfig2[ipconfig2.length-1];*/
                } catch (IOException e) {  
                    e.printStackTrace();  
                }
            	
            	if(ipconfig != null && ipconfig != ""){
            		Intent i2 = new Intent(Loading.this ,Trans2.class);
                	i2.putExtra("ip", ipconfig);
                	startActivity(i2); 
            	}else{
            		progressDialog = new Dialog(Loading.this,R.style.progress_dialog);  
	            	progressDialog.setContentView(R.layout.dialog2);  
	            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
	            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
	            	msg.setText("请配置IP地址");  
	            	progressDialog.show(); 
            	}
            }
        });
        
        
	}  
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.firstpage, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			final EditText inputServer = new EditText(Loading.this);  
			
			try {  
            	String releasepath = getApplicationContext().getFilesDir().getAbsolutePath()+"/IPconfig.txt";
            	FileInputStream inputStream = new FileInputStream(releasepath);
            	byte[] bytes = new byte[100];  
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();  
                while (inputStream.read(bytes) != -1) {  
                    arrayOutputStream.write(bytes, 0, bytes.length);  
                }  
                inputStream.close();  
                arrayOutputStream.close(); 
                int count = 0;
                for(int i = 0;i<bytes.length;i++){
                	if(bytes[i] == 0){
                		count++;
                	}else{
                		count=0;
                	}
                	
                	if(count == 10){
                		bytes = Arrays.copyOfRange(bytes, 0, i-9);
                		break;
                	}
                }
                ipconfig = new String(bytes,"UTF-8");
                
                
                /*is = getResources().getAssets().open("IPconfig.txt");  
                byte[] bytes = new byte[is.available()];  
                is.read(bytes);  
                String ipconfig1 = new String(bytes);  
                String[] ipconfig2 = ipconfig1.split(",");
                ipconfig = ipconfig2[ipconfig2.length-1];*/
            } catch (IOException e) {  
                e.printStackTrace();  
            }
			
			inputServer.setText(ipconfig);
			
            AlertDialog.Builder builder = new AlertDialog.Builder(Loading.this);  
            builder.setTitle("IP地址配置").setView(inputServer)  
                    .setNegativeButton("取消", null);  
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
  
				public void onClick(DialogInterface dialog, int which) {  
					ipconfig = inputServer.getText().toString(); 
                	try {
                		String releasepath = getApplicationContext().getFilesDir().getAbsolutePath()+"/IPconfig.txt";
                		FileOutputStream outputStream = new FileOutputStream(releasepath);
                		outputStream.write(ipconfig.getBytes());  
                        outputStream.flush();  
                        outputStream.close();  
                		
						/*FileOutputStream outStream = new FileOutputStream(file,true);
						FileOutputStream outStream = getResources().getAssets().openFd("IPconfig.txt").createOutputStream();
						outStream.write(ipconfig.getBytes());
						outStream.flush();
			            outStream.close();*/
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                 }  
            });  
            builder.show();  
		}
		return super.onOptionsItemSelected(item);
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
	 * ˫���˳����� 
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
	        }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
	  
	    } else {  
	        finish();  
	        System.exit(0);  
	    }  
	}  
	
}
