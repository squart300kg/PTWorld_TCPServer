import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Receiver implements Runnable {

	Socket socket;
	ServerSocket serverSocket = null;
	
	
	InputStream is = null;
    InputStreamReader isr = null;
    BufferedReader br = null;

    OutputStream os = null;
    OutputStreamWriter osw = null;
    PrintWriter pw = null;
 
	Receiver(Socket socket, ServerSocket serverSocket){
		this.socket = socket;
		this.serverSocket = serverSocket;
		 
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

            System.out.println("[server] connected by client");
            System.out.println("[server] Connect with " + socketAddress.getHostString() + " " + socket.getPort() + "입장");

         // inputStream 가져와서 (주 스트림) StreamReader와 BufferedReader로 감싸준다 (보조 스트림)
	        is = socket.getInputStream();
	        isr = new InputStreamReader(is, "UTF-8");
	        br = new BufferedReader(isr);

	        // outputStream 가져와서 (주 스트림) StreamWriter와 PrintWriter로 감싸준다 (보조 스트림)
	        os = socket.getOutputStream();
	        osw = new OutputStreamWriter(os, "UTF-8");
	        pw = new PrintWriter(osw, true); 
	        
            synchronized (TCPServer.hm) {
	            TCPServer.hm.put(socketAddress.getHostString() + " " + socket.getPort(), pw);
	        } 
            while (true) {
                String buffer = null;
                buffer = br.readLine(); // Blocking
                if (buffer == null) {

                    // 정상종료 : remote socket close()
                    // 메소드를 통해서 정상적으로 소켓을 닫은 경우
                    System.out.println("[server] closed by client");
                    break;

                }

                System.out.println("[server] recived : " + buffer);
//                pw.println(buffer);
                broadcast(buffer);
                

            }
			
		}catch(IOException e){
            e.printStackTrace();
        } finally {

            try {

                if (serverSocket != null && !serverSocket.isClosed())
                    serverSocket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
		
	}
	public void broadcast(String message) {
		synchronized (TCPServer.hm) {
            Collection<Object> collection = TCPServer.hm.values();
            Iterator<?> iter = collection.iterator();
            while(iter.hasNext()) {
                pw = (PrintWriter)iter.next();
                pw.println(message);
                pw.flush();
            }
        } 
	}
	
}


