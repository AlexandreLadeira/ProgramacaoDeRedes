
package revisaotcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Alexandre Ladeira, 16159 PD-16
 */
public class Cliente {
    
    public final static String HTTP_VERSION = "HTTP/1.1";
    private String host;
    private int porta;
            
    public Cliente(String host, int porta){
        super();
        this.host   = host;
        this.porta  = porta;
    }
    
    public String getContent(String caminho) throws UnknownHostException,
           IOException {
        Socket socket = null;
        try {            
            socket                  = new Socket(this.host, this.porta); 
            PrintWriter saida       = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada  = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));            
            
            saida.println("GET " + caminho + " " + HTTP_VERSION);
            saida.println("Host: " + this.host);
            saida.println("Connection: Close");
            saida.println();
            
            boolean loop = true;
            
            StringBuffer sb = new StringBuffer();
           
            while (loop) {
                if (entrada.ready()) {
                    int i = 0;
                    while ((i = entrada.read()) != -1) {
                        sb.append((char) i);
                    }
                    loop = false;
                }
            }
            return sb.toString();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    
}
