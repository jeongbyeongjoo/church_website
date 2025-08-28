package com.yourchurch.churchwebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventItemDto {
    private String id;
    private String title;       // 'name' -> 'title'로 변경
    private String date;
    private String startTime;   // 'time' -> 'startTime'으로 변경
    private String location;
    private String description;
}