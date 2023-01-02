package com.iw.application.security;

import com.iw.application.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

//    private static final String LOGIN_PROCESSING_URL = "/login";
//    private static final String LOGIN_FAILURE_URL = "/login?error";
//    private static final String LOGIN_URL = "/login";

    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

// TODO fyi the following is handled by the superclass VaadinWebSecurity
//
//        // Vaadin handles CSRF internally
//        http.csrf().disable()
//
//                // Register our CustomRequestCache, which saves unauthorized access attempts, so the user is redirected after login.
//                .requestCache().requestCache(new CustomRequestCache())
//
//                // Allow all Vaadin internal requests.
//                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
//
//                // Restrict access to our application.
//                .and().authorizeRequests()
//
//
//                // Allow all requests by logged-in users.
//                .anyRequest().authenticated()
//
//                // Configure the login page.
//                .and().formLogin()
//                .loginPage(LOGIN_URL).permitAll()
//                .loginProcessingUrl(LOGIN_PROCESSING_URL)
//                .failureUrl(LOGIN_FAILURE_URL)
//
//                // Configure logout
//                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);

        super.configure(http);

        // This is important to register your login view to the
        // view access checker mechanism:
        setLoginView(http, LoginView.class);
    }

//  @Bean
//  @Override
//  public UserDetailsService userDetailsService() {
//    UserDetails user = User.withUsername("user")
//            .password("{noop}userpass")
//            .roles("USER")
//            .build();
//
//    return new InMemoryUserDetailsManager(user);
//  }

    /**
     * Allows access to static resources, bypassing Spring Security.
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                // Client-side JS
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",

                // icons and images
                "/icons/**",
                "/images/**",
                "/styles/**",

                // (development mode) H2 debugging console
                "/h2-console/**");
    }


    /**
     * This method is invoked by Spring Boot
     * it defines the PasswordEncoder which is used by Spring Security to authenticate Requests
     *
     * @return the custom PasswordEncoder
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}