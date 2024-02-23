package net.javaguides.springboot.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.struct.AccessDenialMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class AccessDenialProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void sendMessage(AccessDenialMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String messageJson = objectMapper.writeValueAsString(message);

        Message<String> messageWithHeaders = MessageBuilder.withPayload(messageJson)
                .setHeader(KafkaHeaders.TOPIC, "ACCESS-REQUEST.DENY")
                .setHeader("spring_json_header_types", "{\"contentType\":\"java.lang.String\"" +
                        ",\"target-protocol\":\"java.lang.String\"}")
                .setHeader("contentType", "application/json")
                .setHeader("target-protocol", "kafka")
                .build();

        kafkaTemplate.send(messageWithHeaders);
    }

}
