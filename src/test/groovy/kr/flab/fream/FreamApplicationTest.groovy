package kr.flab.fream


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class FreamApplicationTest extends DatabaseTest {

    @Autowired
    private ApplicationContext context

    def "load context"() {
        expect: "context will be loaded"
        context
    }

}
