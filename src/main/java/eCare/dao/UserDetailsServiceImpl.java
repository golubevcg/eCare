package eCare.dao;

import eCare.model.User;
import eCare.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userServiceImpl.getUserByLogin(login).get(0);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if(user!=null){
            builder = org.springframework.security.core.userdetails.User.withUsername(login);
            builder.disabled(!user.isActive());
            builder.password(user.getPassword());
            String[] authorities  = user.getRoles().stream().map(r->r.getRolename()).toArray(String[]::new);
            builder.authorities(authorities);
        }else{
            throw new UsernameNotFoundException(login + " user wasn't found");
        }

        return builder.build();
    }
}
