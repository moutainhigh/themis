package me.ele.themis.ruleengine.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 29/8/2016.
 */
@Service
public class MessageConsumer implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("接受消息: " +  message.toString());

        //回复N  ack
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    }
}
