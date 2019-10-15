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


		// TODO: 18.03.2019
		//  html w mailu
		//  zabezpieczyc sie przed niedoplatami (*)
		//  formatowanie daty w widoku
		//  odswiezanie tokena
		//  .
		//  wysyla juz z tpaya localnie z postmana

		SpringApplication.run(AutomatApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("RUN :)");
		for (int i = 0; i < 10; i++) {
			repository.save(new Voucher("x","100.00","serial" + i,"012384756453234" + i, false, null, null));
		}
		for (int i = 0; i < 10; i++) {
			repository.save(new Voucher("y","50.00","s0234" + i,"57383748939384" + i, false, null, null));
		}
		logger.info("sending mail...");
		logger.info("mail sent");

	}


}
