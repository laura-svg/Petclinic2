package com.example.petclinic2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Petclinic2Application {

    public static void main(String[] args) {
try{
        SpringApplication.run(Petclinic2Application.class, args);
    }
catch(Throwable e){
e.printStackTrace();
}
    }

}
