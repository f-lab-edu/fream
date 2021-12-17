package kr.flab.fream.domain.auction.dto;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.modelmapper.TypeToken;

/**
 * 체결 결과 응답 객체.
 *
 * @author Jake
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class SignAuctionResponse {

    private static final Type TYPE_OBJECT = new TypeToken<SignAuctionResponse>() {
    }.getType();

    long id;
    LocalDateTime signedAt;

    public static Type getTypeObject() {
        return TYPE_OBJECT;
    }
}
