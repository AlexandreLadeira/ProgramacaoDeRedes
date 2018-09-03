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
public class ScannerDePortasRemoto {
     public static void main(String[] args) {
       try {
           byte[] dado = new byte[8192];
           System.out.print("Digite o IP:");
           
           BufferedReader leitor = new BufferedReader(
                                   new InputStreamReader(System.in)); 
           
           String ip = leitor.readLine();
           
           InetAddress endereco  = InetAddress.getByName(ip);
           DatagramPacket pacote = new DatagramPacket(dado,8192);
           
           //i == porta que será testada 
           for(int i = 1024; i <= 65535; i++) 
               try {
                 DatagramSocket servidor = new DatagramSocket(i);
                 pacote.setAddress(endereco);
                 servidor.send(pacote);
                 
                 System.out.println("A porta "+i+" está livre");
                 servidor.close();
               } catch(Exception e ) {
                   System.out.println("Porta-"+i+"-em uso");
               }
              
           
       } catch (Exception e ) {
           System.out.println("Oocorreu um erro:"+e.getMessage());
       }
    }
    
}
