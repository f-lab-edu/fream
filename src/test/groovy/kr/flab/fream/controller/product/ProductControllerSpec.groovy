package kr.flab.fream.controller.product

import kr.flab.domain.product.ProductFixtures

import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.domain.product.service.ProductService
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration
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

import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProductController)
@Import([ObjectMapper, ModelMapperConfiguration])
class ProductControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    ProductService productService = Stub {
        search(_) >> ProductFixtures.getNikeProducts()
    }

    def "get Products"() {
        def expect = ProductFixtures.getNikeProducts().stream()
            .map(product -> new ProductDto(product.id, product.name,
                product.englishName, product.category.name(),
                product.brand.name, product.brand.englishName))
            .collect(Collectors.toList())

        def resultActions = mockMvc.perform(requestBuilder)

        expect:
        resultActions.andExpect(status().is(HttpStatus.OK.value()))

        and:
        def resBody = resultActions.andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8)
        def resultObject = Arrays.asList(objectMapper.readValue(resBody, ProductDto[].class))
        def config = new RecursiveComparisonConfiguration()
        config.ignoreCollectionOrder(true)
        assertThat(resultObject).usingRecursiveComparison(config).isEqualTo(expect)

        where:
        requestBuilder << [
            get("/products"),
            get("/products").param("page", "1").param("keyword", "nike")
        ]
    }

    def "return 400 If parameters are invalid when requesting Product List - '#testcase'"() {
        def resultActions = mockMvc.perform(request)

        expect:
        resultActions.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))

        and:
        resultActions.andExpect(jsonPath('$.status').value(400))
            .andExpect(jsonPath('$.error').value(errorMessage))

        where:
        testcase              || request                                 || errorMessage
        "Invalid page number" || get("/products").param("page", "0")     || "페이지 번호는 0보다 커야 합니다."
        "Invalid Keyword"     || get("/products").param("keyword", "a")  || "검색 키워드는 두 글자 이상이어야 합니다."
    }

}
