package br.edu.utfpr.iotapi.controllers;

import br.edu.utfpr.iotapi.services.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private QueueService queueService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody String message) {
        queueService.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }
}
