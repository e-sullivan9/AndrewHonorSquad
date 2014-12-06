/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Eric Sullivan
 */
public class GameLogic {
 private int x, y;
 private int[][] board;
 protected Socket socket;
 protected Socket socket2;
 
    public GameLogic(){
        x=0;
        y=0;
        board = new int[15][15];
    }
     public void setSockets(Socket socket, Socket socket2){
        this.socket=socket;
        this.socket=socket2;
    }
     public void execute(){
         try{
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()));
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            
            writer.write("1");
            writer2.write("2");
            while(true){
                String line  = reader.readLine();
                String line3 = "1,"+processInput("1,"+line);
                writer.write(line3);
                writer.newLine();
                writer.flush();
                writer2.write(line3);
                writer2.newLine();
                writer2.flush();
                if(line3.split(",").length==4)
                    break;
                 String line2 = reader2.readLine();
                String line4 ="2,"+processInput("2,"+line2);
                writer.write(line4);
                writer.newLine();
                writer.flush();
                writer2.write(line4);
                writer2.newLine();
                writer2.flush();
                if(line3.split(",").length==4)
                    break;
            }
            writer.close();
            reader.close();
            writer2.close();
            reader2.close();
         }
         catch(Exception e){
                    e.printStackTrace();
                    
        }
     
 }

    private String processInput(String line) {
        String[] split = line.split(",");
        int num = Integer.parseInt(split[0]);
        x = Integer.parseInt(split[1]);
        y = Integer.parseInt(split[2]);
        board[x][y]=num;
        if(1+checkLeft(num,x,y)+checkRight(num,x,y)==5){
            return(""+num+","+x+","+y+","+"win");
        }
        else
        if(1+checkUp(num,x,y)+checkDown(num,x,y)==5){
            return(""+num+","+x+","+y+","+"win");
        }
        else
        if(1+checkUpLeft(num,x,y)+checkDownRight(num,x,y)==5){
            return(""+num+","+x+","+y+","+"win");
        }
        else
        if(1+checkUpRight(num,x,y)+checkDownLeft(num,x,y)==5){
            return(""+num+","+x+","+y+","+"win");
        }
        else
            return(""+num+","+x+","+y);
    }

    private int checkLeft(int num, int i, int y) {
        if(i-1<=0&&board[i-1][y]==num){
            return checkLeft(num,i-1,y)+1;
        }
        else
            return 0;
    }

    private int checkRight(int num, int i, int y) {
        if(i+1>=14&&board[i++][y]==num){
            return checkLeft(num,i++,y)+1;
        }
        else
            return 0;
    }
    private int checkUp(int num, int i, int y) {
        if(y+1>=14&&board[i][y++]==num){
            return checkLeft(num,i,y++)+1;
        }
        else
            return 0;
    }

    private int checkDown(int num, int i, int y) {
        if(y-1<=0&&board[i][y--]==num){
            return checkLeft(num,i,y--)+1;
        }
        else
            return 0;
    }
    private int checkUpLeft(int num, int i, int y) {
        if(i+1>=14&&y-1<=0&&board[i--][y++]==num){
            return checkLeft(num,i--,y++)+1;
        }
        else
            return 0;
    }

    private int checkDownLeft(int num, int i, int y) {
        if(i-1<=0&&y-1<=0&&board[i--][y--]==num){
            return checkLeft(num,i--,y--)+1;
        }
        else
            return 0;
    }
    private int checkUpRight(int num, int i, int y) {
        if(i+1>=14&&y+1>=14&&board[i++][y++]==num){
            return checkLeft(num,i++,y++)+1;
        }
        else
            return 0;
    }

    private int checkDownRight(int num, int i, int y) {
        if(i+1>=14&&y-1<=0&&board[i++][y--]==num){
            return checkLeft(num,i++,y--)+1;
        }
        else
            return 0;
    }
}
