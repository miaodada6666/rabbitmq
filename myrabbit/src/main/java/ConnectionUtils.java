import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {
    public static Connection getConnection() throws IOException, TimeoutException {
        //创建connection工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置ip
        factory.setHost("127.0.0.1");
        //设置端口号
        factory.setPort(5672);
        //设置用户名称
        factory.setUsername("guest");
        //设置密码
        factory.setPassword("guest");
        //设置vhost
       // factory.setVirtualHost("/hahaha");
        return factory.newConnection();
    }
}