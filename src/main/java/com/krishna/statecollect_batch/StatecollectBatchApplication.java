package com.krishna.statecollect_batch;

import com.krishna.statecollect_batch.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@SpringBootApplication
public class StatecollectBatchApplication {

	private final static Logger LOG= LogManager.getLogger(StatecollectBatchApplication.class);

	public static void main(String[] args) {

		ConfigurableApplicationContext context=SpringApplication.run(StatecollectBatchApplication.class, args);
		ActorCollectService service=context.getBean(ActorCollectService.class);
		CopyActorService copyService=context.getBean(CopyActorService.class);
		PrepareFileService prepareFileService=context.getBean(PrepareFileService.class);

		FilmCountTemplateService filmCountTemplateService=context.getBean(FilmCountTemplateService.class);

		CustomerAddressService customerAddressService=context.getBean(CustomerAddressService.class);

		FilmRecordsService filmRecordsService=context.getBean(FilmRecordsService.class);

		CountryCityService countryCityService=context.getBean(CountryCityService.class);

		TestSiva siva=context.getBean(TestSiva.class);

		CountryJpaService countryJpa=context.getBean(CountryJpaService.class);

		System.out.println(Arrays.toString(args));

		if (args[0].equals( "COPY_ACTOR")){
			copyService.copyActor(args);
		} else if (args[0].equals("REPORT")) {
			LOG.info("argu :{}",args);
			LOG.info("arg :{},{},{}",args);
			service.getActors(args);
		} else if (args[0].equals("VALIDATE_ACTOR")) {
			prepareFileService.validateAndPrepare();
		} else if (args[0].equals("FILM_COUNT")) {
			filmCountTemplateService.filmCount(args);
		} else if (args[0].equals("CUSTOMER_ADDRESS")) {
			customerAddressService.fetchCustomerAddress();
		} else if (args[0].equals("FILM_RECORDS")) {
			filmRecordsService.fetchRecords(args);
		} else if (args[0].equals("CITY_COUNTRY")) {
			countryCityService.country(args);
		} else if (args[0].equals( "TEST_SIVA")){
			siva.test();
		} else if (args[0].equals("COUNTRY_JPA")) {
			countryJpa.country(args);

		}

	}

}
