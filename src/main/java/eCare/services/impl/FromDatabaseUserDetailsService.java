package eCare.services.impl;

import eCare.model.Role;
import eCare.model.User;
import eCare.services.interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FromDatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserDetailsImpl userDetailsImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            List<User> returnedListOfUsers = userServiceImpl.getUserByLogin(username);
            if(returnedListOfUsers.isEmpty()){
                throw new UsernameNotFoundException(username);
            }else{
            User user = returnedListOfUsers.get(0);
            List<SimpleGrantedAuthority> grantedAuthorities = user.getRoles()
                    .stream().map(authority -> new SimpleGrantedAuthority(authority.getRolename()))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails
                    .User(user.getLogin(), user.getPassword(), grantedAuthorities); // (2)
                }


    }
}
