import java.net.InetAddress;
import java.net.Socket;

public class   ProduceClient   {

    public static void main(String[] args) throws Exception {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        MqClient client = new MqClient();
        client.produce("SEND:Hello World");

    }
}
