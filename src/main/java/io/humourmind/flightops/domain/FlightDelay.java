package io.humourmind.flightops.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class FlightDelay {

	private String flightNo;
	private LocalDateTime sta;
	private LocalDateTime ata;
	private long delayInterval;
}
