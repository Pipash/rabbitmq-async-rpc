package rpc.async.rpcclient.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rpc.async.rpcclient.configs.RabbitMQConfig;

import java.util.UUID;

@RestController
@Slf4j
public class ClientController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    /*private final RestTemplate restTemplate;

    public ClientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }*/

    @GetMapping("/send")
    public String helloWorld(String message) {
        String response = "done";
        final String correlationId = UUID.randomUUID().toString();
        // Create a message subject
        Message newMessage = MessageBuilder.withBody(message.getBytes()).build();
        //The publisher sends a message
        rabbitTemplate.convertAndSend(RabbitMQConfig.RPC_EXCHANGE, RabbitMQConfig.RPC_MESSAGE_QUEUE, newMessage, m-> {
            m.getMessageProperties().setCorrelationId(correlationId);
            return m;
        });

        Message result =  rabbitTemplate.receive(RabbitMQConfig.RPC_REPLY_MESSAGE_QUEUE);
        assert result != null;

        return new String(result.getBody());
    }

    @GetMapping("/sendByHTTP")
    public String httpRequest(String string) {
        //log.info(string);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:9090/httpResponse?msg="+string, String.class);
    }
}
