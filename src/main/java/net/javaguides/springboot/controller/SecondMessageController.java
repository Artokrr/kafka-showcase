package net.javaguides.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.kafka.AccessDenialProducer;
import net.javaguides.springboot.struct.AccessDenialMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Slf4j
@RestController
@RequestMapping("api/v1/kafka")
public class SecondMessageController {

    private final AccessDenialProducer producer;

    public SecondMessageController(AccessDenialProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam String rqUID,
                                          @RequestParam String requestNumber,
                                          @RequestParam String contractNumber,
                                          @RequestParam Long visitorId,
                                          @RequestParam String cause) {
        AccessDenialMessage message = new AccessDenialMessage(
                rqUID,
                requestNumber,
                contractNumber,
                visitorId,
                LocalDate.now(),
                cause);
        try {
            producer.sendMessage(message);
            return ResponseEntity.ok("Message sent");
        } catch (Exception e) {
            log.error("Failed to send message", e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message");
        }

    }
}
