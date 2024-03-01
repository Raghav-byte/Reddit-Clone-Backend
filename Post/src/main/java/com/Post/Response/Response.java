package com.Post.Response;

import lombok.Data;

@Data
public class Response {

    private Object data;

    public Response(Object data) {
        this.data = data;
    }
}
