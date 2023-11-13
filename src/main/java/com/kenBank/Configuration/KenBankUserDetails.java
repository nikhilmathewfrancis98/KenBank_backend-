//package com.kenBank.Configuration;
//
//import com.kenBank.Repository.CustomerRepository;
//import com.kenBank.pojo.Customer;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *  We are doing our own custom logic to fetch the data from our own
// *  custom table for our own custom authentication
// *  So here we are trying to use the help of UserDetailsService interface
// *  And return the UserDetails to the framework for the further procedure
// *  Here The DaoAuthProvider Check all the impl classes of the UserDetailsServices that the user uses
// *  one is this and the other one is the JdbcUserDetailsManager the return type is UserDetailsService
// *  So the spring ioc get confuse and there will be a conflict
// */
//
//@Service
//public class KenBankUserDetails implements UserDetailsService {
//    @Autowired
//    CustomerRepository customerRepository;
//    /**
//     * Locates the user based on the username. In the actual implementation, the search
//     * may possibly be case sensitive, or case insensitive depending on how the
//     * implementation instance is configured. In this case, the <code>UserDetails</code>
//     * object that comes back may have a username that is of a different case than what
//     * was actually requested..
//     *
//     * @param username the username identifying the user whose data is required.
//     * @return a fully populated user record (never <code>null</code>)
//     * @throws UsernameNotFoundException if the user could not be found or the user has no
//     *                                   GrantedAuthority
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String userName,password=null;
//        List<GrantedAuthority> authorities=null;
//        Customer customer=customerRepository.findByEmail(username);
//        if (customer.equals(null))
//            throw new UsernameNotFoundException("User Details not found for the User "+username);
//        else
//           userName=customer.getEmail();
//            password=customer.getPwd();
//            authorities=new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority(customer.getRole()));
//            // Since we have a single role for each user we can keep the GrantedAuthority this way
//       return new User(userName,password,authorities); // we are sending back the details to the framework
//            //  here the password is given to the DAOAuthenticationProvider to check if our psswd and the user entered psswd is same
//    }
//}
//
//// Commenting this out since we are using the authptovider impl