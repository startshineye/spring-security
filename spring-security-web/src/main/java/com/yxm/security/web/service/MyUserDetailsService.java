package com.yxm.security.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author yexinming
 * @date 2020/2/22
 **/
@Component
public class MyUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //返回一个实例对象,我们使用spring-security自带的User
        logger.info("登录用户名:"+username);
        //根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结:我们用7个参数的构造方法
       // new User(username,"123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"))
        //创建一个用户被锁定账号
        UserDetails user = new User(username,passwordEncoder.encode("123456"), true,true,
                true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user;
    }
}
