package com.userservice.Dtos;

import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class JwtData {
    private String email;
    private List<String> roles;
}
