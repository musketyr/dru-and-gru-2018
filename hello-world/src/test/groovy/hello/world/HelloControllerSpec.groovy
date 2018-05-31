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

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    @Rule Gru gru = Gru.equip(Http.steal(this))

    void setup() {
        String serverUrl = embeddedServer.getURL()
        gru.prepare {
            baseUri serverUrl
        }
    }

    void "test index"() {
        expect:
            gru.test {
                get '/hello'
                expect {
                    text inline('Hello World')
                }
            }
    }
}
