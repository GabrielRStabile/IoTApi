package br.edu.utfpr.iotapi.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueueService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SimpMessagingTemplate template;

    @Value("${spring.rabbitmq.queue}")
    public String QUEUE;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(QUEUE, message);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(String message) {
        template.convertAndSend("/topic/message", message);
    }
}
