package com.example.wifilisttest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.example.wifilisttest.MySQLiteOpenHelper;

public class MainActivity extends FragmentActivity {
    List<ScanResult> list;
    String[] Flist=null;
    Button but;
    
    private MySQLiteOpenHelper sqlHelper;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Fragment1 f1 = new Fragment1();  
        FragmentManager manager  = getSupportFragmentManager();  
        manager.beginTransaction().add(R.id.fragments, f1).commit(); 
        
        sqlHelper = new MySQLiteOpenHelper(this, "StuDatabase.db", null, 2);
        sqlHelper.getWritableDatabase();
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	
}
