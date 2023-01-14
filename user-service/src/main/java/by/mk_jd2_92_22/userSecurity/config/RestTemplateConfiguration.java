package by.mk_jd2_92_22.userSecurity.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }

}



//public class RestTemplateFactory
//        implements FactoryBean<RestTemplate>, InitializingBean {
//
//    private RestTemplate restTemplate;
//
//    public RestTemplate getObject() {
//        return restTemplate;
//    }
//    public Class<RestTemplate> getObjectType() {
//        return RestTemplate.class;
//    }
//    public boolean isSingleton() {
//        return true;
//    }
//
//    public void afterPropertiesSet() {
//        HttpHost host = new HttpHost("localhost", 8082, "http");
//        restTemplate = new RestTemplate(
//                new HttpComponentsClientHttpRequestFactoryBasicAuth(host));
//    }
//}
