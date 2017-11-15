package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ProcessorWithMethod extends Thread{

    private PrintStream out;
    private InputStream input;

    public ProcessorWithMethod(Socket socket){
        try{
            input = socket.getInputStream();
            out = new PrintStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void doPost()
    {
        try {
            out.write(("HTTP/1.1 200 OK\n\nWorkerThread: " +
                    "this is by post").getBytes());
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void doGet()
    {
        try {
            out.write(("HTTP/1.1 200 OK\n\nWorkerThread: " +
                    "this is by get").getBytes());
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            byte[] byteBuffer = new byte[1000];

            input.read(byteBuffer);
            for (byte a :byteBuffer)
                System.out.print((char)a);

            if(byteBuffer[0] == 'G' && byteBuffer[1] == 'E' && byteBuffer[2] == 'T')
            {
                doGet();
            }
            else {
                doPost();
            }
            input.close();
            System.out.println("接收到请求");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
