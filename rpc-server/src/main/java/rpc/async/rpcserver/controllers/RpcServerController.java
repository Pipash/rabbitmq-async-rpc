package rpc.async.rpcserver.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rpc.async.rpcserver.configs.RabbitMqConfig;

@Component
@Slf4j
public class RpcServerController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMqConfig.RPC_MESSAGE_QUEUE)
    public void process(Message message) {
        byte[] body = message.getBody();
        log.info("server "+new String(body));
        //This is the message to be returned by the server
        Message build = MessageBuilder.withBody(("I am the rpc server, I received the message from the client：" + new String(body)).getBytes()).build();
        final String correlationId = message.getMessageProperties().getCorrelationId();
        rabbitTemplate.convertAndSend(RabbitMqConfig.RPC_REPLY_EXCHANGE, RabbitMqConfig.RPC_REPLY_MESSAGE_QUEUE, build, m -> {
            m.getMessageProperties().setCorrelationId(correlationId);
            return m;
        });
    }
}
