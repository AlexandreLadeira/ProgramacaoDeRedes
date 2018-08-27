/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercicioudp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author u16159
 */
public class Cliente {
    
    public static void main(String args[]) throws Exception{
        try{
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            
            BufferedReader leitor = new BufferedReader( 
                                    new InputStreamReader(System.in));        
           
            while(true){
                System.out.print("Digite sua mensagem:");
                String sentence = leitor.readLine();
                
                if(sentence.equals("S"))
                    break;
                
                byte[ ] sendData = new byte[1024];
                sendData = sentence.getBytes();
                
                DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                clientSocket.send(sendPacket);
                
            }
            
            clientSocket.close();
        }catch(Exception e ){
            System.out.println(e.getMessage());
        }
    }
}
