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
/* Client portion of Five in a Row game
*/
public class Client
{
    protected String hostname;
    protected int port;
    protected Socket s;
    protected BufferedReader reader;
    protected BufferedWriter writer;
    
    /** Default Constructor for local game
     * @throws IOException 
     */
    public Client() throws IOException
    {
        hostname = "localhost";
        port = 10000;
        s = new Socket(hostname, port);
        reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }
    
    /** Constructor for IP game
     * 
     * @param string IP address of server
     * @throws IOException 
     */
    public Client(String string) throws IOException
    {
        hostname = string;
        port = 10000;
        s = new Socket(hostname, port);
        reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }
    
    /** Reads buffered string from server
     * 
     * @return result The string read from the server
     * @throws IOException 
     */
    public String receive() throws IOException
    {
        String result = reader.readLine();
        return result;
    }
    
    /** Writes buffered string to the server
     * 
     * @param s String to be written
     * @throws IOException 
     */
    public void send(String s) throws IOException
    {
        writer.write(s);
        writer.newLine();
        writer.flush();
    }
    
}
