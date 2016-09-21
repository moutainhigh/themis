package me.ele.themis.ruleengine.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 29/8/2016.
 */
@Service
public class MessageProducer {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendMsg(Object msg){
        amqpTemplate.convertAndSend("queueTest",msg);
    }

}
