package com.yourchurch.churchwebsite.service;

import com.yourchurch.churchwebsite.dto.EventItemDto;
import com.yourchurch.churchwebsite.entity.EventItemEntity;
import com.yourchurch.churchwebsite.repository.EventItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventItemService {

    private final EventItemRepository eventItemRepository;

    // 모든 교회 행사를 조회하는 로직
    @Transactional(readOnly = true)
    public List<EventItemDto> getAllEventItems() {
        return eventItemRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 새로운 교회 행사를 생성하는 로직
    @Transactional
    public EventItemDto createEventItem(EventItemDto eventItemDto) {
        EventItemEntity eventItemEntity = convertToEntity(eventItemDto);
        EventItemEntity savedEventItem = eventItemRepository.save(eventItemEntity);
        return convertToDto(savedEventItem);
    }

    private EventItemDto convertToDto(EventItemEntity entity) {
        return new EventItemDto(
                String.valueOf(entity.getId()),
                entity.getName(), // DB 필드는 그대로 name
                entity.getDate(),
                entity.getTime(), // DB 필드는 그대로 time
                entity.getLocation(),
                entity.getDescription()
        );
    }

    private EventItemEntity convertToEntity(EventItemDto dto) {
        return EventItemEntity.builder()
                .name(dto.getTitle()) // DTO의 title을 Entity의 name으로
                .date(dto.getDate())
                .time(dto.getStartTime()) // DTO의 startTime을 Entity의 time으로
                .location(dto.getLocation())
                .description(dto.getDescription())
                .build();
    }
}