package com.msvoo.ms_voo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.msvoo.ms_voo.model.EstadoVoo;
import com.msvoo.ms_voo.enums.TipoEstadoVoo;
import com.msvoo.ms_voo.repository.EstadoVooRepository;
import com.msvoo.ms_voo.entity.Aeroporto;
import com.msvoo.ms_voo.repository.AeroportoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;

@SpringBootApplication
public class MsVooApplication {
	private static final Logger logger = LoggerFactory.getLogger(MsVooApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MsVooApplication.class, args);
	}

	@Bean
	CommandLineRunner seedData(EstadoVooRepository estadoVooRepo, AeroportoRepository aeroportoRepo) {
		return args -> {
			logger.info("[SEED] Inserindo estados de voo e aeroportos iniciais...");
			if (aeroportoRepo.count() == 0) {
				aeroportoRepo.save(new Aeroporto("GRU", "Guarulhos", "Guarulhos", "SP"));
				aeroportoRepo.save(new Aeroporto("GIG", "Galeão", "Rio de Janeiro", "RJ"));
			}
			if (estadoVooRepo.count() == 0) {
				for (TipoEstadoVoo tipo : TipoEstadoVoo.values()) {
					estadoVooRepo.save(new EstadoVoo(tipo));
				}
			}
			logger.info("[SEED] Dados iniciais inseridos com sucesso.");
		};
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
