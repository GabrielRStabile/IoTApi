package br.edu.utfpr.iotapi.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QueueService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    public String QUEUE;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(QUEUE, message);
    }
}
