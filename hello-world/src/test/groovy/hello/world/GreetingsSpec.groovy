package hello.world

import com.agorapulse.dru.Dru
import grails.gorm.transactions.Rollback
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.Rule
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@Rollback                                                                       // <1>
class GreetingsSpec extends Specification {

    @Shared @AutoCleanup

    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)      // <2>

    @Shared GreetingsService service = embeddedServer.applicationContext
                                                     .getBean(GreetingsService) // <3>

    private static final List<Map<String, String>> GREETINGS = [                // <4>
            [language: 'dk', greeting: 'Hej Verden!'],
            [language: 'en', greeting: 'Hello World!'],
            [language: 'es', greeting: 'Hola Mundo!'],
    ]

    @Rule Dru dru = Dru.plan {
        from 'GREETINGS', {                                                     // <5>
            map {
                to Greetings                                                    // <6>
            }
        }
    }

    void setup() {
        dru.load()                                                              // <7>
    }

    void 'load with dru and fetch by gorm method'() {
        expect:
            dru.findAllByType(Greetings).size() == 3                            // <8>
            Greetings.findByLanguage('dk')                                      // <9>
    }

    void 'load with dru and fetch by gorm service'() {
        expect:
            dru.findAllByType(Greetings).size() == 3
            service.findByLanguage('dk')                                        // <10>
    }
}
