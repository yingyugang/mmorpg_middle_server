package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.entity.MessageRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CheckConnect extends Thread
{
    private Socket sc;
    private BufferedReader br;
    private PrintWriter pw;
    
    ConnectType connectType = ConnectType.None;
    GsonBuilder builder ;
	Gson gson;
	Test server;
	ConnectToClient connectToClient;
    //コンストラクタ
    public CheckConnect(Socket s,Test server,ConnectToClient connectToClient)
    {
        sc = s;
        builder = new GsonBuilder();
        this.server = server;
        this.connectToClient = connectToClient;
    	gson = builder.create();
    }

    //スレッド実行
    public void run()
    {
    	try {
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())));
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
        while(true){
            try{
            	//sc.sendUrgentData(0xFF);
            	pw.println("/r/n");
            	pw.flush();
            	sleep(100);
            }
            catch(Exception e){
                try{
                    sc.close();
                    System.out.println("Good Bye !!");
                    if(server.server == connectToClient) {
                    	server.server = null;
                    }
                    else {
                    	if(server.clients!=null && server.clients.contains(connectToClient))
                    		server.clients.remove(connectToClient);
                    }
                    connectToClient.stop();
                    this.stop();
                }
                catch(Exception ex){
                	this.stop();
                    ex.printStackTrace();
                }
            }
        }
    }
}
