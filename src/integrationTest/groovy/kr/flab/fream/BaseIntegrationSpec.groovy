package kr.flab.fream

import groovy.transform.CompileStatic
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import org.junit.Rule
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.JUnitRestDocumentation

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration

@CompileStatic
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseIntegrationSpec extends DatabaseTest {

    @Rule
    JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation()

    RequestSpecification spec

    def setup() {
        this.spec = new RequestSpecBuilder()
            .addFilter(documentationConfiguration(this.restDocumentation)
                .operationPreprocessors()
                .withResponseDefaults(prettyPrint()))
            .build()
    }

}
