package hello.world

import com.agorapulse.gru.Gru
import com.agorapulse.gru.http.Http
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.Rule
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class HelloControllerSpec extends Specification {

    @Shared @AutoCleanup
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)      // <1>

    @Rule Gru gru = Gru.equip(Http.steal(this))                                 // <2>

    void setup() {
        String serverUrl = embeddedServer.getURL()                              // <3>
        gru.prepare {
            baseUri serverUrl                                                   // <4>
        }
    }

    void "test index"() {
        expect:
            gru.test {
                get '/hello'                                                    // <5>
                expect {
                    text inline('Hello World')                                  // <6>
                }
            }
    }
}
