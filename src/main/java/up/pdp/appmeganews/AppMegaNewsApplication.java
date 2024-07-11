package up.pdp.appmeganews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Random;

@EnableJpaAuditing
//@EnableJpaRepositories
@SpringBootApplication
public class AppMegaNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppMegaNewsApplication.class, args);
    }

    @Bean
    public Random random() {
        return new Random();
    }
}
