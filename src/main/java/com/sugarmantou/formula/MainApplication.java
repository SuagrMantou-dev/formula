package com.sugarmantou.formula;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.sugarmantou.formula.Services.Node.Infomation;

@SpringBootApplication
public class MainApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        clearConsole();
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener() {
        return event -> {
            try {
                printStartupMessage();
            } catch (Exception e) {
                System.out.println(e);
            }
        };
    }

    private void printStartupMessage() throws Exception {
        String formula_Version = Infomation.Version();
        String api_Port = environment.getProperty("server.port", "8080");

        System.out.println("=====================================");
        System.out.println("      Application Started           ");
        System.out.println("      Name: Formula");
        System.out.println("      Version: " + formula_Version);
        System.out.println("      Port: " + api_Port);
        System.out.println("=====================================");
        // DockerApiCallTest sugarDockerSdk = new DockerApiCallTest();
        // sugarDockerSdk.main();
        // SugarDockerSkCore sugarDockerSdk = new SugarDockerSkCore();
        // String listData = sugarDockerSdk.listContainers();
        // System.out.println(listData);
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }
}
