
package br.com.fiap.EcoTrack.steps;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = CucumberSpringConfiguration.TestConfig.class)
public class CucumberSpringConfiguration {

    @Configuration
    static class TestConfig {
    }
}
