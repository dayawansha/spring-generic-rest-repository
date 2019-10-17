package com.genericCrud.springgenericrestrepository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {

    private String message;
    private boolean status;
    private Integer statusCode;
    private String user;
    private Date timestamp;
    private String id;

}
