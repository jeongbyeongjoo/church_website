package com.yourchurch.churchwebsite.controller;

import com.yourchurch.churchwebsite.dto.EventItemDto;
import com.yourchurch.churchwebsite.dto.MinistryDto;
import com.yourchurch.churchwebsite.dto.SermonDto;
import com.yourchurch.churchwebsite.service.EventItemService;
import com.yourchurch.churchwebsite.service.MinistryService;
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

    // 3개의 Service를 모두 주입받습니다.
    private final SermonService sermonService;
    private final EventItemService eventItemService;
    private final MinistryService ministryService;

    // --- 설교 API ---
    @GetMapping("/sermons")
    public ResponseEntity<List<SermonDto>> getAllSermons() {
        return ResponseEntity.ok(sermonService.getAllSermons());
    }

    // --- 행사 API ---
    @GetMapping("/events")
    public ResponseEntity<List<EventItemDto>> getAllEvents() {
        // 임시 데이터 대신 실제 Service를 호출합니다.
        return ResponseEntity.ok(eventItemService.getAllEventItems());
    }

    // --- 사역 API ---
    @GetMapping("/ministries")
    public ResponseEntity<List<MinistryDto>> getAllMinistries() {
        // 임시 데이터 대신 실제 Service를 호출합니다.
        return ResponseEntity.ok(ministryService.getAllMinistries());
    }

    @PostMapping("/contact")
    public String handleContactForm() {
        // 연락처 양식 데이터를 처리하는 로직을 구현합니다.
        // 예를 들어 이메일 전송이나 DB 저장
        return "Contact form submitted successfully!";
    }
}