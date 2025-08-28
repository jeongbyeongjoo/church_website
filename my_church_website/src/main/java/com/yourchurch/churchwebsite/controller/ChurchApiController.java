package com.yourchurch.churchwebsite.controller;

import com.yourchurch.churchwebsite.dto.EventItemDto;
import com.yourchurch.churchwebsite.dto.MinistryDto;
import com.yourchurch.churchwebsite.dto.SermonDto;
import com.yourchurch.churchwebsite.service.SermonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChurchApiController {

    private final SermonService sermonService;

    @GetMapping("/sermons")
    public ResponseEntity<List<SermonDto>> getAllSermons() {
        List<SermonDto> sermons = sermonService.getAllSermons();
        return ResponseEntity.ok(sermons);
    }


    @GetMapping("/events")
    public List<EventItemDto> getEvents() {
        return List.of(
                new EventItemDto("e1", "Youth Gathering", "August 26", "7:00 PM", "Youth Chapel", "A night of worship and fellowship for all youth."),
                new EventItemDto("e2", "Young Adult Service", "September 2", "8:00 PM", "Main Sanctuary", "A special service for young adults.")
        );
    }

    @GetMapping("/ministries")
    public List<MinistryDto> getMinistries() {
        return List.of(
                new MinistryDto("m1", "Youth Ministry", "Pastor Alex", "Equipping the next generation to be disciples of Christ.", "https://example.com/youth-ministry.jpg"),
                new MinistryDto("m2", "Worship Team", "Jane Doe", "Leading the congregation in worship through music.", "https://example.com/worship-team.jpg")
        );
    }

    @PostMapping("/contact")
    public String handleContactForm() {
        // 연락처 양식 데이터를 처리하는 로직을 구현합니다.
        // 예를 들어 이메일 전송이나 DB 저장
        return "Contact form submitted successfully!";
    }
}