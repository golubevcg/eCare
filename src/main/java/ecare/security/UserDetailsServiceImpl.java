package ecare.security;

import ecare.model.entity.User;
import ecare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);

    final
    UserService userServiceImpl;

    public UserDetailsServiceImpl(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

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
