// tag::header[]
package gritter

import com.agorapulse.gru.Gru
import com.agorapulse.gru.http.Http
import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback
import org.junit.Rule
import org.springframework.beans.factory.annotation.Value
import spock.lang.Specification

@Integration                                                                    // <1>
@Rollback
class StatusControllerIntegrationSpec extends Specification  {

    @Value('${local.server.port}') Integer serverPort                           // <2>

    @Rule Gru<Http> gru = Gru.equip(Http.steal(this))                           // <3>

    void setup() {
        final String serverUrl = "http://localhost:${serverPort}"
        gru.prepare {
            baseUri serverUrl                                                   // <4>
        }
    }
    // end::header[]

    void 'create status'() {
        expect:
            gru.test {
                post '/status', {
                    headers User: 'John Sno'
                    json 'newStatusRequest.json'

                }
                expect {
                    status CREATED
                    json 'newStatusResponse.json'
                }
            }
    }

    // tag::footer[]
}
// end::footer[]
