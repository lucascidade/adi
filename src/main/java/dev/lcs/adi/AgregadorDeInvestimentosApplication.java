package dev.lcs.adi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AgregadorDeInvestimentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgregadorDeInvestimentosApplication.class, args);
	}

}
