package kr.flab.fream.config

import kr.flab.domain.product.ProductFixtures
import kr.flab.fream.controller.product.ProductDto
import org.modelmapper.ModelMapper
import spock.lang.Specification

class ModelMapperConfigurationSpec extends Specification {

    private ModelMapper modelMapper;

    def setup() {
        def configuration = new ModelMapperConfiguration();
        modelMapper = configuration.modelMapper()
    }

    def "map Product to ProductDto"() {
        given:
        def product = ProductFixtures.getNikeDunkLowRetroBlack();
        def expect = new ProductDto(
            product.id,
            product.name,
            product.englishName,
            product.category.name(),
            product.brand.name,
            product.brand.englishName
        )

        expect:
        modelMapper.map(product, ProductDto.class) == expect
    }
    
}
