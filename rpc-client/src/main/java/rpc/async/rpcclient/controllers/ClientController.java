package rpc.async.rpcclient.controllers;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rpc.async.rpcclient.configs.RabbitMQConfig;

import java.util.HashMap;

@RestController
public class ClientController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public String helloWorld(String message) {
        // Create a message subject
        Message newMessage = MessageBuilder.withBody(message.getBytes()).build();
        //The customer sends a message
        rabbitTemplate.convertAndSend(RabbitMQConfig.RPC_MESSAGE_QUEUE, newMessage);

        //Message result = rabbitTemplate.sendAndReceive(RabbitMQConfig.RPC_EXCHANGE, RabbitMQConfig.RPC_MESSAGE_QUEUE, newMessage);
        String response = "done";
        /*if (result != null) {
            // To get message sent correlationId
            String correlationId = newMessage.getMessageProperties().getCorrelationId();

            // Get response header information
            HashMap<String, Object> headers = (HashMap<String, Object>) result.getMessageProperties().getHeaders();
            // Access server Message returned id
            String msgId = (String) headers.get("spring_returned_message_correlation");
            if (msgId.equals(correlationId)) {
                response = new String(result.getBody());
            }
        }*/
        return response;
    }
}
