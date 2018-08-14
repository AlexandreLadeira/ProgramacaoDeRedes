/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package revisaotcp;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Servidor simples que recebe o caminho de um arquivo, retornando 
 * para o cliente a pagina html correspondente e escrevendo na tela 
 * do servidor o cabecalho da mensagem vindo no navegador. 
 * 
 * UTILIZEI ESTE SITE PARA CONSULTA: 
 * https://tableless.com.br/criando-seu-proprio-servidor-http-do-zero-ou-quase-parte-iii/
 * 
 * @author Alexandre Ladeira, 16159 PD-16
 */
public class Servidor {    
    
   
    public static void main(String[] args) throws IOException {
       
        
        ServerSocket servidor = new ServerSocket(8000);
        //para usar localhost:8000
        
        while(true){//loop para não parar de aceitar conexoes
       
        Socket socket = servidor.accept();
        
        //verifica se esta conectado
        if (socket.isConnected()) {            
           
            //Leitor do input do cliente
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("\n");
            
            // a primeira linha contem todos os dados necesarios 
            String linha = buffer.readLine();
            
            //quebra a string pelo espaço em branco
            String[] dadosReq = linha.split(" ");
           
            String metodo = dadosReq[0];
            
            if(!metodo.equals("GET")) // Checando se é GET
                break;
            
            String caminhoArquivo = dadosReq[1];
           
            String protocolo = dadosReq[2];
            
            //termina de ler aa linha e escreve o cabeçalho da mensagem 
            while (!linha.isEmpty()) {               
                System.out.println(linha);// escreve a linha           
                linha = buffer.readLine();// pega a prox linha
            }
            
            //pega o arquivo padrão
            if (caminhoArquivo.equals("/")) {
                caminhoArquivo = "index.html";
            }            
            
         
            File arquivo = new File(caminhoArquivo.replaceFirst("/", ""));

            String status = protocolo + " 200 OK\r\n";
            
            // Se o arquvio não existe, muda o status e exibe a pagina de erro
            if (!arquivo.exists()) {
                status = protocolo + " 404 Not Found\r\n";
                arquivo = new File("erro.html");
            }

            /*Todo esse bloco le o arquivo em bytes e formata 
            para ficar de acordo com o padrão do HTTP*/
            byte[] conteudo = Files.readAllBytes(arquivo.toPath()); 
            
            SimpleDateFormat formatador = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss", Locale.ENGLISH);
            
            formatador.setTimeZone(TimeZone.getTimeZone("GMT")); //formata hora
            
            Date data = new Date();           
            String dataFormatada = formatador.format(data) + " GMT"; //formata data
            
            //cabeçalho HTTP
            String header = status
                    + "Location: http://localhost:8000/\r\n"
                    + "Date: " + dataFormatada + "\r\n"
                    + "Server: MeuServidor/1.0\r\n"
                    + "Content-Type: text/html\r\n"
                    + "Content-Length: " + conteudo.length + "\r\n"
                    + "Connection: close\r\n"
                    + "\r\n";
           
            //Cria o canal para a saida da resposta e manda 
            OutputStream resposta = socket.getOutputStream();           
            resposta.write(header.getBytes());            
            resposta.write(conteudo);            
            resposta.flush();
        }
    }
    }
}
