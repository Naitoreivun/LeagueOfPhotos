package com.naitoreivun.lop;

import com.naitoreivun.lop.dao.SeasonDAO;
import com.naitoreivun.lop.domain.Image;
import com.naitoreivun.lop.security.JwtFilter;
import com.naitoreivun.lop.service.ImageService;
import com.naitoreivun.lop.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(MyProperties.class)
public class LeagueOfPhotosApplication implements CommandLineRunner {

    @Autowired
    private MyProperties properties;
    @Autowired
    private VoteService voteService;
    @Autowired
    private ImageService imageService;

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
