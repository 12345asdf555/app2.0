package com.example.wifilisttest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Firstpage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstpage);
		try {
			
			String releasepath = getApplicationContext().getFilesDir().getAbsolutePath()+"/IPconfig.txt";
			File file = new File(releasepath);
			if (!file.exists()) {  
			    file.mkdirs();
			    
			    InputStream is = getResources().getAssets().open("IPconfig.txt");
				FileOutputStream fos = new FileOutputStream(new File(releasepath)); 
				byte[] buffer = new byte[1024];  
	            int byteCount=0;                 
	            while((byteCount=is.read(buffer))!=-1) {//循环从输入流读取 buffer字节          
	                fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流  
	            }  
	            fos.flush();//刷新缓冲区  
	            is.close();  
	            fos.close();
			}  
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Timer timer=new Timer();
	    TimerTask timerTask=new TimerTask() {
	        @Override
	        public void run() {
	            Intent intent1=new Intent(Firstpage.this,Loading.class);
	            startActivity(intent1);
	            Firstpage.this.finish();
	        }
	    };
	    timer.schedule(timerTask,1000*3);
		
		
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.firstpage, menu);
		/*for(int i=0;i<=10000;i++){
			for(int j=0;j<=10000;j++){
				int k;
			    k=i+j;
			    k++;
			}
		}
		
		
		Intent k=new Intent(Firstpage.this,Loading.class);
		try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		startActivity(k);*/
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
