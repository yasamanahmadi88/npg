package ix.portal.npg.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;

@Configuration(proxyBeanMethods = false)
public class ProblemJackson2Configuration {

    @JsonIgnoreProperties({ "cause", "stackTrace", "message", "localizedMessage", "suppressed" })
    private abstract static class ProblemThrowableMixin {}

    @Bean
    public WebMvcConfigurer problemDetailsJackson2WebMvcConfigurer(ObjectMapper objectMapper) {
        ObjectMapper problemObjectMapper = objectMapper.copy();

        problemObjectMapper.addMixIn(AbstractThrowableProblem.class, ProblemThrowableMixin.class);
        problemObjectMapper.addMixIn(DefaultProblem.class, ProblemThrowableMixin.class);
        problemObjectMapper.addMixIn(ConstraintViolationProblem.class, ProblemThrowableMixin.class);

        ProblemOnlyJackson2HttpMessageConverter problemDetailsConverter =
            new ProblemOnlyJackson2HttpMessageConverter(problemObjectMapper);

        problemDetailsConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_PROBLEM_JSON));

        return new WebMvcConfigurer() {
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(0, problemDetailsConverter);
            }
        };
    }

    private static final class ProblemOnlyJackson2HttpMessageConverter
        extends MappingJackson2HttpMessageConverter {

        private ProblemOnlyJackson2HttpMessageConverter(ObjectMapper objectMapper) {
            super(objectMapper);
        }

        @Override
        public boolean canRead(Class<?> clazz, MediaType mediaType) {
            return false;
        }

        @Override
        public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
            return false;
        }

        @Override
        public boolean canWrite(Class<?> clazz, MediaType mediaType) {
            return Problem.class.isAssignableFrom(clazz) && super.canWrite(clazz, mediaType);
        }

        @Override
        public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
            return Problem.class.isAssignableFrom(clazz) && super.canWrite(type, clazz, mediaType);
        }

        @Override
        public List<MediaType> getSupportedMediaTypes(Class<?> clazz) {
            if (!Problem.class.isAssignableFrom(clazz)) {
                return List.of();
            }

            return super.getSupportedMediaTypes(clazz);
        }
    }
}