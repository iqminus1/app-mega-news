package uz.pdp.appmeganews;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uz.pdp.appmeganews.entity.Code;

import java.io.Serializable;
import java.util.Properties;
import java.util.Random;

@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories
@EnableCaching
@MultipartConfig
@SpringBootApplication
public class AppMegaNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppMegaNewsApplication.class, args);
    }

    @Bean
    public Random random() {
        return new Random();
    }
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("wwxeww1wwxeww@gmail.com");
        mailSender.setPassword("mmzi sfot cdjs mgwc");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        return mailSender;
    }
}
