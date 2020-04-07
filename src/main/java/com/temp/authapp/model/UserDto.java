package com.temp.authapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String userName;
    private String password;
    private String roles;
    private boolean active;
}
