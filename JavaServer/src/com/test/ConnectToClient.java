package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import com.entity.Albums;
import com.entity.MessageRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
enum ConnectType{None,Server,Client};
//java.lang Package
public class ConnectToClient extends Thread
{
    private Socket sc;
    private BufferedReader br;
    private PrintWriter pw;
    
    ConnectType connectType = ConnectType.None;
    GsonBuilder builder ;
	Gson gson;
	Test server;
    //コンストラクタ
    public ConnectToClient(Socket s,Test server)
    {
        sc = s;
        builder = new GsonBuilder();
        this.server = server;
    	gson = builder.create();
    }

    //スレッド実行
    public void run()
    {
        try{
            //クライアントから送られてきたデータを一時保存するバッファ（受信バッファ）
            br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            //サーバがクライアントへ送るデータを一時保存するバッファ（送信バッファ）
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        while(true){
            try{
            	//受信バッファからデータを読み込む（今回は文字列）
                String str = br.readLine();
                if(connectType == ConnectType.None) {
                	MessageRequest request = gson.fromJson(str, MessageRequest.class);
                	//System.out.println(str);
                	if(request.requestId == 80000) {
                		connectType = ConnectType.Server;
                		server.server = this;
                		System.out.println("Server");
                	}else if(request.requestId == 70000){
                		connectType = ConnectType.Client;
                		server.clients.add(this);
                		System.out.println("Client");
                	}
                }else if(connectType == ConnectType.Server) {
                	for(int i = 0; i <server.clients.size();i++) {
                		server.clients.get(i).Send(str);
                	}
                }
        		/*Albums albums = new Albums();
        		albums.title = "Free Music Archive - Albums";
        		albums.message = "";
        		albums.total = "11259";
        		albums.total_pages = 2252;
        		albums.page = 1;
        		albums.limit = "5";
        		System.out.println(gson.toJson(albums));*/
        		
        		//MessageRequest request = gson.fromJson(str, MessageRequest.class);
        		//System.out.println(gson.toJson(request));
        		
            }
            catch(Exception e){
                try{
                    br.close();
                    pw.close();
                    sc.close();
                    System.out.println("Good Bye !!");
                    break;
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        }
    }
    
    public void Send(String str) {
    	 pw.println(str);
         //ここが重要！flushメソッドを呼ぶことでソケットを通じてデータを送信する
         pw.flush();
    }
}