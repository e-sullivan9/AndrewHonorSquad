/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tests;

/**
 *
 * @author Eric Sullivan
 */

import java.net.*;
import java.io.*;
public class SimpleServer implements Runnable {

    private ServerSocket so;
    private Thread thread;
    private ServerThread clients[] = new ServerThread[2];
    private int threadCount;
    
    
    
    public SimpleServer(int port){
        try{
            so = new ServerSocket(port);
            start();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void start(){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }
    @Override
    public void run() {
        while(thread != null){
            try{
                while(true){
                    addClient(so.accept());
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        
    }

    private void addClient(Socket socket) {
        if(threadCount<clients.length){
            clients[threadCount] = new ServerThread(this,socket);
            try{
                clients[threadCount].open();
                clients[threadCount].start();
                threadCount++;
            }
            catch(Exception e){
                 System.out.println(e.getMessage());
                    }
        }
    }
    public void write(String line){
        for(int i = 0; i<threadCount;i++){
            System.out.println(line);
            clients[i].send(line);
        }
    }

    private static class ServerThread extends Thread {
         private SimpleServer ss;
         private Socket socket;
         private int ID;
         private BufferedReader reader;
         private BufferedWriter writer;

        public ServerThread(SimpleServer so, Socket socket) {
            super();
            ss = so;
            this.socket = socket;
            ID = socket.getPort();
        }
        public void run(){
            while(true){
               try{
                   String line = reader.readLine();
                    System.out.println(line);
                    ss.write(line);
               }
               catch(Exception e){
                  System.out.println(e.getMessage());
               }
            }
        }
        public void send(String line){
            try{
                System.out.println(line);
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
            catch(Exception e){
               System.out.println(e.getMessage());
            }
        }
        public void open() throws IOException { 
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

}
    public static void main(String[] args){
        new SimpleServer(9999);
    }
}
