package org.apache.dolphinscheduler.api.security;

import org.apache.dolphinscheduler.dao.entity.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ApiTokenAuthentication extends AbstractAuthenticationToken {
    private final User user;

    public ApiTokenAuthentication(User user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    public Integer getUserId() {
        return user.getId();
    }
}
