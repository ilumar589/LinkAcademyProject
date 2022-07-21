package com.linkacademy.commerceapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.constraints.NotNull;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BasicAuthSecurityConfig implements WebMvcConfigurer  {


    @NotNull
    @Value("${commerce.security.user.name}")
    private String userName;

    @NotNull
    @Value("${commerce.security.user.password}")
    private String userPassword;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(userName).password(new BCryptPasswordEncoder().encode(userPassword)).roles("ADMIN_ROLE");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/commerce/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false).maxAge(3600);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .anonymous();
//                .authorizeRequests()
//                .antMatchers(UrlMapping.AUTH + UrlMapping.SIGN_UP).permitAll()
//                .antMatchers(UrlMapping.AUTH + UrlMapping.LOGIN).permitAll()
//                .antMatchers(UrlMapping.VALIDATE_JWT).permitAll()
//                .antMatchers("/swagger-ui/**").permitAll()
//                .anyRequest().authenticated().and().httpBasic();

        return http.build();
    }
}
