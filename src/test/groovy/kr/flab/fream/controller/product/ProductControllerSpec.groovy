package kr.flab.fream.controller.product

import kr.flab.fream.config.FormattingConfiguration
import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.domain.product.OrderOption
import kr.flab.fream.domain.product.SearchOption
import kr.flab.fream.domain.product.service.ProductService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

import static kr.flab.domain.product.ProductFixtures.nikeProducts
import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProductController)
@Import([ObjectMapper, ModelMapperConfiguration, FormattingConfiguration])
class ProductControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    ProductService productService = Mock {
        search(_ as SearchOption) >> getNikeProducts()
    }

    def "get Products"() {
        def expect = getNikeProducts().stream()
            .map(product -> new ProductDto(product.id, product.name,
                product.englishName, product.category.name(),
                product.brand.name, product.brand.englishName))
            .collect(Collectors.toList())

        def resultActions = mockMvc.perform(request)

        expect:
        resultActions.andExpect(status().is(HttpStatus.OK.value()))

        and:
        def resBody = resultActions.andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8)
        def resultObject = Arrays.asList(objectMapper.readValue(resBody, ProductDto[].class))
        assertThat(resultObject).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expect)

        where:
        request << [
            get("/products"),
            get("/products")
                .param("page", "1")
                .param("keyword", "nike"),
            get("/products")
                .param("page", "1")
                .param("keyword", "nike")
                .param("category", "CLOTHING"),
        ]
    }

    def "return 200 and sort by popularity(view count) even using wrong sort option input"() {
        given:
        def request = get("/products")
            .param("orderOption", "INVALID_ORDER_OPTION")

        when:
        def resultActions = mockMvc.perform(request)

        then:
        resultActions.andExpect(status().is(HttpStatus.OK.value()))

        and:
        1 * productService.search(_) >> {
            def searchOption = (it as List<?>)[0] as SearchOption
            assert searchOption.orderOption == OrderOption.POPULAR
            return getNikeProducts()
        }
    }

    def "return 400 If parameters are invalid when requesting Product List - '#testcase'"() {
        def resultActions = mockMvc.perform(request)

        expect:
        resultActions.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))

        and:
        resultActions.andExpect(jsonPath('$.status').value(400))
            .andExpect(jsonPath('$.error').value(errorMessage))

        where:
        testcase              || request                                                || errorMessage
        "Invalid page number" || get("/products").param("page", "0")                    || "페이지 번호는 0보다 커야 합니다."
        "Invalid Keyword"     || get("/products").param("keyword", "a")                 || "검색 키워드는 두 글자 이상이어야 합니다."
        "Invalid Category"    || get("/products").param("category", "INVALID_CATEGORY") || "존재하지 않는 카테고리 입니다."
    }

}
