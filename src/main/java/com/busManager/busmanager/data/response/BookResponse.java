package com.busManager.busmanager.data.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class BookResponse {
    Boolean bookingDone;
}
