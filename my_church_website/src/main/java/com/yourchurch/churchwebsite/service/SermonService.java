package com.yourchurch.churchwebsite.service;

import com.yourchurch.churchwebsite.dto.SermonDto;
import com.yourchurch.churchwebsite.entity.SermonEntity;
import com.yourchurch.churchwebsite.repository.SermonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SermonService {

    // final 키워드를 사용하여 sermonRepository가 변경되지 않도록 보장합니다.
    // @RequiredArgsConstructor가 이 필드를 위한 생성자를 만들어주고,
    // 스프링이 실행될 때 해당 생성자를 통해 SermonRepository의 구현체(객체)를 자동으로 주입(DI)해 줍니다.
    private final SermonRepository sermonRepository;

    /**
     * 모든 설교 목록을 조회하는 메서드입니다.
     *
     * @Transactional(readOnly = true)
     * - 이 메서드가 실행되는 동안 데이터베이스 연결을 유지하며, 하나의 트랜잭션으로 묶입니다.
     * - readOnly = true 옵션은 이 트랜잭션이 데이터 변경 없이 '읽기 전용'임을 명시합니다.
     * - 데이터베이스에 불필요한 부하를 줄여 성능을 최적화하는 효과가 있습니다.
     *
     * @return DB에서 조회된 모든 설교 목록을 SermonDto 리스트 형태로 반환합니다.
     */
    @Transactional(readOnly = true)
    public List<SermonDto> getAllSermons() { /** 모든 설교 조회 */
        // 1. sermonRepository를 통해 데이터베이스에 있는 모든 SermonEntity를 조회합니다.
        //    findAll()은 JpaRepository에 기본적으로 구현되어 있는 메서드입니다.
        //    반환값은 List<SermonEntity> 형태입니다.
        return sermonRepository.findAll()
                .stream() // 2. 조회된 List<SermonEntity>를 스트림(Stream)으로 변환합니다. 스트림은 데이터의 흐름으로, 데이터를 가공하고 처리하는 데 유용합니다.
                .map(this::convertToDto) // 3. 스트림의 각 SermonEntity에 대해 convertToDto 메서드를 적용(map)하여 SermonDto로 변환합니다.
                .collect(Collectors.toList()); // 4. 변환된 SermonDto들를 모아서(collect) 다시 List 형태로 만듭니다.
    }

    /**
     * 새로운 설교를 데이터베이스에 저장하는 메서드입니다.
     *
     * @Transactional
     * - 이 어노테이션이 붙은 메서드 내의 모든 데이터베이스 작업은 하나의 트랜잭션으로 처리됩니다.
     * - 중간에 예외(에러)가 발생하면, 이전에 수행했던 모든 DB 작업이 취소(Rollback)되어 데이터 일관성을 보장합니다.
     * - (readOnly = true 옵션이 없으므로 데이터 변경이 가능한 트랜잭션입니다.)
     *
     * @param sermonDto Controller로부터 전달받은 클라이언트의 설교 데이터 (DTO)
     * @return 저장된 설교 정보를 담은 새로운 SermonDto 객체
     */
    @Transactional
    public SermonDto createSermon(SermonDto sermonDto) { /** 새 설교를 데이터베이스에 저장 */
        // 1. 클라이언트로부터 받은 SermonDto를 데이터베이스에 저장할 수 있는 SermonEntity 형태로 변환합니다.
        SermonEntity sermonEntity = convertToEntity(sermonDto);

        // 2. sermonRepository의 save() 메서드를 사용하여 변환된 Entity를 데이터베이스에 저장합니다.
        //    save() 메서드는 저장된 Entity(ID가 생성된 상태)를 반환합니다.
        SermonEntity savedSermon = sermonRepository.save(sermonEntity);

        // 3. 데이터베이스에 성공적으로 저장된 Entity를 다시 클라이언트에게 보여줄 DTO 형태로 변환하여 반환합니다.
        return convertToDto(savedSermon);
    }

    private SermonDto convertToDto(SermonEntity entity) { /** entity를 dto로 변환 */
        // DTO의 생성자를 사용하여 Entity의 각 필드 값을 DTO의 필드에 복사합니다.
        return new SermonDto(
                String.valueOf(entity.getId()), // Long 타입의 ID를 String으로 변환
                entity.getTitle(),
                entity.getSpeaker(),
                entity.getDate(),
                entity.getSeries(),
                entity.getThumbnail(),
                entity.getAudioUrl(),
                entity.getVideoUrl(),
                entity.getPassage()
        );
    }

    private SermonEntity convertToEntity(SermonDto dto) { /** dto를 entity로 변환 */
        // Entity의 @Builder를 사용하여 DTO의 값을 Entity 객체로 안전하고 명확하게 만듭니다.
        return SermonEntity.builder()
                .title(dto.getTitle())
                .speaker(dto.getSpeaker())
                .series(dto.getSeries())
                .thumbnail(dto.getThumbnail())
                .audioUrl(dto.getAudioUrl())
                .videoUrl(dto.getVideoUrl())
                .passage(dto.getPassage())
                .build(); // 마지막에 build()를 호출하여 객체 생성을 완료합니다.
        // 참고: dto의 id와 date는 Entity로 변환할 때 포함하지 않습니다.
        //      - Entity의 id는 DB에 저장될 때 자동으로 생성됩니다. (auto-increment)
        //      - Entity의 date는 @CreationTimestamp에 의해 자동으로 현재 시간이 기록됩니다.
    }
}