package com.busManager.busmanager.data.response;

import com.busManager.busmanager.data.dto.BusSearchResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SearchResponse {
    List<BusSearchResponse> busResponseList;
}
