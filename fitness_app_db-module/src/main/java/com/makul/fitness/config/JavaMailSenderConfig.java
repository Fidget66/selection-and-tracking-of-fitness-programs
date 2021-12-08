package com.makul.fitness.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class JavaMailSenderConfig {

    @Autowired
    private  MailSenderProperties mailSenderProperties;
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailSenderProperties.getHost());
        mailSender.setPort(mailSenderProperties.getPort());
        mailSender.setUsername(mailSenderProperties.getUsername());
        mailSender.setPassword(mailSenderProperties.getPassword());
        Properties props = mailSender.getJavaMailProperties();
        props.putAll(mailSenderProperties.getProps());
        return mailSender;
    }

    @Bean
    public SimpleMailMessage getSimpleMailMessage(){
        return new SimpleMailMessage();
    }
}
