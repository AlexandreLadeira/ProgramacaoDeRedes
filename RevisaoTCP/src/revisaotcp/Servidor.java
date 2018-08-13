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
        
        while(true){//não para de aceitar conexoes
       
        Socket socket = servidor.accept();
        
        //verifica se esta conectado
        if (socket.isConnected()) {            
           
            //Leitor do input do cliente
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("\n");
            /* Lê a primeira linha
             contem as informaçoes da requisição
             */
            String linha = buffer.readLine();
            //quebra a string pelo espaço em branco
            String[] dadosReq = linha.split(" ");
            //pega o metodo
            String metodo = dadosReq[0];
            //paga o caminho do arquivo
            String caminhoArquivo = dadosReq[1];
            //pega o protocolo
            String protocolo = dadosReq[2];
            //Enquanto a linha não for vazia
            while (!linha.isEmpty()) {
                //imprime a linha
                System.out.println(linha);
                //lê a proxima linha
                linha = buffer.readLine();
            }
            //se o caminho foi igual a / entao deve pegar o /index.html
            if (caminhoArquivo.equals("/")) {
                caminhoArquivo = "index.html";
            }            
            
            //abre o arquivo pelo caminho
            File arquivo = new File(caminhoArquivo.replaceFirst("/", ""));

            String status = protocolo + " 200 OK\r\n";
            //se o arquivo não existe então abrimos o arquivo de erro, e mudamos o status para 404
            if (!arquivo.exists()) {
                status = protocolo + " 404 Not Found\r\n";
                arquivo = new File("404.html");
            }

            //lê todo o conteúdo do arquivo para bytes
            byte[] conteudo = Files.readAllBytes(arquivo.toPath());
            //cria um formato para o GMT espeficicado pelo HTTP
            SimpleDateFormat formatador = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss", Locale.ENGLISH);
            formatador.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date data = new Date();
            //Formata a dara para o padrao
            String dataFormatada = formatador.format(data) + " GMT";
            //cabeçalho padrão da resposta HTTP
            String header = status
                    + "Location: http://localhost:8000/\r\n"
                    + "Date: " + dataFormatada + "\r\n"
                    + "Server: MeuServidor/1.0\r\n"
                    + "Content-Type: text/html\r\n"
                    + "Content-Length: " + conteudo.length + "\r\n"
                    + "Connection: close\r\n"
                    + "\r\n";
            //cria o canal de resposta utilizando o outputStream
            OutputStream resposta = socket.getOutputStream();
            //escreve o headers em bytes
            resposta.write(header.getBytes());
            //escreve o conteudo em bytes
            resposta.write(conteudo);
            //encerra a resposta
            resposta.flush();
        }
    }
    }
}
