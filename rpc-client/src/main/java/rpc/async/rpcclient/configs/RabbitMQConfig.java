package rpc.async.rpcclient.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String RPC_MESSAGE_QUEUE = "rpc_msg_queue";
    public static final String RPC_REPLY_MESSAGE_QUEUE = "rpc_reply_msg_queue";
    public static final String RPC_EXCHANGE = "rpc_exchange";
    /** *
     * Set sending RPCQueue message
     Configure the Send Message Queue*/
    @Bean
    Queue msgQueue() {

        return new Queue(RPC_MESSAGE_QUEUE);
    }
    /** *
     * Return Queue Configuration
     */
    @Bean
    Queue replyQueue() {

        return new Queue(RPC_REPLY_MESSAGE_QUEUE);
    }
    /** *
     * Switch setting
     */
    @Bean
    TopicExchange exchange() {

        return new TopicExchange(RPC_EXCHANGE);
    }
    /** *
     * Queuing and Switch Link Request
     */
    @Bean
    Binding msgBinding() {

        return BindingBuilder.bind(msgQueue()).to(exchange()).with(RPC_MESSAGE_QUEUE);
    }
}
