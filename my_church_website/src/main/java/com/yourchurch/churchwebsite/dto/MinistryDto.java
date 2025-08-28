package com.yourchurch.churchwebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinistryDto {
    private String id;
    private String name;
    private String leader;
    private String description;
    private String imageUrl;
}
