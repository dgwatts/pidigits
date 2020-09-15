package com.github.dgwatts.pidigits;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class HealthService {

	private final int[] activity = new int[60];

	@PostConstruct
	public void setup() {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				activity[getSecond(1000L)] = 0;
			}
		}, 0, 500);
	}

	private int getCurrentSecond() {
		return getSecond(0);
	}

	private int getSecond(long shunt) {
		return (int) (((System.currentTimeMillis() + shunt) / 1000) % 60);
	}

	public void logRequest() {
		int currentSecond = getCurrentSecond();
		synchronized (activity) {
			activity[currentSecond] = activity[currentSecond] + 1;
		}
	}

	public int getLoad() {
		int total = 0;
		for(int i = 0; i < activity.length; i++) {
			total += activity[i];
		}

		return total;
	}
}
