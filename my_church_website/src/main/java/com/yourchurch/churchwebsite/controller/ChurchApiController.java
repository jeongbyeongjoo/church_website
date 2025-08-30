package com.yourchurch.churchwebsite.controller;

import com.yourchurch.churchwebsite.dto.ContactFormDto;
import com.yourchurch.churchwebsite.dto.EventItemDto;
import com.yourchurch.churchwebsite.dto.MinistryDto;
import com.yourchurch.churchwebsite.dto.SermonDto;
import com.yourchurch.churchwebsite.service.EventItemService;
import com.yourchurch.churchwebsite.service.MinistryService;
import com.yourchurch.churchwebsite.service.SermonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RestController
 * 이 클래스가 RESTful 웹 서비스의 컨트롤러임을 나타냅니다.
 * 이 어노테이션이 붙은 클래스의 모든 메서드는 기본적으로 뷰(HTML 페이지)를 반환하는 대신,
 * JSON이나 XML 같은 순수한 데이터(객체)를 반환(Response Body에 작성)하게 됩니다.
 */
@RestController
/**
 * @RequestMapping("/api")
 * 이 클래스 내의 모든 API 메서드들의 공통 URL 경로를 "/api"로 지정합니다.
 * 예를 들어, @GetMapping("/sermons")는 최종적으로 "http://localhost:8080/api/sermons" 주소가 됩니다.
 * 이렇게 공통 경로를 지정하면 코드의 중복을 줄이고 관리가 편해집니다.
 */
@RequestMapping("/api")
/**
 * @RequiredArgsConstructor
 * Lombok 어노테이션으로, 'final' 키워드가 붙은 모든 필드를 포함하는 생성자를 자동으로 만들어줍니다.
 * 스프링의 의존성 주입(DI) 중 '생성자 주입'을 가장 깔끔하게 구현하는 방법입니다.
 */
@RequiredArgsConstructor
public class ChurchApiController {

    // final 키워드를 통해 이 컨트롤러는 반드시 3개의 Service 객체를 필요로 함을 명시합니다.
    // @RequiredArgsConstructor가 이 필드들을 위한 생성자를 만들고,
    // 스프링이 실행되면서 해당 생성자를 통해 각 Service의 실제 객체(Bean)를 자동으로 주입해줍니다.
    private final SermonService sermonService;
    private final EventItemService eventItemService;
    private final MinistryService ministryService;

    // --- 설교 API ---
    /**
     * @GetMapping("/sermons")
     * HTTP GET 요청 중에서 "/api/sermons" 경로로 들어오는 요청을 이 메서드가 처리하도록 지정합니다.
     * 클라이언트(프론트엔드)가 이 주소로 데이터를 요청하면 아래 메서드가 실행됩니다.
     */
    @GetMapping("/sermons")
    public ResponseEntity<List<SermonDto>> getAllSermons() {
        // 1. sermonService에게 모든 설교 데이터를 가져오라고 요청(위임)합니다.
        List<SermonDto> sermons = sermonService.getAllSermons();
        // 2. ResponseEntity.ok()는 HTTP 상태 코드 200 (OK, 성공)을 의미합니다.
        //    성공 상태와 함께, 조회된 설교 목록(sermons)을 body에 담아 클라이언트에게 응답합니다.
        //    이 List<SermonDto> 객체는 스프링에 의해 자동으로 JSON 배열 형태로 변환되어 전송됩니다.
        return ResponseEntity.ok(sermons);
    }

    // --- 행사 API ---
    /**
     * @GetMapping("/events")
     * HTTP GET 요청 중에서 "/api/events" 경로로 들어오는 요청을 처리합니다.
     */
    @GetMapping("/events")
    public ResponseEntity<List<EventItemDto>> getAllEvents() {
        // 1. eventItemService에게 모든 행사 데이터를 가져오라고 요청합니다.
        List<EventItemDto> events = eventItemService.getAllEventItems();
        // 2. 성공(200 OK) 응답과 함께 행사 목록 데이터를 JSON 형태로 반환합니다.
        return ResponseEntity.ok(events);
    }

    // --- 사역 API ---
    /**
     * @GetMapping("/ministries")
     * HTTP GET 요청 중에서 "/api/ministries" 경로로 들어오는 요청을 처리합니다.
     */
    @GetMapping("/ministries")
    public ResponseEntity<List<MinistryDto>> getAllMinistries() {
        // 1. ministryService에게 모든 사역 데이터를 가져오라고 요청합니다.
        List<MinistryDto> ministries = ministryService.getAllMinistries();
        // 2. 성공(200 OK) 응답과 함께 사역 목록 데이터를 JSON 형태로 반환합니다.
        return ResponseEntity.ok(ministries);
    }

    /**
     * @PostMapping("/contact")
     * 연락처 폼 제출(POST 요청)을 처리하는 API 엔드포인트입니다.
     *
     * @RequestBody ContactFormDto contactFormDto
     * - @RequestBody: 프론트엔드가 요청 본문(body)에 담아 보낸 JSON 데이터를
     * - ContactFormDto: 우리가 만든 자바 객체로 자동으로 변환해서 넣어줘! 라는 의미의 마법 어노테이션입니다.
     */
    @PostMapping("/contact")
    public ResponseEntity<String> handleContactForm(@RequestBody ContactFormDto contactFormDto) {
        // 이제 contactFormDto 객체 안에 프론트엔드가 보낸 데이터가 예쁘게 담겨 있습니다.

        // 데이터가 잘 도착했는지 백엔드 콘솔(터미널)에 출력해봅니다.
        System.out.println("===== 새로운 문의 접수 =====");
        System.out.println("이름: " + contactFormDto.getName());
        System.out.println("이메일: " + contactFormDto.getEmail());
        System.out.println("메시지: " + contactFormDto.getMessage());
        System.out.println("==========================");

        // 나중에는 여기에 이메일을 보내거나, DB에 문의 내용을 저장하는 로직을 추가할 수 있습니다.

        // 프론트엔드에게 성공적으로 처리되었다는 응답(200 OK)과 메시지를 보냅니다.
        return ResponseEntity.ok("Contact form submitted successfully!");
    }
}