package com.busManager.busmanager.data.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SearchRequest {
    private String source;
    private String destination;

    private Date date;

}
