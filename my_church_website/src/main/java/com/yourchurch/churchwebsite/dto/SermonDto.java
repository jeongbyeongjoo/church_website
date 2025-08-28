package com.yourchurch.churchwebsite.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Getter, @Setter, @ToString 등 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 자동 생성
public class SermonDto {
    private String id;
    private String title;
    private String speaker;
    private Instant date;
    private String series;
    private String thumbnail;
    private String audioUrl;
    private String videoUrl;
    private String passage;
}
