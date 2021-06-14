package com.boubthr.security.Configurations.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.boubthr.security.Configurations.security.UserRole.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder ;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//details of this in next section
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic() ;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails aUser =  User.builder()
                .username("boub")
                .password(passwordEncoder.encode("password"))
                .roles(STUDENT.name())
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin@123"))
                .roles(ADMIN.name())
                .build();
        UserDetails trainee = User.builder()
                .username("trainee")
                .password(passwordEncoder.encode("trainee@123"))
                .roles(ADMIN_TRAINEE.name())
                .build();
        return new InMemoryUserDetailsManager(aUser, admin, trainee);
    }
}
