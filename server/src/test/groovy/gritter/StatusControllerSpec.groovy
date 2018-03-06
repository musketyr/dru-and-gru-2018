package gritter

import com.agorapulse.dru.Dru
import com.agorapulse.gru.Gru
import com.agorapulse.gru.grails.Grails
import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import org.junit.Rule
import spock.lang.Specification

class StatusControllerSpec extends Specification implements ControllerUnitTest<StatusController>, DataTest {

    @Rule Dru dru = Dru.plan {
        include StatusServiceSpec.statuses
    }

    @Rule Gru gru = Gru.equip(Grails.steal(this)).prepare {
        include UrlMappings
        include UserInterceptor
    }

    void setup() {
        dru.load()
    }

    void 'get statuses'() {
        expect:
            gru.test {
                get '/status', {
                    params max: 10, offset: 5
                    executes  controller.&index
                }

                expect {
                    json 'statuses.json'
                }
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
