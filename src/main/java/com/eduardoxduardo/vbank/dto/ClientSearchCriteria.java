package com.eduardoxduardo.vbank.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientSearchCriteria {
    private Long id;
    private String document;
    private String name;
    private String email;
    private String phone;
}
