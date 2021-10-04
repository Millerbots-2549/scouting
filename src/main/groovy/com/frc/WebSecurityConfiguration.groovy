package com.frc

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

import javax.sql.DataSource

@CompileStatic
@Configuration
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username,password,active from student where username = ?")
                .authoritiesByUsernameQuery("select s.username,sr.role from student s, student_role sr where s.id = sr.student_id and s.username = ?")
    }

    @Override
    protected void configure(HttpSecurity http) {
        http
                .csrf().disable()
                .rememberMe()
                .and().formLogin()
                .and().logout().logoutSuccessUrl('/')
                .and().httpBasic()
                .and().authorizeRequests()
                .antMatchers('/js/**', '/ico/**', '/favicon.ico').permitAll()
                .anyRequest().authenticated()
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }

}
