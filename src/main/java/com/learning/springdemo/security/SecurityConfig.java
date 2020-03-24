package com.learning.springdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import sun.security.util.Password;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("buzz")
                .password("123123")
                .authorities("ROLE_USER")
                .and()
                .withUser("woody")
                .password("321321")
                .authorities("ROLE_USER");

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/design", "/orders").hasRole("ROLE_USER")
                .antMatchers("/", "/**").permitAll()
                .and().formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/design", true)
                    .loginProcessingUrl("/authenticate")
                    .usernameParameter("user")
                    .passwordParameter("pwd")
                .and().logout().logoutSuccessUrl("/");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("secret");
    }

//    /* JDBC */
//    @Autowired
//    DataSource dataSource;
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, enabled from Users where username=?")
//                .authoritiesByUsernameQuery("select username, password, enabled from UserAuthorities where username=?")
//                .passwordEncoder(new StandardPasswordEncoder("53cr3t"));
//    }

//    /* LDAP */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .ldapAuthentication()
//                .userSearchBase("ou=people")
//                .userSearchFilter("(uid={0})")
//                .groupSearchBase("ou=groups")
//                .groupSearchFilter("member={0}")
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .passwordAttribute("passcode");
//                //TODO not sure why not work: .contextSource().url("ldap://somehost:389/dc=orgname,dc=com");
//    }
}
