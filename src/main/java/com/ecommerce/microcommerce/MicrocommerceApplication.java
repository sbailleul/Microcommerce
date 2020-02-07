package com.ecommerce.microcommerce;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.logging.LogManager;

@SpringBootApplication
@EnableSwagger2
public class MicrocommerceApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(MicrocommerceApplication.class);

	public static void main(String[] args) {
	    SpringApplication.run(MicrocommerceApplication.class, args);

	    while(1==1){
			LOGGER.info("test");
			LOGGER.debug("test");
		}

	}

}
