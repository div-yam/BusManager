package com.busManager.busmanager.data.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SearchResponse {
    List<BusResponse> busResponseList;
}
