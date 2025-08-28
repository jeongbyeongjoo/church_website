package com.yourchurch.churchwebsite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sermon")
public class SermonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 50)
    private String speaker;

    @CreationTimestamp // Entity가 생성될 때의 시간이 자동으로 저장됩니다.
    @Column(nullable = false, updatable = false)
    private Instant date;

    @Column(length = 100)
    private String series;

    @Column(length = 255)
    private String thumbnail;

    @Column(length = 255)
    private String audioUrl;

    @Column(length = 255)
    private String videoUrl;

    @Column(length = 100)
    private String passage;
}