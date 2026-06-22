package com.transporte;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransportApiApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
			.ignoreIfMissing()
			.load();

		if (dotenv.get("MONGODB_URI") != null) {
			System.setProperty("MONGODB_URI", dotenv.get("MONGODB_URI"));
		}
		if (dotenv.get("JWT_SECRET") != null) {
			System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		}
		if (dotenv.get("JWT_EXPIRATION") != null) {
			System.setProperty("JWT_EXPIRATION", dotenv.get("JWT_EXPIRATION"));
		}

		SpringApplication.run(TransportApiApplication.class, args);
	}

}
