package com.example.wifilisttest;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Buff extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		boolean flag = false;
		int i=0;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buff);
		
		
		Timer timer=new Timer();
	    TimerTask timerTask=new TimerTask() {
	        @Override
	        public void run() {
	            Intent intent1=new Intent(Buff.this,Loading.class);
	            startActivity(intent1);
	            Buff.this.finish();
	        }
	    };
	    timer.schedule(timerTask,1000*3);
		
	}
	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //����˫���˳�����  
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
	        isExit = true; // ׼���˳�    
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false; // ȡ���˳�  
	            }  
	        }, 20); // ���2������û�а��·��ؼ�����������ʱ��ȡ�����ղ�ִ�е�����  
	  
	    } else {  
	        finish();  
	        System.exit(0);  
	    }  
	}  
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buff, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.error) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
