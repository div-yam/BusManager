package com.busManager.busmanager.data.request;

import com.busManager.busmanager.data.WeekDays;
        import lombok.Getter;
        import lombok.Setter;

        import java.sql.Time;
        import java.util.List;

@Getter
@Setter
public class AddBusRequest {
    String busName;
    Integer totalSeats;
    String source;
    String destination;
    Time departureTime;
    Integer distance;
    List<WeekDays> weekDays;
}
