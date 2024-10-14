package com.orange.vinicola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class VinicolaApplication {

    public static void main(String[] args) {

        File file = new File("./data");
        long freeSpace = file.getFreeSpace();
        long totalSpace = file.getTotalSpace();
        System.out.println("Espaço livre: " + freeSpace / (1024 * 1024 * 1024) + " GB");
        System.out.println("Espaço total: " + totalSpace / (1024 * 1024 * 1024) + " GB");

        SpringApplication.run(VinicolaApplication.class, args);
    }

}
