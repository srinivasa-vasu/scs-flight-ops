package io.humourmind.flightops;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;

import io.humourmind.flightops.domain.FlightDelay;
import io.humourmind.flightops.domain.FlightSchedule;

@SpringBootApplication
@EnableBinding(Processor.class)
public class FlightOpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightOpsApplication.class, args);
	}

	private final Processor processor;

	FlightOpsApplication(Processor processor) {
		this.processor = processor;
	}

	@StreamListener(Processor.INPUT)
	public void delayProcessor(FlightSchedule flight) {
		processor.output()
				.send(MessageBuilder.withPayload(FlightDelay.builder()
						.flightNo(flight.getFlightNo()).sta(flight.getSta())
						.ata(flight.getAta()).delayInterval(Duration
								.between(flight.getSta(), flight.getAta()).toMinutes())
						.build()).build());
	}
}
