package com.gonzalo.history;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class History implements CommandLineRunner {

    private final MongoDBRepository mongoDBRepository;

    public History(MongoDBRepository mongoDBRepository) {
        this.mongoDBRepository = mongoDBRepository;
    }

    @Bean
    Queue queue() {
        return new Queue("spring-boot", false);
    }


    @RabbitListener(queues = "spring-boot")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        View view = new View();
        view.setTitle(message);
        mongoDBRepository.save(view);
    }

    public static void main(String[] args) {
        SpringApplication.run(History.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
