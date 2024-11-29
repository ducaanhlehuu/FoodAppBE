package com.shop.food.entity.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResponseBody {
    private String resultMessage;
    private String resultCode;
    private Object data;

    @JsonIgnore public static final String SUCCESS = "SUCCESS";
    @JsonIgnore public static final String NOT_FOUND = "NOT_FOUND";
    @JsonIgnore public static final String BAD_REQUEST = "BAD_REQUEST";
    @JsonIgnore public static final String FAILED = "FAILED";
    @JsonIgnore public static final String UNAUTHORIZED = "UNAUTHORIZED";
    @JsonIgnore public static final String FORBIDDEN = "FORBIDDEN";
    @JsonIgnore public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    @JsonIgnore public static final String SERVER_ERROR = "SERVER_ERROR";
    @JsonIgnore public static final String CREATED = "CREATED";
    @JsonIgnore public static final String UPDATED = "UPDATED";
    @JsonIgnore public static final String DELETED = "DELETED";
    @JsonIgnore public static final String CONFLICT = "CONFLICT";
}
