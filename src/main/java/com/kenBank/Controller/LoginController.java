package com.kenBank.Controller;

import com.kenBank.Repository.CustomerRepository;
import com.kenBank.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class LoginController  {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/register") // this won't work since we disable CSRF in config file and add the permit all
    public ResponseEntity<String> userRegistration(@RequestBody Customer customer){
        Customer savedCustomer = null;
        ResponseEntity response = null;
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
            savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getId() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Customer customers = customerRepository.findByEmail(authentication.getName());
        if (!customers.equals(null)) {
            return customers;
        } else {
            return null;
        }

    }

}


/**
 *  Old code
 *  @PostMapping("/register") // this won't work since we disable CSRF in config file and add the permit all
 *     public ResponseEntity<String> userRegistration(@RequestBody Customer customer){
 *         ResponseEntity response=null;
 *          try {
 *              customer.setPwd(encoder.encode(customer.getPwd()));
 *              Customer c1=customerRepository.save(customer);
 *              if (c1.getId()>0){
 *                  response=ResponseEntity.status(HttpStatus.CREATED).body("Registered user Successfully");
 *              }
 *          }catch (Exception e){
 *              response=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
 *                      body("An Exception occured due to: "+e.getMessage());
 *          }
 *
 *           return response;
 *     }
 */
