import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    
    public static void main(String arg[])
    {
        Socket socket = null;                //Client와 통신하기 위한 Socket
        ServerSocket server_socket = null;  //서버 생성을 위한 ServerSocket 
        BufferedReader in = null;            //Client로부터 데이터를 읽어들이기 위한 입력스트림
        PrintWriter out = null;                //Client로 데이터를 내보내기 위한 출력 스트림
        
        try{
            server_socket = new ServerSocket(9999);
            
        }catch(IOException e)
        {
            System.out.println("해당 포트가 열려있습니다.");
        }
        try {
            
            System.out.println("서버 오픈!!");
            socket = server_socket.accept();    //서버 생성 , Client 접속 대기
            
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //입력스트림 생성
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); //출력스트림 생성
            
            String str = null;
            str = in.readLine();                //Client로부터 데이터를 읽어옴

            System.out.println("Client로 부터 온 메세지 : " + str);
            
            out.write(str);
            out.flush(); 
        }catch(IOException e){
        	e.printStackTrace();
        }

    }
}