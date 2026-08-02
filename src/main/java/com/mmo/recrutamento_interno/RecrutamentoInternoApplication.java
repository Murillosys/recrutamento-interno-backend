package com.mmo.recrutamento_interno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RecrutamentoInternoApplication {

    public static void main(String[] args) {

        SpringApplication.run(RecrutamentoInternoApplication.class, args);
        System.out.println("HASH ADMIN1234: " + new BCryptPasswordEncoder().encode("admin1234"));
    }

}
