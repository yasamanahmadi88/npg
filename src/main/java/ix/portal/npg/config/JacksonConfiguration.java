package ix.portal.npg.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.hibernate7.Hibernate7Module;
import com.fasterxml.jackson.datatype.hibernate7.Hibernate7Module.Feature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    public Jdk8Module jdk8Module() {
        return new Jdk8Module();
    }

    @Bean
    public Hibernate7Module hibernate7Module() {
        Hibernate7Module hibernate7Module = new Hibernate7Module();
        hibernate7Module.configure(Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
        return hibernate7Module;
    }

    @Bean
    public ObjectMapper objectMapper(
        Hibernate7Module hibernate7Module,
        JavaTimeModule javaTimeModule,
        Jdk8Module jdk8Module
    ) {
        return new ObjectMapper()
            .registerModule(hibernate7Module)
            .registerModule(javaTimeModule)
            .registerModule(jdk8Module)
            .registerModule(new JaxbAnnotationModule());
    }
}