package com.busManager.busmanager.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Bus {
    private Integer busId;
    private String busName;
    private Integer totalSeats;
}
