package com.kamikakushipage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User implements Serializable{
    private Integer id;
    private String email;
    private String password;
    private String salt;
    private String confirmCode;
    private LocalDateTime activation;
    private Byte isValid;




}
