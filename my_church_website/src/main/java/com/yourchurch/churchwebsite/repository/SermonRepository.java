package com.yourchurch.churchwebsite.repository;

import com.yourchurch.churchwebsite.entity.SermonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 이 인터페이스가 Repository임을 명시
public interface SermonRepository extends JpaRepository<SermonEntity, Long> {
    // JpaRepository<[Entity 클래스], [PK 타입]>을 상속받으면
    // save(), findById(), findAll() 등 기본적인 CRUD 메서드가 자동으로 생성됩니다.
    // 여기에 필요한 추가적인 쿼리 메서드를 선언할 수도 있습니다. (예: findBySpeaker(String speaker))
}