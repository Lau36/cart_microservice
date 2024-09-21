package com.example.cart_microservice.infrastructure.configuration.feignclient.feignconfig;

import com.example.cart_microservice.infrastructure.configuration.feignclient.FeignConstans;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();
    @Override
    public Exception decode(String s, Response response) {
        if (response != null) {
            String message = parseErrorResponse(response);
            return switch (response.status()) {
                case 400 -> new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
                case 404 -> new ResponseStatusException(HttpStatus.NOT_FOUND,  message);
                case 500 ->
                        new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, FeignConstans.ERROR_WITH_MICROSERVICE);
                default -> defaultErrorDecoder.decode(s, response);
            };
        } else {
            return new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, FeignConstans.ERROR_WITH_MICROSERVICE_CONEXION);
        }
    }

        private String parseErrorResponse(Response response) {
            try {
                String body = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> errorResponse = mapper.readValue(body, new TypeReference<>() {
                });
                return (String) errorResponse.get(FeignConstans.MESSAGE);

            } catch (IOException e) {
                return FeignConstans.ERROR;
            }
        }

}
