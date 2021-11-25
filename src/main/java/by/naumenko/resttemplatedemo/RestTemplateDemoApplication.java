package by.naumenko.resttemplatedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestTemplateDemoApplication {


    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(RestTemplateDemoApplication.class, args);
        Communication communication = context.getBean("communication", Communication.class);

//        System.out.println(communication.getAllUsers());

        System.out.println("ANSWER: " + communication.getAnswer());
    }

}
