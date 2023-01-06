package AlsongDalsong_backend.AlsongDalsong.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 생성, 수정 시간 매핑 정보 제공 테이블
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDateTime; // 생성 날짜 및 시간

    @LastModifiedDate
    private LocalDateTime modifiedDateTime; // 수정 날짜 및 시간
}