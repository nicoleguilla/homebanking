package com.example.homebanking;

import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		return (args ->{
			Client client1 = new Client();
			client1.setFirstName("Nicole");
			client1.setLastName("Guillamon");
			client1.setEmail("kdsjlad@gmail.com");
			clientRepository.save(client1);
		});
	}


}
