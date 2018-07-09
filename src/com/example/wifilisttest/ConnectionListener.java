package com.example.wifilisttest;


import java.util.concurrent.TimeUnit;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class ConnectionListener implements ChannelFutureListener {
	private Client client; 
	public LayoutInflater inflater;
	public SocketChannel socketChannel;
	public ConnectionListener(Client client) {  
	    this.client = client;  
	}  
	@Override
	public void operationComplete(ChannelFuture channelFuture) throws Exception {
		// TODO Auto-generated method stub
		if (!channelFuture.isSuccess()) {  
		      //System.out.println("Reconnect");  
		      final EventLoop loop = channelFuture.channel().eventLoop();  
		      loop.schedule(new Runnable() {  
		        @Override  
		        public void run() {  
		          client.createBootstrap(new Bootstrap(), loop);  
		        }  
		      }, 1L, TimeUnit.SECONDS);  
		    }else{
		    	/*View convertView = inflater.inflate(R.layout.activity_trans2, null);
		    	Button startBtn1 = (Button) convertView.findViewById(R.id.btn_start2);
		    	startBtn1.setEnabled(true);*/
		    	client.trans2.socketChannel = socketChannel;
		    	
		    	//测试用
		    	//client.trans2.startBtn1.setEnabled(true);
		    	
		    	//正式
		    	if(client.count != 0){
		    	   client.trans2.startBtn1.setEnabled(true);
		    	}
			}  
		}  
	}

