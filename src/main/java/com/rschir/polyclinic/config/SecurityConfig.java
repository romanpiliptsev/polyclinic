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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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

    // Secure the endpoints with HTTP Basic authentication
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
//        http
//                .csrf()
//                .disable()
//                .cors()
//                .disable()
//                .authorizeRequests()
////                .antMatchers("/").permitAll()
////                .antMatchers("/css/**").permitAll()
////                .antMatchers("/js/**").permitAll()
////                .antMatchers("/img/**").permitAll()
////                .antMatchers("/products").permitAll()
////                .antMatchers("/product").permitAll()
////                .antMatchers("/login", "/sign").permitAll()
//                .antMatchers("/patients").hasAuthority("doctor")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().and().logout().logoutSuccessUrl("/").and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .sessionFixation().migrateSession();
    }
}
