package uk.ncl.ac.sshephard.simplemicro;

import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@Configuration
@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter {
	
    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        // registerReporter allows the MetricsConfigurerAdapter to
        // shut down the reporter when the Spring context is closed    	   	
        registerReporter(CsvReporter
        		.forRegistry(metricRegistry)
                .build(new File("./logs/")))
                .start(10, TimeUnit.SECONDS);
    }
}
