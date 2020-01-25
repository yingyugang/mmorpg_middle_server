package com.test;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;

public class Test 
{
	public ConnectToClient server;
	public ArrayList<ConnectToClient> clients = new ArrayList<ConnectToClient>();
	static Test manager;
	
	public static void main(String[] args)
	{
	    try{
	        ServerSocket ss = new ServerSocket(10000);
	        manager = new Test();
	        System.out.println("Waiting now ...");
	        while(true){
	            try{
	                //サーバー側ソケット作成
	                Socket sc = ss.accept();
	                System.out.println("Welcom!");
	                ConnectToClient cc= new ConnectToClient (sc,manager);
	                CheckConnect ccc = new CheckConnect (sc,manager,cc);
	                ccc.start();
	                cc.start();
	            }
	            catch(Exception ex)
	            {
	                ex.printStackTrace();
	                break;
	            }
	        }
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	}
}
/*
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;


public class Test extends JFrame implements Runnable
{
    //接続先ホスト名(今回はローカルホスト)
    //ここにサーバのIPアドレスを指定します．普通はハードコーディングせずに外部入力から設定します．
    //例)　192.xxx.yyy.z --> "192.xxx.yyy.z"
    public static final String HOST = "34.85.109.164";
    //接続先ポート番号(サーバー側で設定したものと同じもの)
    //ポート番号をクライアントとサーバ間で一致させてないと通信できませんよ！
    public static final int PORT    = 10000;

    //今回はGUIアプリを作ったので，ソケット通信に関係ないメンバ変数も含んでます．
    private JTextField tf;
    private JTextArea  ta;
    private JScrollPane sp;
    private JPanel pn;
    private JButton bt;

    //ソケット通信用の変数です．サーバ側と同じくソケットクラス，バッファへの読み書きクラスです．
    private Socket sc;
    private BufferedReader br;
    private PrintWriter pw;

    public static void main(String[] args)
    {
    	Test cl = new Test();
    }

    public Test()
    {
        //親クラスのコンストラクタを呼び出し
        super("Client Field");

        tf = new JTextField();
        ta = new JTextArea();
        sp = new JScrollPane(ta);
        pn = new JPanel();
        bt = new JButton("Send");

        //以下、GUIレイアウトとコンポーネントのイベント設定
        pn.add(bt);
        add(tf, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(pn, BorderLayout.SOUTH);

        bt.addActionListener(new SampleActionListener());
        addWindowListener(new SampleWindowListener());

        setSize(400,300);
        setVisible(true);

        //Threadクラスのインスタンスを作成・実行
        //ここからソケット通信用のスレッドが作成され，通信が開始します．
        Thread th = new Thread(this);
        th.start();
    }

    //Runメソッドの実装
    public void run()
    {
        try{
            //ここでサーバへ接続されます
            sc = new Socket(HOST,PORT);
            br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())));

            //サーバから受け取ったデータをGUI表示させているのですが，スレッドが終了しないように無限ループさせてます
            while(true){
                try{
                    String str = br.readLine();
                    ta.append(str + "\n");
                }
                catch(Exception e){
                    br.close();
                    pw.close();
                    sc.close();
                    break;
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public class SampleActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try{
                //今回のGUIアプリはテキストをボックスに入力　→　送信ボタンで送信というシンプルな構造になってます．
                //ボタンにリスナーを登録し，押下時に送信バッファからデータを送信するようにしてます．
                String str = tf.getText();
                pw.println(str);
                ta.append(str + "\n");
                pw.flush();
                tf.setText("");
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    class SampleWindowListener extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            System.exit(0);
        }
    }
}*/


