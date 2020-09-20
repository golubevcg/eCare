package eCare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http)throws  Exception{
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/reg").permitAll()
                .antMatchers("/clientOffice").hasRole("USER")
                .antMatchers("/workerOffice").hasRole("EMPLOYEE")
                .antMatchers("/newTarif").hasRole("EMPLOYEE")
                .antMatchers("/newOption").hasRole("EMPLOYEE")
                .antMatchers("/userRegistration").hasRole("EMPLOYEE")

                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()

                .httpBasic();
    }
}
