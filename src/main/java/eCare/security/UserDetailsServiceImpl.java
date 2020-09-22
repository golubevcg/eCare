package eCare.security;

import eCare.config.SpringSecurityConfig;
import eCare.model.enitity.User;
import eCare.services.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userServiceImpl.getUserByLogin(login).get(0);
        if(user!=null){
            log.info("User for authorisation successfully loaded. ");
            return SecurityUser.fromUser(user);
        }else{
            throw new UsernameNotFoundException(login + " user wasn't found");
        }

    }
}
