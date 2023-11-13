package com.kenBank.Configuration;
/**
 * In this config file we have added the cors config and we are doing it in a different way
 * like creating a supplier inter face since the cors config takes in an anonymous inner class
 */

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.function.Supplier;

@Configuration
public class ProjectSecurityConfiguration {
//    @Autowired
//    PasswordEncoder encoder2;
    private static CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler=new CsrfTokenRequestAttributeHandler();

    public CsrfTokenRequestAttributeHandler getCsrfTokenRequestAttributeHandler() {
        return csrfTokenRequestAttributeHandler;
    }

    Supplier<CorsConfigurationSource> corsConfigurationSourceSupplier= ()->new CorsConfigurationSource() {
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
            // Setting the origin as this particular server
            // The all controller methods can be accessed with the origin req (no need to annotate for each Class and method)
            corsConfiguration.setAllowCredentials(true); // There might be a chance that some req has Credentials in response
            corsConfiguration.setAllowedHeaders(Collections.singletonList("*")); // Allows if a response has headers
            corsConfiguration.setMaxAge(3600L); // The aage of the request here its 3600 mls we can set it to hrs / days according to our prod need
        return corsConfiguration;
    }
};
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf->csrf.disable())
        getCsrfTokenRequestAttributeHandler().setCsrfRequestAttributeName("_csrf");

        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSourceSupplier.get())).
        csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler).ignoringRequestMatchers("/contact","/register")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())).
                authorizeHttpRequests((requests) -> {  // csrf((csrf) -> csrf.disable()). is removed
                    ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                            requests.requestMatchers
                            ("/myAccount", "/myBalance", "/myCards", "/myLoans","/user")).authenticated();
           requests.requestMatchers("/notices", "/contact","/register").permitAll(); // "/UserLogStmt" for logging
                }).formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return (SecurityFilterChain) http.build();
    }

//http.authorizeHttpRequests((requests) -> {
//        requests.requestMatchers("/myAccount","/myBalance","/myCards","/myLoans").authenticated().
//        requestMatchers("/notices","/contact").permitAll();
//        }).formLogin(Customizer.withDefaults())
//        .httpBasic(Customizer.withDefaults()); // This code is the edited version of mine of the above code


    /**
     * As of mow we are only having one user we are going to handle multiple users within our web application using
     * In memoryUserDetailsManager
     */


    /**
     *  1st approach we are using the default password encoder which is deprecated


    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails admin= User.withDefaultPasswordEncoder().username("Nikhil")
                .password("Nikoo@1998")
                .roles("ADMIN")
                .authorities("admin")
                .build();
        UserDetails user1= User.withDefaultPasswordEncoder().username("Mathew")
                .password("Mathayikutty@77")
                .roles("USER")
                .authorities("read")
                .build();
        UserDetails user2= User.withDefaultPasswordEncoder().username("Alex Antony")
                .password("Alex@007")
                .authorities("read")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin,user1,user2);
    }*/


    /**
     * 2nd Approach where we are using the NoOpPasswordEncoder encoder
     * Since NoOpPasswordEncoder is deprecated and the approach is not recommended for prod env
     * @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        InMemoryUserDetailsManager userDetailsManager=new InMemoryUserDetailsManager();
        UserDetails admin=User.withUsername("adminVasu").password("vasutan@123").authorities("Admin").build();
        UserDetails user1=User.withUsername("Gokul Das").password("gokul@8888").authorities("User").build();
        userDetailsManager.createUser(admin);
        userDetailsManager.createUser(user1);
        return userDetailsManager;
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance(); // Deprecated
            // Since we are creating the bean the spring will automatically takes the bean on running time and
            // identifies not to do hashing and encrypting. We don't have to use it the spring will do that
    }

     // Now we are going to comment the below code since we are going to ass
    @Bean
    public UserDetailsManager userDetailsService() { // Or the return type can be the InMemoryUserDetailsManager
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // In this code the withDefaultPasswordEncoder() is deprecated so fixed with another method that is person2.....
//        UserDetails admin1 = User.withDefaultPasswordEncoder().username("admin").
//                password("admin@123").roles("ADMIN").build();
//        UserDetails person1 = User.builder().username("niko").password("Nikhil@1998").roles("USER")
//                .build();
//        UserDetails person2 = User.builder().username("user").
//                password(encoder.encode("user@123")).roles("USER").build(); // Won't work since we are using PasswordEncoderFactories
        // This encoding of the password is with the modern technique like BCryptPasswordEncoder
//            InMemoryUserDetailsManager m=new InMemoryUserDetailsManager();
//            m.createUser(User.builder().username("Nik").password("nooble@123").build());
        UserDetails admin1 =User.builder().username("admin").password(encoder2().encode("admin@123"))
                .roles("ADMIN").build(); // Builder is a method that helps to chain all the methods inside the class and finally build() makes the instance of that class
        UserDetails person3 = User.builder().username("Balubabu").password(encoder2().encode("Balu@1998"))
                .roles("USER").build(); // Works using the BCryptPasswordEncoder
        return new InMemoryUserDetailsManager(admin1,person3);
    }
     */
    /**
     * We are using the JDBCUserDetailsManager to load the user details from the database
     * Creating the bean and passing the DataSource
     * @return
     */

  /**  @Bean
    UserDetailsManager userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    } // If we try to use this userDetailsService even if we are using our own userDetailsService customized we will
    // get an error like
//    No AuthenticationProvider found for org.springframework.security.authentication.UsernamePasswordAuthenticationToken
    // It's because the ambiguity  in the userDetailsService the Auth provider loops through all
    // Auth providers available including DAOAuth and DAOAuth finds more than one userDetailsService which result in conflict
   */
//    @Bean
//    public PasswordEncoder encoder2() {
////        return new BCryptPasswordEncoder(); // Since we are using the plane text to store the password using this can lead to the bad credentials error
//        // When we were trying to use the jdbcUserDetailsManger
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
//        BCrypt.
        return new BCryptPasswordEncoder();
    }
}