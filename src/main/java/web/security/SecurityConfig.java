package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import web.security.handler.LoginSuccessHandler;
import web.detailsService.MyUserDetailService;

@Configuration
@EnableWebSecurity
@ComponentScan("web.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService getuserDetailService(){
        return new MyUserDetailService();
    }

    @Autowired
    AuthProviderImpl authProvider;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider).userDetailsService(getuserDetailService());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/hello").permitAll()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/user/**").access("hasAnyRole('ADMIN','USER')")
                .and()
                .formLogin()
                .successHandler(loginSuccessHandler);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
