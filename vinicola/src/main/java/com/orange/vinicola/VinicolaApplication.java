package com.orange.vinicola;

import jdk.jfr.Enabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class VinicolaApplication {

    public static void main(String[] args) {
        //Verificação base de tamanho de disco livre para o banco de dados
        File file = new File("./data");
        long freeSpace = file.getFreeSpace(); // Espaço livre em bytes
        long totalSpace = file.getTotalSpace(); // Espaço total em bytes
        System.out.println("Espaço livre: " + freeSpace / (1024 * 1024 * 1024) + " GB");
        System.out.println("Espaço total: " + totalSpace / (1024 * 1024 * 1024) + " GB");

        SpringApplication.run(VinicolaApplication.class, args);
    }

}
