package com.proyecto.g2.equipay;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EquipayApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(EquipayApplication.class, args);
        openSwagger();
    }

    private static void openSwagger() throws IOException {
        Runtime rt = Runtime.getRuntime();
        rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8080/swagger-ui/index.html");
    }

}
