package ex01.pyrmont;

import java.net.InetAddress;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;
import java.net.Socket;
import java.net.ServerSocket;

public class HttpServer{
    /**
    *  WEB_ROOT is the directory where out HTML and other files reside.
    *  For this package,WEB_ROOT is the "webroot" directory under the working directory.
    *  The working directory is the location in the file system from where the java command
    *  was invoked.
    */
    // user.dir 代表编译后class文件所在的根目录，ex01的父目录
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    // shutdown command
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    // the shutdown command received
    private boolean shutdown = false;

    public static void main(String[] args){
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await(){
        ServerSocket serverSocket = null;
        int port = 9090;
        try{
            serverSocket = new ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        // Loop waiting for a request
        while(!shutdown){
           Socket socket = null;
           InputStream in = null;
           OutputStream out = null;

           try{
               socket = serverSocket.accept();
               in = socket.getInputStream();
               out = socket.getOutputStream();

               //create Request object and parse
               Request request = new Request(in);
               request.parse();

               //create Response object
               Response response = new Response(out);
               response.setRequest(request);
               response.sendStaticResource();

               //close the socket
               socket.close();

               //check if the previous URI is a shutdown command
               shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
           }catch(Exception e){
               e.printStackTrace();
               continue;
           }
        }
    }
}
