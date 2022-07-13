package com.example.palmenttoapp.service;

import com.example.clientapp.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PalmentoService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper om = new ObjectMapper();

    private String topicName = "notification-topic";

    @KafkaListener(topics = "order-topic", groupId = "groupId")
    public void processOrder(String orderString) {
        try {
            Order order = om.readValue(orderString, Order.class);
            System.out.println("Order " + order.getId() + " is starting cooking!");
            order.setStatus("READY");
            String orderStr = om.writeValueAsString(order);
            kafkaTemplate.send(topicName, orderStr);
            System.out.println("Order " + order.getId() + " is ready!");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
