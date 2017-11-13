package ex02.pyrmont;

import java.net.*;
import java.io.*;

public class HttpServer1{
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private boolean shutdown = false;

    public static void main(String[] args){
        HttpServer1 server = new HttpServer1();
        server.await();
    }

    public void await(){
        ServerSocket server = null;
        int port = 9090;

        try{
            server = new ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        // Loop waiting for a request
        while(!shutdown){
            Socket       socket = null;
            InputStream  input  = null;
            OutputStream output = null;

            try{
                socket = server.accept();
                input  = socket.getInputStream();
                output = socket.getOutputStream();

                // create Request object and parse
                Request request = new Request(input);
                request.parse();

                // create Response object
                Response response = new Response(output);
                response.setRequest(request);

                // check if this is a request for a servlet or a static resource
                // a request for a servlet begins with "/servlet"
                if(request.getUri().startsWith("/servlet/")){
                    ServletProcessor1 processor = new ServletProcessor1();
                    processor.process(request,response);
                }else{
                    StaticResourceProcessor processor = new StaticResourceProcessor();
                    processor.process(request,response);
                }
                // close the socket
                socket.close();
                // check if the previous URI is a shutdown command
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            }catch(Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }

    }
}
