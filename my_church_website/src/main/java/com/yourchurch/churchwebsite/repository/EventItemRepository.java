package com.yourchurch.churchwebsite.repository;

import com.yourchurch.churchwebsite.dto.EventItemDto;
import com.yourchurch.churchwebsite.entity.EventItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventItemRepository extends JpaRepository<EventItemEntity, Long> {
    // JpaRepository<[Entity 클래스], [PK 타입]>을 상속받으면
    // save(), findById(), findAll() 등 기본적인 CRUD 메서드가 자동으로 생성됩니다.
    // 여기에 필요한 추가적인 쿼리 메서드를 선언할 수도 있습니다. (예: findBySpeaker(String speaker))
}
