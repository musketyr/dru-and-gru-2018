package gritter

import com.agorapulse.dru.Dru
import com.agorapulse.gru.Gru
import com.agorapulse.gru.grails.Grails
import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import org.junit.Rule
import spock.lang.Specification

class StatusControllerSpec extends Specification
        implements ControllerUnitTest<StatusController>, DataTest {

    @Rule Dru dru = Dru.plan {                                                  // <1>
        from 'statuses.json', {                                                 // <2>
            map {
                to Status                                                       // <3>
            }
        }
    }

    void setup() {
        dru.load()                                                              // <4>
    }

    @Rule Gru gru = Gru.equip(Grails.steal(this)).prepare {
        include UrlMappings
        include UserInterceptor
    }

    void 'get statuses'() {
        expect:
            gru.test {                                                          // <5>
                get '/status', {
                    params max: 10, offset: 5                                   // <6>
                    executes  controller.&index
                }

                expect {
                    json 'statusesResponse.json'                                // <7>
                }
            }
    }

    void 'create status'() {                                                    // <8>
        expect:
            gru.test {
                post '/status', {
                    headers User: 'John Sno'
                    executes controller.&save
                    json 'newStatusRequest.json'

                }
                expect {
                    status CREATED
                    json 'newStatusResponse.json'
                }
            }
    }

}
