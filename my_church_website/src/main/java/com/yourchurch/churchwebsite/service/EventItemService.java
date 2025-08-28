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

    // Entity -> DTO 변환 (EventItem에 맞게 수정)
    private EventItemDto convertToDto(EventItemEntity entity) {
        return new EventItemDto(
                String.valueOf(entity.getId()),
                entity.getName(),
                entity.getDate(),
                entity.getTime(),
                entity.getLocation(),
                entity.getDescription()
        );
    }

    // DTO -> Entity 변환 (EventItem에 맞게 수정)
    private EventItemEntity convertToEntity(EventItemDto dto) {
        return EventItemEntity.builder()
                .name(dto.getName())
                .date(dto.getDate())
                .time(dto.getTime())
                .location(dto.getLocation())
                .description(dto.getDescription())
                .build();
    }
}