package org.sergey.idf_bank_microservice.utils;

import org.springframework.web.context.request.WebRequest;

import java.net.URI;

public class WebRequestUtils {

    public static URI extractUri(WebRequest request) {
        return URI.create(request.getDescription(false).substring(4));
    }

}
