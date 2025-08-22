package com.eduardoxduardo.vbank.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSearchCriteria {
    Long id;
    String accountNumber;
    Long clientId;
}
