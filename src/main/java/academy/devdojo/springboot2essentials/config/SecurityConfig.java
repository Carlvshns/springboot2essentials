package academy.devdojo.springboot2essentials.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import academy.devdojo.springboot2essentials.service.DevDojoUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * BasicAuthenticationFilter
     * UsernamePasswordAuthenticationFilter
     * DefaultLoginPageGeneretingFilter
     * DefaultLogoutPageGeneretingFilter
     * FilterSecurityInterceptor
     * Authentication -> Authorization
     * @param http
     * @throws Exception
     */

     private final DevDojoUserDetailsService devDojoUserDetailsService;

     public SecurityConfig (DevDojoUserDetailsService devDojoUserDetailsService){
         this.devDojoUserDetailsService = devDojoUserDetailsService;
     }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      // <!-- Em producao deve ser habilitado -->   
      //http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
      //.authorizeRequests().anyRequest().authenticated().and().httpBasic();

        http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/animes/admin/**").hasRole("ADMIN")
        .antMatchers("/animes/**").hasRole("USER")
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .and()
        .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        System.out.println("Password encoded {} "+passwordEncoder.encode("rockblin0123")); 
       
       // <!-- Authentication pela memoria -->
        auth.inMemoryAuthentication()
       .withUser("isa")
       .password(passwordEncoder.encode("rockblin0123"))
       .roles("USER", "ADMIN")
       .and()
       .withUser("william")
       .password(passwordEncoder.encode("rockblin0123"))
       .roles("USER");

       // <!-- Vindo do banco de dados -->
       auth.userDetailsService(devDojoUserDetailsService).passwordEncoder(passwordEncoder);
    }
}
