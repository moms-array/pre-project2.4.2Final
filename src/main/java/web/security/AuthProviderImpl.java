package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.repository.UserRepository;

import java.util.*;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("user not found");
        }
        String password = authentication.getCredentials().toString();
        if (!password.equals(user.getPassword())){
            throw new BadCredentialsException("bad credentials");
        }
        List<GrantedAuthority> authority = new ArrayList<>(user.getRoles());
        return new UsernamePasswordAuthenticationToken(user,null,authority);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
