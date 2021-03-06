package com.naitoreivun.lop;

import com.naitoreivun.lop.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableConfigurationProperties(MyProperties.class)
@EnableAspectJAutoProxy
public class LeagueOfPhotosApplication implements CommandLineRunner {

    @Autowired
    private MyProperties properties;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter(properties));
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
    public static void main(String[] args) {
        SpringApplication.run(LeagueOfPhotosApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {
    }
}
