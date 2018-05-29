package gritter

import com.agorapulse.gru.Gru
import com.agorapulse.gru.grails.Grails
import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import org.junit.Rule
import spock.lang.Specification

class StatusControllerBasicSpec extends Specification
        implements ControllerUnitTest<StatusController>, DataTest {             // <1>

    @Rule Gru gru = Gru.equip(Grails.steal(this)).prepare {                     // <2>
        include UrlMappings                                                     // <3>
        include UserInterceptor                                                 // <4>
    }

    void setup() {
        mockDomains User, Status                                                // <5>
    }

    void 'create status'() {
        given:
            new User(username: 'John Sno').save(failOnError: true)              // <6>
        expect:
            gru.test {                                                          // <7>
                post '/status', {
                    headers User: 'John Sno'
                    executes controller.&save                                   // <8>
                    json 'newStatusRequest.json'

                }
                expect {
                    status CREATED
                    json 'newStatusResponse.json'
                }
            }
    }

}
