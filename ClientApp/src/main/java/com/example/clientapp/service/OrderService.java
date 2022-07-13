package com.example.clientapp.service;

import com.example.clientapp.entity.Order;
import com.example.clientapp.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;;

@Service
public class OrderService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    OrderRepository orderRepository;

    ObjectMapper om = new ObjectMapper();

    @Value(value = "${order.topic.name}")
    private String orderTopicName;

    public Order save(Order order) {
        order.setStatus("CREATED");
        orderRepository.save(order);
        try {
            String orderStr = om.writeValueAsString(order);
            kafkaTemplate.send(orderTopicName, orderStr);
            System.out.println("Order " + order.getId() + " created!");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @KafkaListener(topics = "notification-topic", groupId = "groupId")
    public void processOrder(String orderString) {
        try {
            Order order = om.readValue(orderString, Order.class);
            orderRepository.save(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
