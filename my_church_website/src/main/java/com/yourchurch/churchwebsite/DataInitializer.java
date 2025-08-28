package com.yourchurch.churchwebsite;

import com.yourchurch.churchwebsite.dto.EventItemDto;
import com.yourchurch.churchwebsite.dto.MinistryDto;
import com.yourchurch.churchwebsite.dto.SermonDto;
import com.yourchurch.churchwebsite.service.EventItemService;
import com.yourchurch.churchwebsite.service.MinistryService;
import com.yourchurch.churchwebsite.service.SermonService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // 이 클래스도 스프링이 관리하는 컴포넌트로 등록합니다.
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    // 데이터를 생성하기 위해 3개의 Service를 모두 주입받습니다.
    private final SermonService sermonService;
    private final EventItemService eventItemService;
    private final MinistryService ministryService;

    /**
     * Spring Boot 애플리케이션이 시작될 때 이 run 메서드가 자동으로 실행됩니다.
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("===== 데이터 초기화 시작 =====");

        // 샘플 설교 데이터 생성
        sermonService.createSermon(new SermonDto(null, "은혜의 강가로", "김목사", null, "은혜 시리즈", "https://images.unsplash.com/photo-1496307042754-b4aa456c4a2d?q=80&w=2070&auto=format&fit=crop", null, "#", "요한복음 3:16"));
        sermonService.createSermon(new SermonDto(null, "믿음의 반석 위에", "박장로", null, "믿음 시리즈", "https://images.unsplash.com/photo-1504051771394-dd2e66b2e08f?q=80&w=2069&auto=format&fit=crop", "#", null, "마태복음 7:24"));

        // 샘플 행사 데이터 생성
        eventItemService.createEventItem(new EventItemDto(null, "청년부 수련회", "8월 26일", "오후 7:00", "청년부실", "청년들을 위한 예배와 교제의 밤."));
        eventItemService.createEventItem(new EventItemDto(null, "장년부 성경공부", "9월 2일", "오후 8:00", "본당", "장년 성도들을 위한 특별 성경공부."));

        // 샘플 사역 데이터 생성
        ministryService.createMinistry(new MinistryDto(null, "청년 사역", "알렉스 목사", "다음 세대를 그리스도의 제자로 세웁니다.", "https://example.com/youth-ministry.jpg"));
        ministryService.createMinistry(new MinistryDto(null, "찬양팀", "제인 도", "음악을 통해 성도들을 예배로 인도합니다.", "https://example.com/worship-team.jpg"));

        System.out.println("===== 데이터 초기화 완료 =====");
    }
}