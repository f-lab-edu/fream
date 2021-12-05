package kr.flab.fream.domain.user.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주소 타입 클래스
 * 정해진 주소와, 사용자가 직접입력하는 상세주소로 나뉜다.
 * 사용자가 주소의 별칭을 설정할수 있다. (ex: 집, 회사, 본가...)
 *
 * @since 1.0.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Long id;
    private Long userId;
    private String alias;
    private String address;
    private String addressDetail;
    private Boolean isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}