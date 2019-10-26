package com.kuros.automatgae;

import com.kuros.automatgae.model.Voucher;
import com.kuros.automatgae.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.Logger;

@SpringBootApplication
@EnableScheduling
public class AutomatApplication implements CommandLineRunner {

	Logger logger = Logger.getLogger(AutomatApplication.class.getName());

	@Autowired
	private VoucherRepository repository;

	public static void main(String[] args) {


		// TODO:
		//  frontend
		//  odswiezanie tokena (do uporzadkowania)
		//  wysylka maila po zakupie
		//  * tworzenie kont

		SpringApplication.run(AutomatApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}


}
