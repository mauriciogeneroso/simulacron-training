package com.generoso.ft.training.simulacron.config;

import com.generoso.ft.training.simulacron.YamlFileApplicationContextInitializer;
import com.generoso.ft.training.simulacron.utils.SimulacronUtils;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {
        TestConfiguration.class,
        SimulacronUtils.class
},
        initializers = YamlFileApplicationContextInitializer.class)
@CucumberContextConfiguration
public class CucumberSpringConfiguration {
}