import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerServer implements Runnable {//将队列发布到本地服务器端口

    public static int SERVICE_PORT = 8002;
    private final Socket socket;
    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));//从客户端得到的数据
                PrintWriter out = new PrintWriter(socket.getOutputStream())//要发送给客户端的程序
        )
        {
            while (true) {
                String str = in.readLine();
                if (str == null) {
                    continue;
                }
                System.out.println("接收到原始数据：" + str);

                if (str.equals("CONSUME")) { //CONSUME 表示要消费一条消息
                    //从消息队列中消费一条消息
                    String message = Broker.consume();
                    out.println(message);
                    out.flush();
                } else if (str.contains("SEND:")){
                    //接受到的请求包含SEND:字符串 表示生产消息放到消息队列中
                    Broker.produce(str);
                }else {
                    System.out.println("原始数据:"+str+"没有遵循协议,不提供相关服务");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(SERVICE_PORT);
        while (true) {
            BrokerServer brokerServer = new BrokerServer(server.accept());//开启对服务器指定端口的监视
            new Thread(brokerServer).start();//多线程实现，保证服务器一直打开
        }
    }
}