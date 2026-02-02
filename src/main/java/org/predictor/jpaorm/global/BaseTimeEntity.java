package org.predictor.jpaorm.global;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * [공통] 생성 및 수정 시간을 자동으로 관리하는 추상 클래스
 */
@Getter
@MappedSuperclass // 1. JPA 엔티티들이 이 클래스를 상속받으면, 이 클래스의 필드(createdAt, updatedAt)도 컬럼으로 인식하게 함
@EntityListeners(AuditingEntityListener.class) // 2. 엔티티의 상태 변화(저장, 수정)를 감지하여 자동으로 시간을 넣어주는 리스너 등록
public abstract class BaseTimeEntity { // 3. 직접 객체로 생성할 일이 없으므로 추상 클래스(abstract)로 선언

    @CreatedDate // 4. 엔티티가 처음 저장(Insert)될 때 현재 시간을 자동으로 입력
    private LocalDateTime createdAt;

    @LastModifiedDate // 5. 엔티티가 수정(Update)될 때마다 마지막 수정 시간을 자동으로 갱신
    private LocalDateTime updatedAt;
}