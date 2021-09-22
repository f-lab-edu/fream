package kr.flab.fream.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link ModelMapper} 추가 설정 및 빈 생성을 위한 Configuration.
 *
 * @since 1.0.0
 */
@Configuration
public class ModelMapperConfiguration {

    /**
     * Field 이름으로 매핑하는 기능과 {@code private} 접근제어자까지 허용하는 옵션을 추가한
     * {@link ModelMapper} 빈을 생성한다.
     *
     * @return modelMapper 반환
     */
    @Bean
    public ModelMapper modelMapper() {
        final var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE);

        return modelMapper;
    }

}
