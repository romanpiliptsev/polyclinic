package com.rschir.polyclinic.config;

import com.rschir.polyclinic.dbservice.services.DoctorServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DoctorServiceImpl doctorService;
    public SecurityConfig(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(doctorService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/stuff/**").hasAnyAuthority("registry", "admin", "doctor")
                .antMatchers("/stuff/admin/**").hasAuthority("admin")
                .antMatchers("/stuff/registry/**").hasAnyAuthority("registry", "admin")
                .antMatchers("/stuff/doc/**").hasAuthority("doctor")
                .antMatchers("/user/**").hasAuthority("user")
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
