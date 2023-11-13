package com.kenBank.Configuration;

import com.kenBank.Repository.CustomerRepository;
import com.kenBank.pojo.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class KenbankUserPwdAuthenticationProvider implements AuthenticationProvider {

//    @Autowired
//    KenBankUserDetails kenBankUserDetails;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder encoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName=authentication.getName();
        String pwd=  authentication.getCredentials().toString();
        Customer user= customerRepository.findByEmail(userName);
        // here we are trying to add the KenBankUserDetails class and loading the user from db since userDetailsService renturn the
//        Value to Auth-providers here the auth provider is our custom one
//        UserDetails details=kenBankUserDetails.loadUserByUsername(userName);
//
//        String role=details.getAuthorities().stream().map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(""));
//        log.error(role);
//       Customer user= Customer.builder().email(details.getUsername()).pwd(details.getPassword()).
//               role(role).build();
        if (!user.equals(null)){
            if (encoder.matches(pwd,user.getPwd())){ // Here we are just calling the matches() we are not encoding
                // the raw pwd and checks with the one in the db its done by Bcrypt matches()
                List<GrantedAuthority> authorities=new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getRole()));
                return new UsernamePasswordAuthenticationToken(userName,pwd,authorities);
            }else {
                throw new BadCredentialsException("Invalid Password");
            }
        }else {
            throw new BadCredentialsException("No user Registered With this details !");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)); // here we are telling the Provider manager that this AuthProvider supports username pwd auth token type authentication
    }
}
