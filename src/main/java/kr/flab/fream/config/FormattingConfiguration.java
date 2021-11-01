package kr.flab.fream.config;

import kr.flab.fream.domain.product.OrderOption.OrderOptionConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * {@code @RequestParam} 입력을 enum 타입으로 변환해주는 컨버터 설정 클래스.
 *
 * @since 1.0.0
 */
@Configuration
public class FormattingConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new OrderOptionConverter());
    }

}
