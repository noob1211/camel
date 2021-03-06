/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.spring.boot;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.component.properties.PropertiesParser;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(CamelConfigurationProperties.class)
@Import(SpringConversionServiceConfiguration.class)
public class CamelAutoConfiguration {

    /**
     * Spring-aware Camel context for the application. Auto-detects and loads all routes available in the Spring
     * context.
     */
    @Bean
    CamelContext camelContext(ApplicationContext applicationContext,
                              CamelConfigurationProperties configurationProperties) {
        CamelContext camelContext = new SpringCamelContext(applicationContext);

        if (!configurationProperties.isJmxEnabled()) {
            camelContext.disableJMX();
        }

        return camelContext;
    }

    @Bean
    RoutesCollector routesCollector() {
        return new RoutesCollector();
    }

    /**
     * Default producer template for the bootstrapped Camel context.
     */
    @Bean
    ProducerTemplate producerTemplate(CamelContext camelContext,
                                      CamelConfigurationProperties configurationProperties) {
        return camelContext.createProducerTemplate(configurationProperties.getProducerTemplateCacheSize());
    }

    /**
     * Default consumer template for the bootstrapped Camel context.
     */
    @Bean
    ConsumerTemplate consumerTemplate(CamelContext camelContext,
                                      CamelConfigurationProperties configurationProperties) {
        return camelContext.createConsumerTemplate(configurationProperties.getConsumerTemplateCacheSize());
    }

    @Bean
    PropertiesParser propertiesParser() {
        return new SpringPropertiesParser();
    }

    @Bean
    PropertiesComponent properties() {
        PropertiesComponent properties = new PropertiesComponent();
        properties.setPropertiesParser(propertiesParser());
        return properties;
    }

}
