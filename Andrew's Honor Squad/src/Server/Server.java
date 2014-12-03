/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

/**
 *
 * @author Justin Jiang
 */
import java.net.*;

public class Server extends GameLogic implements Runnable
{
    public void run()
    {
        execute();
    }

    public static void main(String[] args) throws Exception
    {
        ServerSocket se = new ServerSocket(10000);
        while(true)
        {
            Socket s1 = se.accept();
            Socket s2 = se.accept();
            Server serv = new Server();
            serv.setSockets(s1, s2);
            new Thread(serv).start();
        }
    }
}
