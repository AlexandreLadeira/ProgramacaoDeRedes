/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercicioudp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author u16159
 */
public class Servidor {    
    
   public static void main(String args[]) throws Exception{
        DatagramSocket serverSocket = new DatagramSocket(9876);       
        System.out.println("Servidor OK!");
        
        while(true){
            byte[ ] DadosRecebidos = new byte[1024];
             
            DatagramPacket PacoteRecebido =
            new DatagramPacket(DadosRecebidos, DadosRecebidos.length);
            
            serverSocket.receive(PacoteRecebido);
            
            String sentence = new String(PacoteRecebido.getData());
            
            InetAddress IPAddress = PacoteRecebido.getAddress();
            System.out.println
            ("DO CLIENTE: " + sentence + "IP ORIGEM: " + IPAddress);
        }
    }

    
}
