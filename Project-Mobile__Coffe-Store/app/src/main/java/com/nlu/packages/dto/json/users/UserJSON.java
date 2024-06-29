package com.nlu.packages.dto.json.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nlu.packages.enums.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJSON {
    @JsonProperty("id")
    private long userId;
    @JsonProperty("role")
    private ERole role;
    @JsonProperty("password")
    private String password;
    @JsonProperty("username")
    private String username;
    @JsonProperty("details")
    private UserDetailsJSON details;
    @JsonProperty("addresses")
    private List<AddressJSON> addresses;
}
