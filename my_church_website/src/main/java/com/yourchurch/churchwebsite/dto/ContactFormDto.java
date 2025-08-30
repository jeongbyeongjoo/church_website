package com.yourchurch.churchwebsite.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Getter, @Setter 등을 자동으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
public class ContactFormDto {
    private String name;
    private String email;
    private String message;
}