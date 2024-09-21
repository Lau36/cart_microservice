package com.example.cart_microservice.infrastructure.configuration.feignclient.feignconfig;

import com.example.cart_microservice.infrastructure.configuration.feignclient.FeignConstans;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if(requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(authorization != null && authorization.startsWith(FeignConstans.TOKEN_START_WITH)) {
                requestTemplate.header(HttpHeaders.AUTHORIZATION, authorization);
            }
        }
    }
}
