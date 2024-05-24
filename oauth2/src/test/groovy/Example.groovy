import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
class ExampleSpecification extends Specification {

    def "Should be a simple assertion"() {
        expect:
        1 == 1
    }
}