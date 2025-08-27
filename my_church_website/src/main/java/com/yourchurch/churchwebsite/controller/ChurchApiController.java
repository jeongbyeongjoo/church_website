package com.yourchurch.churchwebsite.controller;

import com.yourchurch.churchwebsite.dto.EventItem;
import com.yourchurch.churchwebsite.dto.Ministry;
import com.yourchurch.churchwebsite.dto.Sermon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.time.Instant;

@RestController
public class ChurchApiController {

    @GetMapping("/api/sermons")
    public List<Sermon> getSermons() {
        // 실제 데이터베이스에서 데이터를 가져오는 로직을 여기에 구현해야 합니다.
        // 지금은 테스트를 위해 더미 데이터를 반환할 수 있습니다.
        return List.of(
                new Sermon("s1", "Hope in Every Season", "Pastor Kim", Instant.now(), "Philippians", null, "#", null, "Philippians 4:4-9"),
                new Sermon("s2", "Walking in Grace", "Elder Park", Instant.now().minusSeconds(86400 * 7), "Galatians", null, "#", null, "Galatians 5:1-12")
        );
    }

    @GetMapping("/api/events")
    public List<EventItem> getEvents() {
        return List.of(
                new EventItem("e1", "Youth Gathering", "August 26", "7:00 PM", "Youth Chapel", "A night of worship and fellowship for all youth."),
                new EventItem("e2", "Young Adult Service", "September 2", "8:00 PM", "Main Sanctuary", "A special service for young adults.")
        );
    }

    @GetMapping("/api/ministries")
    public List<Ministry> getMinistries() {
        return List.of(
                new Ministry("m1", "Youth Ministry", "Pastor Alex", "Equipping the next generation to be disciples of Christ.", "https://example.com/youth-ministry.jpg"),
                new Ministry("m2", "Worship Team", "Jane Doe", "Leading the congregation in worship through music.", "https://example.com/worship-team.jpg")
        );
    }

    @PostMapping("/api/contact")
    public String handleContactForm() {
        // 연락처 양식 데이터를 처리하는 로직을 구현합니다.
        // 예를 들어 이메일 전송이나 DB 저장
        return "Contact form submitted successfully!";
    }
}