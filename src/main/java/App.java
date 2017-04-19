import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import benchmarks.Test;

public class App {
	
	public static void main(String[] args) throws RunnerException, UnknownHostException {
		

		int max = 10;
		String[] inputs = new String[max];
		for (int i = 0; i < max; i++) {
			inputs[i] = String.valueOf(i);
		}
		
		Options opts = new OptionsBuilder()
				.include(Test.class.getSimpleName())
				.warmupIterations(5)
				.warmupTime(TimeValue.seconds(1))
				.measurementIterations(5)
				.measurementTime(TimeValue.seconds(1))
				.forks(7)
				.param("arg", inputs)
				.result("results-" + InetAddress.getLocalHost().getHostName() + ".csv")
				.build();
		
		new Runner(opts).run();
	}
}
