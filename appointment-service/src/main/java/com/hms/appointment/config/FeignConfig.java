package com.hms.appointment.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

//FeignConfig.java — forward auth context headers to downstream services
@Configuration
public class FeignConfig {
@Bean
public RequestInterceptor headerForwarder() {
 return template -> {
   RequestAttributes ra = RequestContextHolder.getRequestAttributes();
   if (ra instanceof ServletRequestAttributes sra) {
     HttpServletRequest req = sra.getRequest();
     // propagate gateway-injected identity
     var email = req.getHeader("X-Auth-User-Email");
     var role  = req.getHeader("X-Auth-User-Role");
     if (email != null) template.header("X-Auth-User-Email", email);
     if (role  != null) template.header("X-Auth-User-Role", role);
     // also forward Authorization if present (optional)
     var auth = req.getHeader("Authorization");
     if (auth != null) template.header("Authorization", auth);
   }
 };
}
}
