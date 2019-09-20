import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
public class Recover {

    private static final String QUEUE_NAME="queue_hrabbit";

    public static void main(String[] args) throws IOException, TimeoutException {

        //获取connection连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道，你必须要声明一个消息消息队列，然后向该队列里推送消息
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //在低版本中，new QueueingConsumer();的方式，但是这种方式已经被废弃了，不建议使用
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "utf-8");
                System.out.println("msg:"+msg);
            }
        };

        channel.basicConsume(QUEUE_NAME,defaultConsumer);
    }
}