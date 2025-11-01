package com.zabat.usuarios_zabat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.zabat")
public class UsuariosZabatApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsuariosZabatApplication.class, args);
    }
}
