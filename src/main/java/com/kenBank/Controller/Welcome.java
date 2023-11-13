package com.kenBank.Controller;//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.web.access.intercept.AuthorizationFilter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Since it's a basic java class without authentication
 *
 */

@RestController
public class Welcome {
    @GetMapping("/welcomeNoSecurity")
    public String welcomeWithoutSecurity(){
        return "Welcome to Spring Security Framework-6 folks!";
    }

}
