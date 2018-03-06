package gritter

import com.agorapulse.gru.Gru
import com.agorapulse.gru.http.Http
import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback
import org.junit.Rule
import org.springframework.beans.factory.annotation.Value
import spock.lang.Specification

@Integration
@Rollback
class StatusControllerIntegrationSpec extends Specification  {

    @Value('${local.server.port}') Integer serverPort

    @Rule Gru<Http> gru = Gru.equip(Http.steal(this))

    void setup() {
        final String serverUrl = "http://localhost:${serverPort}"
        gru.prepare {
            baseUri serverUrl
        }
    }

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

}
