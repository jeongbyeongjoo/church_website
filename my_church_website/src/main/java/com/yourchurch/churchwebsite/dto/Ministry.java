package com.yourchurch.churchwebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ministry {
    private String id;
    private String name;
    private String leader;
    private String description;
    private String imageUrl;
}
