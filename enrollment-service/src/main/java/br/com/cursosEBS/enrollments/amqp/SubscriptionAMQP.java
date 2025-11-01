package br.com.cursosEBS.enrollments.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "amqp.enabled", havingValue = "true", matchIfMissing = true)
//Ativa o amqp so no properties exigido
public class SubscriptionAMQP {

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    //Ao iniciar a aplicacao, cria a fila e detectar que houve conexao
    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    //Payments
    @Bean
    public DirectExchange directExchangePayments(){
        return ExchangeBuilder
                .directExchange("payments.sb")
                .build();
    }
    
    @Bean
    public Queue queuePaymentCreate(){
        return QueueBuilder
                .nonDurable("payments.create")
                .build();
    }

    @Bean
    public Queue queuePaymentStatus(){
        return QueueBuilder
                .nonDurable("payments.status")
                .build();
    }

    @Bean
    public Binding bindPaymentCreate(){
        return BindingBuilder
                .bind(queuePaymentCreate())
                .to(directExchangePayments()).withQueueName();
    }

    @Bean
    public Binding bindPaymentStatus(){
        return BindingBuilder
                .bind(queuePaymentStatus())
                .to(directExchangePayments()).withQueueName();
    }

    //Users
    @Bean
    public DirectExchange directExchangeUsers() {
        return new DirectExchange("users.sb");
    }

    //Converte a message para JSON
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    //Configura o rabbitTempalte para fazer a conversao para o Jackson2JsonMessageConverter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
