package com.yourchurch.churchwebsite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event_item") // 테이블 이름 지정
public class EventItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성 (auto-increment)
    private Long id; // 보통 PK는 Long 타입을 많이 사용합니다.

    @Column(nullable = false, length = 100) // NOT NULL, 길이 100
    private String name;

    @Column(nullable = false, length = 20)
    private String date;

    @Column(length = 20)
    private String time;

    @Column(length = 255)
    private String location;

    @Column(columnDefinition = "TEXT") // 긴 텍스트를 위한 설정
    private String description;
}