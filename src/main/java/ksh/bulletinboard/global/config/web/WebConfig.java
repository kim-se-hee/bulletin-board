package ksh.bulletinboard.global.config.web;

import ksh.bulletinboard.global.login.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login", "/boards", "/post/{postId}/comments", "/member",
                        "/board/{boardId}/post/{postId}", "/board/{boardId}/posts",
                        "/post/{postId}/reply"
                );
    }

}
