package eCare.security;

import eCare.model.User;
import eCare.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userServiceImpl.getUserByLogin(login).get(0);
        if(user!=null){
            return SecurityUser.fromUser(user);
        }else{
            throw new UsernameNotFoundException(login + " user wasn't found");
        }

    }
}
