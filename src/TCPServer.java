import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class TCPServer {

    public static final int PORT = 7778; 
    static HashMap<String, Object> hm = new HashMap();
    
    public static void main(String[] args) {

    	ServerSocket serverSocket = null;
    	Socket socket = null;

        try {
            // 1. Server Socket 생성
            serverSocket = new ServerSocket();

            // 2. Binding : Socket에 SocketAddress(IpAddress + Port) 바인딩 함

            InetAddress inetAddress = InetAddress.getLocalHost();
            String localhost = inetAddress.getHostAddress();

            
            serverSocket.bind(new InetSocketAddress(localhost, PORT));

            System.out.println("[server] binding " + localhost);

            // 3. accept(클라이언트로 부터 연결요청을 기다림)
            
            Thread thread[] = new Thread[2];
            int count = 0;
            while(true) {
            	System.out.println("소켓카운트 : " + count);
                socket = serverSocket.accept();  
                thread[count] = new Thread(new Receiver(socket, serverSocket)); 
                thread[count].start();
                count ++;
            }
            // 3.accept(클라이언트로 부터 연결요청을 기다림)
            // .. blocking 되면서 기다리는중, connect가 들어오면 block이 풀림
        } catch (IOException e) {
            e.printStackTrace();
        } 
//        finally {
//
//            try {
//
//                if (serverSocket != null && !serverSocket.isClosed())
//                    serverSocket.close();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
// 
//
//        }
         
        	
    }
    

}