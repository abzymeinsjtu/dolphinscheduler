package org.apache.dolphinscheduler.api.security;

import org.apache.dolphinscheduler.api.exceptions.ServiceException;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
public class AuthenticationService {
    private static AuthenticationService authenticationService;


    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    void init() {
        authenticationService = this;
    }

    public static AuthenticationService getInstance() {
        return authenticationService;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("token");

        if (token == null) throw new ServiceException("token not exists");

        User user = userMapper.queryUserByToken(token, new Date());

        if (user == null) throw new ServiceException("user not found with token: " + token);

        return new ApiTokenAuthentication(user, AuthorityUtils.NO_AUTHORITIES);
    }
}
