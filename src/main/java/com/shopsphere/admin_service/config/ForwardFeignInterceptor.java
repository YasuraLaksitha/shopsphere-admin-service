package com.shopsphere.admin_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
public class ForwardFeignInterceptor implements RequestInterceptor {

    public static final String USER_ID_HEADER = "X-User-Id";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            final String userId = attributes.getRequest().getHeader(USER_ID_HEADER);
            log.info("{} header found in ForwardFeignInterceptor: {}", USER_ID_HEADER, userId);

            if (StringUtils.hasText(userId))
                requestTemplate.header(USER_ID_HEADER, userId);
        }
    }
}
