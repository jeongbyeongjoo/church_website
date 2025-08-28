package com.yourchurch.churchwebsite.service;

import com.yourchurch.churchwebsite.dto.MinistryDto;
import com.yourchurch.churchwebsite.entity.MinistryEntity;
import com.yourchurch.churchwebsite.repository.MinistryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MinistryService {

    private final MinistryRepository ministryRepository;

    // 모든 사역 정보를 조회하는 로직
    @Transactional(readOnly = true)
    public List<MinistryDto> getAllMinistries() {
        return ministryRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 새로운 사역 정보를 생성하는 로직
    @Transactional
    public MinistryDto createMinistry(MinistryDto ministryDto) {
        MinistryEntity ministryEntity = convertToEntity(ministryDto);
        MinistryEntity savedMinistry = ministryRepository.save(ministryEntity);
        return convertToDto(savedMinistry);
    }

    private MinistryDto convertToDto(MinistryEntity entity) {
        return new MinistryDto(
                String.valueOf(entity.getId()),
                entity.getName(),
                entity.getLeader(),
                entity.getDescription(), // DB 필드는 그대로 description
                entity.getImageUrl()
        );
    }

    private MinistryEntity convertToEntity(MinistryDto dto) {
        return MinistryEntity.builder()
                .name(dto.getName())
                .leader(dto.getLeader())
                .description(dto.getSummary()) // DTO의 summary를 Entity의 description으로
                .imageUrl(dto.getImageUrl())
                .build();
    }
}