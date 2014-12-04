/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

/**
 *
 * @author Justin Jiang
 */

import java.io.*;
import java.net.*;

public class Client
{
    protected String hostname;
    protected int port;
    protected Socket s;
    protected BufferedReader reader;
    protected BufferedWriter writer;
    
    public Client() throws IOException
    {
        hostname = "localhost";
        port = 10000;
        s = new Socket(hostname, port);
        reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }
    
    public Client(String string) throws IOException
    {
        hostname = string;
        port = 10000;
        s = new Socket(hostname, port);
        reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }
    
    public String receive() throws IOException
    {
        String result = reader.readLine();
        return result;
    }
    
    public void send(String s) throws IOException
    {
        writer.write(s);
        writer.newLine();
        writer.flush();
    }
    
}
