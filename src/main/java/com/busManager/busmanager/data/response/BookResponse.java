package com.busManager.busmanager.data.response;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BookResponse {
    HttpStatus httpStatus;
}
