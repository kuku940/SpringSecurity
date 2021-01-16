package cn.xiaoyu.springsecurity.service;

import cn.xiaoyu.springsecurity.domain.MyUserBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetailsService的实现类，用于在程序中引入一个自定义的AuthenticationProvider，实现数据库访问模式的验证
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    /**
     * 只是单纯的从数据库查询值，这儿直接用内存值来替换数据查询操作
     */
    private static final List<MyUserBean> users = initUser();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserBean userBean = selectByUsername(username);
        if (userBean == null) {
            throw new UsernameNotFoundException("数据库中无此用户！");
        }
        return userBean;
    }

    private MyUserBean selectByUsername(String username) {
        return users.stream().filter(user -> username.equals(user.getUsername())).findFirst().orElse(null);
    }

    /**
     * 初始化用户
     */
    private static List<MyUserBean> initUser() {
        List<MyUserBean> list = new ArrayList<>();
        // 两个密码都是pwd
        list.add(new MyUserBean(1L, "Adam", "beijing", "adam", "$2a$10$9SIFu8l8asZUKxtwqrJM5ujhWarz/PMnTX44wXNsBHfpJMakWw3M6", "ROLE_USER"));
        list.add(new MyUserBean(2L, "SuperMan", "shanghang", "super", "$2a$10$9SIFu8l8asZUKxtwqrJM5ujhWarz/PMnTX44wXNsBHfpJMakWw3M6", "ROLE_USER,ROLE_ADMIN"));

        list.add(new MyUserBean(3L, "Manager", "beijing", "manager", "$2a$10$9SIFu8l8asZUKxtwqrJM5ujhWarz/PMnTX44wXNsBHfpJMakWw3M6", "ROLE_USER,ROLE_MANAGER"));
        list.add(new MyUserBean(4L, "User1", "shanghang", "user1", "$2a$10$9SIFu8l8asZUKxtwqrJM5ujhWarz/PMnTX44wXNsBHfpJMakWw3M6", "ROLE_USER,ROLE_DEPART1"));
        list.add(new MyUserBean(5L, "User2", "shanghang", "user2", "$2a$10$9SIFu8l8asZUKxtwqrJM5ujhWarz/PMnTX44wXNsBHfpJMakWw3M6", "ROLE_USER,ROLE_DEPART2"));
        return list;
    }
}
