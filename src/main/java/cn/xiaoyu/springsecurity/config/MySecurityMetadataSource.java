package cn.xiaoyu.springsecurity.config;

import cn.xiaoyu.springsecurity.domain.MyResourceBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 自定义的元数据源类，用来提供鉴权过程中，访问资源所需的角色
 */
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static final List<MyResourceBean> resources = initResource();

    /**
     * 本方法返回访问资源所需的角色集合
     */
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 从object中得到需要访问的资源，即网址
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        // 从数据库中得到所有资源，以及对应的角色
        List<MyResourceBean> resourceBeans = selectAllResource();
        for (MyResourceBean resource : resourceBeans) {
            // 首先进行地址匹配
            if (antPathMatcher.match(resource.getUrl(), requestUrl)
                    && resource.getRolesArray().length > 0) {
                return SecurityConfig.createList(resource.getRolesArray());
            }
        }
        // 匹配不成功返回一个特殊的ROLE_NONE
        return SecurityConfig.createList("ROLE_NONE");
    }

    private List<MyResourceBean> selectAllResource() {
        return resources;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    /**
     * 初始化资源文件
     */
    private static List<MyResourceBean> initResource() {
        List<MyResourceBean> resources = new ArrayList<>();
        resources.add(new MyResourceBean(1L, "/depart1/**", "ROLE_ADMIN,ROLE_MANAGER,ROLE_DEPART1"));
        resources.add(new MyResourceBean(2L, "/depart2/**", "ROLE_ADMIN,ROLE_MANAGER,ROLE_DEPART2"));
        resources.add(new MyResourceBean(3L, "/user/**", "ROLE_ADMIN,ROLE_USER"));
        resources.add(new MyResourceBean(4L, "/admin/**", "ROLE_ADMIN"));
        return resources;
    }
}
