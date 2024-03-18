package org.apache.dolphinscheduler.api.security.v3;

import org.apache.dolphinscheduler.dao.entity.User;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

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
