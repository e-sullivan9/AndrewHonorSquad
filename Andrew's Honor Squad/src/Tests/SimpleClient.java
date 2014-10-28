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
import java.awt.Color;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Random;
import javax.swing.*;


public class SimpleClient extends JFrame implements ActionListener,Runnable{
    private Random rng;
    private Socket socket;
   private Thread thread = null;
   private BufferedReader reader;
   private BufferedWriter writer;
   private JButton button;
   //private ClientThread client;
    
   public SimpleClient(String serverName,int port){
       super("Client");
       rng = new Random();
       this.setSize(500, 500);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setVisible(true);
       button = new JButton("Change");
       button.addActionListener(this);
       add(button);
       try{  
           System.out.println("working");
           socket = new Socket(serverName,port);
           System.out.println("working");
           writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
           reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           start();
       }
       catch(Exception e){
           e.printStackTrace();
       }
   }
 public void start() throws IOException{
       if(thread == null){
           //client = new ClientThread(this,socket);
           thread = new Thread(this);
           thread.start();
       }
   }
    public void run(){
        while(true){
            try{
                System.out.println("workng");
                String line = reader.readLine();
                System.out.println(line);
                String[] rgb = line.split(" ");
                button.setBackground(new Color(Integer.parseInt(rgb[0]),Integer.parseInt(rgb[1]),Integer.parseInt(rgb[2])));
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
    }
   
    }
 
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(button)){
            try{
            writer.write(""+rng.nextInt(254)+" "+rng.nextInt(254)+" "+rng.nextInt(254));
            writer.newLine();
            writer.flush();
          /*  String line = reader.readLine();
            System.out.println(line);
            String[] rgb = line.split(" ");
            button.setBackground(new Color(Integer.parseInt(rgb[0]),Integer.parseInt(rgb[1]),Integer.parseInt(rgb[2])));
            */
            
            }
            catch(IOException a){
                    System.out.println(a.getMessage());
                    }
        }

    }         public static void main(String[] args){
        new SimpleClient("localHost",9999);
    }

  /*  private static class ClientThread extends Thread {
            private Socket socket;
            private SimpleClient client;
            private BufferedReader reader;
        public ClientThread(SimpleClient aThis, Socket socket) {
          client = aThis;
          socket = socket;
          open();
          start();
        }
    }
    */

}
