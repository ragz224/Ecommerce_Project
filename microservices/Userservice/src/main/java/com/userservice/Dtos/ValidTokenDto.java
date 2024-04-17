package com.userservice.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidTokenDto {
    private String token;
    private Long id;
}
