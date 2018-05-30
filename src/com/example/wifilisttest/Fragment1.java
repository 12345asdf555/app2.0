package com.example.wifilisttest;

import java.util.ArrayList;  
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;  
import java.util.Map;
import android.app.Application;  
import android.R.string;
import android.app.ActionBar;
import android.app.Activity;  
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.Bundle;  
import android.support.v4.app.ListFragment;
import android.text.Spannable;
import android.util.Log;  
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;  
import android.widget.ListView;  
import android.widget.SimpleAdapter;  
import android.widget.TextView;  
import android.widget.Toast;  
  

public class Fragment1 extends ListFragment   { 
    private String TAG = Fragment1.class.getName();  
    private ListView list ;  
    private SimpleAdapter adapter;  
    String[] Flist;
	private TextView Tv;
	public WifiManager wifiManager;
	public WifiInfo wifiinfo;
    List<ScanResult> wifilist;
    List<WifiConfiguration> wifiConfigList;
	private Activity context;

	private List<WifiConfiguration> wificonfigList;
    
    
	public void onAttach(Activity activity) {  
        super.onAttach(activity);  
        Log.i(TAG, "----------onAttach");  
    } 
	
	public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        
	        Log.i(TAG, "--------onCreate");
    		int k=0;
    		int l=0;
	        wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE); 
	        wifilist = wifiManager.getScanResults();
	        wifiConfigList = wifiManager.getConfiguredNetworks();
	        wifiinfo = wifiManager.getConnectionInfo(); 
	        
	        //数组初始化要注意
	        String[] listk=new String[wifilist.size()];
	        
	        if(wifilist!=null){  
	            for( int i=0;i<wifilist.size();i++){  
	                ScanResult scanResult =wifilist.get(i);
	                
	                
	                for(int j=0;j<listk.length;j++){
	                	if(scanResult.SSID.equals(listk[j])){
	                		k=0;
	                		break;
	                	}
	                	else{
	                		k++;
	                	}
	                }
	                if(k==listk.length){
	                	k=0;
		                listk[l]="         "+scanResult.SSID; 
		                l++;
	                }
	                else{
	                	continue;
	                }

	            }  
	        } 
	             
	        String[] list0=new String[wifilist.size()];
	        if(wifilist==null){list0[0]="NoWiFi";}
	        else{
	        	list0=listk;
        }
    
	        
	      //设置Fragment1对应R.layout.listtest这个布局文件
       adapter = new SimpleAdapter(getActivity(),getData(list0), R.layout.listtest, new String[]{"title"}, new int[]{R.id.title});  
       setListAdapter(adapter); 
    }
	
	//清除同名WIFI
	 public  List<String> removeDup(String[] array) {  
	        List<String> list = new ArrayList<String>();  
	        list.add(array[0]);  
	        int notEqual = -1;  
	        for (int i = 0; i < array.length; i++) {// ѭ��ȡString����  
	            for (int j = 0; j < list.size(); j++) {// ѭ��ȡLIST  
	                if (array[i].equals("")||array[i].equals(null)) {  
	                    continue;  
	                } else if (array[i].equals(list.get(j))) {  
	                    notEqual = 0;  
	                    break;  
	                } else {  
	                    notEqual = 1;  
	                }  
	            }  
	            if (notEqual == 1) {  
	                list.add(array[i]);  
	            }  
	        }  
	        return list;  
	    }  
	
    
	private List<? extends Map<String, ?>> getData(String[] strs) {  
        List<Map<String ,Object>> list = new ArrayList<Map<String,Object>>();  
          
        for (int i = 0; i < strs.length; i++) {  
            Map<String, Object> map = new HashMap<String, Object>();  
              
            map.put("title", strs[i]);
            list.add(map);  
        }   
        
        return list;  
    }  
    
    
	/** 
     * @描述 在onCreateView中加载布局 
     * */    
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.fragment1, container,false);  
        list = (ListView) view.findViewById(android.R.id.list);

        
        Tv=(TextView)view.findViewById(R.id.tv);
        Tv.setText("焊机列表");
        Tv.setTextColor(android.graphics.Color.WHITE);
        
        Log.i(TAG, "--------onCreateView");  
        return view;  
    }  
  
    
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);
        
        ActionBar actionBar = getActivity().getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		setHasOptionsMenu(true);
		
        Log.i(TAG, "--------onActivityCreated");  
  
    } 
 
    
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);  
        ScanResult scanResult = wifilist.get(position);
        WifiConfiguration wifiCon = new WifiConfiguration(); 
        WifiManager wifimanager= (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE); 
        ConnectivityManager manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if(wifiManager.setWifiEnabled(true))
        {	
        	boolean flag = false;
        	int NetID=0;
        	for (int j = 0; j < wifiConfigList.size(); j++) {
            if (wifiConfigList.get(j).SSID.equals("\"" + scanResult.SSID + "\"")) {
            	//如果要连接的wifi在已经配置好的列表中，那就设置允许链接，并且得到id
            	NetID=wifiConfigList.get(j).networkId;
            	//wifiManager.removeNetwork(wifiConfigList.get(j).networkId);
            	wifiManager.saveConfiguration();
                break;
            	}          
        	}
        	wifiCon.allowedAuthAlgorithms.clear();  
        	wifiCon.allowedGroupCiphers.clear();  
        	wifiCon.allowedKeyManagement.clear();  
        	wifiCon.allowedPairwiseCiphers.clear();  
        	wifiCon.allowedProtocols.clear(); 
    		wifiCon.SSID = "\""+scanResult.SSID+"\"";//\"转义字符，代表"
    		/*wifiCon.wepKeys[0] = ""; 
    		wifiCon.hiddenSSID = true; 
    		wifiCon.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED); 
    		wifiCon.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP); 
    		wifiCon.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP); 
    		wifiCon.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40); 
    		wifiCon.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);*/ 
    		wifiCon.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
    		//wifiCon.wepTxKeyIndex = 0;
    	
    		/*wifiCon.SSID = "\""+scanResult.SSID+"\"";//\"转义字符，代表"
    		String jj="shgw201705";
    		wifiCon.preSharedKey = "\""+12345678+"\"";//WPA-PSK密码    
    		wifiCon.hiddenSSID = true;
    		
    		wifiCon.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
    		wifiCon.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
    		wifiCon.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
    		wifiCon.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP); 
    		wifiCon.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
    		wifiCon.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);          	
            wifiCon.status = WifiConfiguration.Status.ENABLED; */
            
            //NetID = wifiManager.addNetwork(wifiCon);
            /*for (WifiConfiguration c:wifiConfigList){
                wifiManager.disableNetwork(c.networkId);
            }*/
            wifiManager.enableNetwork(NetID, true);
            try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            WifiInfo wifiinfo = wifiManager.getConnectionInfo();
            wifimanager.getConnectionInfo();
            System.out.print(wifiinfo.getSSID());
            System.out.print(wifiinfo.getNetworkId()); //RT139（WPA加密） WIFI连接  
            
            /*Intent intent = new Intent(getActivity(),Trans.class); 
	           startActivity(intent);*/
            
            
			//String SSID=wifimanager.;
            //if(SSID.equals("\"" + scanResult.SSID + "\"")){
			   int i=1;
               do  {
      			 try {
      				 	i++;
      					Thread.sleep(500);
      				} catch (InterruptedException e) {
      					// TODO Auto-generated catch block
      					e.printStackTrace();
      				}
      			 if(i>=20){
	      				Intent intent = new Intent(getActivity(),Buff.class); 
 			            startActivity(intent);
      				break;
      			 }
      			 else if(manager.getActiveNetworkInfo() != null){
      			     flag = manager.getActiveNetworkInfo().isAvailable();
      			     if (flag) {
      					   Intent intent = new Intent(getActivity(),Trans.class); 
      			           startActivity(intent);
      			           i=0;
      			           break;
      				   } 
      			    }
               	} 
      		   while(i!=0); 	

    	/*WifiInfo wifiinfo = wifiManager.getConnectionInfo(); 
    	System.out.print(wifiinfo.getNetworkId());
    	System.out.print(wifiinfo.getLinkSpeed());
    	System.out.print(wifiinfo.getIpAddress()); 测试wifi连接和连接ID、speed */
        
        
  
        
        //wificonfigList = wifiManager.getConfiguredNetworks();
		//System.out.println(WifiConfiguration); 
		

    }
    }
    
	public boolean onOptionsItemSelected(MenuItem item){
    	if(item.getItemId() == android.R.id.home){
    	    getActivity().finish();
    	}
		return true;
    }

}  

 