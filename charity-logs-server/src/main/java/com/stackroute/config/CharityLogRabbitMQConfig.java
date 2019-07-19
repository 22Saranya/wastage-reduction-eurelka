package com.stackroute.config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CharityLogRabbitMQConfig {

    @Value("${charityLogs.rabbitmq.queue}")
    String charityQueue;

    @Value("${charity.rabbitmq.exchange}")
    String charityExchange;

    @Value("${charityLogs.rabbitmq.routingkey}")
    private String charityRoutingkey;

    @Bean
    Queue chrQueue() {
        System.out.println("inside charity queue");
        return new Queue(charityQueue, true);
    }

    @Bean
    DirectExchange chrExchange() {
        System.out.println("inside charity exchange");
        return new DirectExchange(charityExchange);
    }

    @Bean
    Binding chrBinding() {
        System.out.println("inside charity binding");
        return BindingBuilder.bind(chrQueue()).to(chrExchange()).with(charityRoutingkey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
