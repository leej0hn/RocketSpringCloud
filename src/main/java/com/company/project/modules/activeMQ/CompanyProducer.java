package com.company.project.modules.activeMQ;

import com.jeespring.common.redis.RedisUtils;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息生产者.
 * @author gaowh
 * @version v.0.1
 * @date 2016年8月23日
 */

@Service("companyProducer")
public class CompanyProducer {
    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    public static final  String ActiveMQQueueKey="Company.ActiveMQ.queue";
    public static final  String ActiveMQQueueKeyA="Company.ActiveMQ.queueA";
    public static final  String ActiveMQQueueKeyB="Company.ActiveMQ.queueB";
    public static String RUN_MESSAGE="ActvieMQ连接异常，请开启ActvieMQ服务.";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    @Autowired
    private JmsMessagingTemplate jmsTemplate;
    // 发送消息，destination是发送到的队列，message是待发送的消息
    public void sendMessage( final String message){
        try{
            Destination destination = new ActiveMQQueue(CompanyProducer.ActiveMQQueueKey);
            jmsTemplate.convertAndSend(destination, message);
        }catch (Exception e){
            logger.error(dateFormat.format(new Date()) + " | " +"ActvieMQ:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
    }

    public void sendMessageA( final String message){
        try{
            Destination destinationA = new ActiveMQQueue(CompanyProducer.ActiveMQQueueKeyA);
            jmsTemplate.convertAndSend(destinationA, message);
        }catch (Exception e){
            logger.error(dateFormat.format(new Date()) + " | " +"ActvieMQ:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
    }
    public void sendMessageB(final String message){
        try{
            Destination destinationB = new ActiveMQQueue(CompanyProducer.ActiveMQQueueKeyA);
            jmsTemplate.convertAndSend(destinationB, message);
        }catch (Exception e){
            logger.error(dateFormat.format(new Date()) + " | " +"ActvieMQ:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
    }
    @JmsListener(destination="company.out.queue")
    public void consumerMessage(String text){
        System.out.println(dateFormat.format(new Date()) + " | " +"ActvieMQ:从out.queue队列收到的回复报文为:"+text);
    }
}