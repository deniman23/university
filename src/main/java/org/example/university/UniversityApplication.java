package org.example.university;

import org.example.university.generator.GeneratedData;
import org.example.university.generator.GeneratedDataService;
import org.example.university.generator.Generator;
import org.example.university.generator.Generator.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ConcurrentLinkedQueue;

@SpringBootApplication
public class UniversityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityApplication.class, args);
	}

	@Bean
	CommandLineRunner run(GeneratedDataService generatedDataService) {
		return args -> {
			Generator generator = new Generator();
			ConcurrentLinkedQueue<Data> generatedDataQueue = generator.generateData(100000);

			generatedDataQueue.forEach(data -> {
				generatedDataService.saveGeneratedData(convertToEntity(data));
			});
		};
	}

	private static GeneratedData convertToEntity(Data data) {
		GeneratedData generatedData = new GeneratedData();
		generatedData.setPk(data.getPk());
		generatedData.setVarchar1(data.getVarchar1());
		generatedData.setVarchar2(data.getVarchar2());
		generatedData.setVarchar3(data.getVarchar3());
		generatedData.setInt1(data.getInt1());
		generatedData.setInt2(data.getInt2());
		generatedData.setTimestamp(data.getTimestamp());
		generatedData.setBool(data.isBool());
		return generatedData;
	}
}

