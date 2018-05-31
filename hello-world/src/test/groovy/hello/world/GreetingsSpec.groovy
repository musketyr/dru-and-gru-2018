package hello.world

import com.agorapulse.dru.Dru
import grails.gorm.transactions.Rollback
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.Rule
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@Rollback
class GreetingsSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared GreetingsService service = embeddedServer.applicationContext.getBean(GreetingsService)

    private static final List<Map<String, String>> GREETINGS = [
            [language: 'en', greeting: 'Hello!'],
            [language: 'cs', greeting: 'Ahoj!'],
    ]

    @Rule Dru dru = Dru.plan {
        from 'GREETINGS', {
            map {
                to Greetings
            }
        }
    }

    void setup() {
        dru.load()
    }

    void 'load with dru and fetch by gorm method'() {
        expect:
            dru.findAllByType(Greetings).size() == 2
            Greetings.findByLanguage('cs')
    }

    void 'load with dru and fetch by gorm service'() {
        expect:
            dru.findAllByType(Greetings).size() == 2
            service.findByLanguage('cs')
    }
}
