/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercicioudp;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author u16159
 */
public class ScannerDePortasLocal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (int port = 1024; port <= 65535; port++) {
            try {
            // the next line will fail and drop into the catch block if
            // there is already a server running on port i
            DatagramSocket server = new DatagramSocket(port);
            System.out.println(port);
            server.close( );
            }
            catch (SocketException ex) {
            System.out.println("Existe um servidor na port:" + port + ".");
            } // end try
        } // end for
    }
    
    
    
}
