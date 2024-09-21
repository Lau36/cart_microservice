package com.example.cart_microservice.infrastructure.adapters;

import com.example.cart_microservice.domain.ports.output.IUserId;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@AllArgsConstructor
public class UserIdAdapter implements IUserId {

    public Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getDetails().toString());
    }
}
