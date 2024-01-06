package com.busManager.busmanager.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusDTO {
    private Integer busId;
    private String busName;
    private Integer totalSeats;
    private List<String> daysOfOperation;
}
