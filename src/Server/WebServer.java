package Server;

import Client.Processor;
import Client.ProcessorWithMethod;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//简单webserver 充当的作用就是接受客户端程序发过来的请求，
// 根据请求内容转换的数据找到自己制定路径下的文件，
// 将其文件转为流返回客服端，这个过程所用的协议为http。


//使用 http://localhost:8080/test.txt
public class WebServer {
    /** 默认使用的服务器Socket端口号 */
    public static final int HTTP_PORT = 8080;
    private ServerSocket serverSocket;

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();


    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Web Server startup on  " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                // 通过线程的方式来处理客户的请求
//                cachedThreadPool.execute(new Processor(socket));
//                System.out.println(cachedThreadPool);
                new ProcessorWithMethod(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * WebServer类的启动方法，可以通过命令行参数指定当前Web服务器所使用的端口号。
     */
    public static void main(String[] args) throws Exception {
        WebServer server = new WebServer();
        if (args.length == 1) {
            server.startServer(Integer.parseInt(args[0]));
        } else {
            server.startServer(WebServer.HTTP_PORT);
        }
    }
}
