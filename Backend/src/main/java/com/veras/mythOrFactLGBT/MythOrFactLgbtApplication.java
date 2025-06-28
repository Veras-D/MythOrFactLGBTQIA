package com.veras.mythOrFactLGBT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MythOrFactLgbtApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MythOrFactLgbtApplication.class);
		app.addListeners(new com.veras.mythOrFactLGBT.config.DotenvApplicationListener());
		app.run(args);
	}

}
