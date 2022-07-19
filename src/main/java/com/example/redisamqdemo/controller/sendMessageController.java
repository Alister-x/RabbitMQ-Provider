package com.example.redisamqdemo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class sendMessageController {
    @Autowired
    RabbitTemplate rabbitTemplate;


    //直接交换机的消息
    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage(){
        String messageId=String.valueOf(UUID.randomUUID());
        String messageData="test message , hello";
        String createTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting",map);
        return "ok";
    }




    //主题交换机的消息
    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1(){
        String messageId=String.valueOf(UUID.randomUUID());
        String messageData="message: M A N";
        String createTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);

        //发送消息的routingkey，要通过config的队列交换机绑定时制定的规程，才能发出
        //交换机马上把所有信息交给所有消费者，消费者再自行处理，不会因为消费者处理慢二阻塞线程
        rabbitTemplate.convertAndSend("topicExchange","topic.man",map);
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2(){
        String messageId=String.valueOf(UUID.randomUUID());
        String messageData="message: W O M A N";
        String createTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend("topicExchange","topic.woman",map);
        return "ok";
    }



    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage(){
        String messageId=String.valueOf(UUID.randomUUID());
        String messageData="message: FanoutMessage";
        String createTime=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend("fanoutExchange",null,map);
        return "ok";
    }



}
