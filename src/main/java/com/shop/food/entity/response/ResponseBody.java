package com.shop.food.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResponseBody {
    private String resultMessage; // Mình tiếng anh thôi
    private String resultCode;
    private Object data;
}
