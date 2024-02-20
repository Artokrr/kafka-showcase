package net.javaguides.springboot.controller;

import net.javaguides.springboot.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaProducer kafkaProducer;

    @Test
    public void testPublishWhenMessagePublishedThenReturnOk() throws Exception {
        String message = "hello world";

        doNothing().when(kafkaProducer).sendMessage(message);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/kafka/publish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Message sent to the topic"));
    }

    @Test
    public void testPublishWhenMessageNotPublishedThenReturnInternalServerError() throws Exception {
        String message = "hello world";

        doThrow(new RuntimeException("Failed to send message to the topic")).when(kafkaProducer).sendMessage(message);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/kafka/publish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Failed to send message to the topic"));
    }
}