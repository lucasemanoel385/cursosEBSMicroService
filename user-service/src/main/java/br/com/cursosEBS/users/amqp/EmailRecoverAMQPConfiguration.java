package br.com.cursosEBS.users.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailRecoverAMQPConfiguration {

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    //Ao iniciar a aplicacao, cria a fila e detectar que houve conexao
    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
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

    //Cria a exchange email.ex
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("email.ex");
    }

    @Bean
    public DirectExchange directExchangeSubscription(){
        return ExchangeBuilder
                .directExchange("users.sb")
                .build();
    }

    @Bean
    public Queue queueUpdateProfile(){
        return QueueBuilder
                .nonDurable("users.profile")
                .build();
    }

    @Bean
    public Binding bindUpdateProfile(DirectExchange directExchange){
        return BindingBuilder
                .bind(queueUpdateProfile())
                .to(directExchange).withQueueName();
    }

}
