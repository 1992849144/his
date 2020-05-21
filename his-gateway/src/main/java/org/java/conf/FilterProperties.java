package org.java.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 白名单属性类，这里面配置的路径不拦截，直接放行
 */
@ConfigurationProperties(prefix = "his.filter")
public class FilterProperties {

    private List<String> allowPaths;  //这里面包含不拦截的路径

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
