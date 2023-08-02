package ru.smartjava.moneytransferservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ru.smartjava"})
public class MoneyTransferServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(MoneyTransferServiceApplication.class, args);
	}

}
