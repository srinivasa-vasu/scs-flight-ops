package io.humourmind.flightops;

import java.time.Duration;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;

import io.humourmind.flightops.domain.FlightDelay;
import io.humourmind.flightops.domain.FlightSchedule;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableBinding(Processor.class)
public class FlightOpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightOpsApplication.class, args);
	}

	@Bean
	public Function<Flux<FlightSchedule>, Flux<FlightDelay>> flyTimeProcessor() {
		return streamElement -> streamElement.map(schedule -> FlightDelay.builder()
				.flightNo(schedule.getFlightNo()).sta(schedule.getSta())
				.ata(schedule.getAta()).delayInterval(Duration
						.between(schedule.getSta(), schedule.getAta()).toMinutes())
				.build());
	}
}
