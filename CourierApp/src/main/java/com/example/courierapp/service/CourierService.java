package com.example.courierapp.service;

import com.example.clientapp.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CourierService {

    @Autowired
    KafkaTemplate<String, Order> kafkaTemplate;


    ObjectMapper om = new ObjectMapper();

    private String topicName = "notification-topic";

    @KafkaListener(topics = "notification-topic", groupId = "groupIdddd")
    public void processOrder(String orderString) {
        try {
//            System.out.println("Exeption");
//            if (true) throw new RuntimeException();
            Order order = om.readValue(orderString, Order.class);
            System.out.println("Order " + order.getId() + " is starting delivery!");
            if (order.getStatus().equals("DELIVERED")){
                return;
            }
            order.setStatus("DELIVERED");
//            String orderStr = om.writeValueAsString(order);
            kafkaTemplate.send(topicName, order);
            System.out.println("Order " + order.getId() + " delivered!");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    @KafkaListener(topics = "notification-topic", groupId = "groupIdddd")
//    public void processOrder1(String orderString) {
//        try {
//            Order order = om.readValue(orderString, Order.class);
//            System.out.println("Order " + order.getId() + " is starting delivery!");
//            if (order.getStatus().equals("DELIVERED")){
//                return;
//            }
//            order.setStatus("DELIVERED");
//            String orderStr = om.writeValueAsString(order);
//            kafkaTemplate.send(topicName, orderStr);
//            System.out.println("Order " + order.getId() + " delivered!");
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @KafkaListener(topics = "notification-topic", groupId = "groupIdddd")
//    public void processOrder2(String orderString) {
//        try {
//            Order order = om.readValue(orderString, Order.class);
//            System.out.println("Order " + order.getId() + " is starting delivery!");
//            if (order.getStatus().equals("DELIVERED")){
//                return;
//            }
//            order.setStatus("DELIVERED");
//            String orderStr = om.writeValueAsString(order);
//            kafkaTemplate.send(topicName, orderStr);
//            System.out.println("Order " + order.getId() + " delivered!");
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
