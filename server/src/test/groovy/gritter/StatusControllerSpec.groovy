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

    void setup() {
        dru.load()
    }

    void 'test index'() {
        when:
            params.max = 10
            params.offset = 5

            def model = controller.index()

        then:
            model
            model.statuses
            model.statuses.size() == 10

        when:
            def status = model.statuses.first()
        then:
            status
            status.user
            status.user.username == 'Ready Spready Go'
            status.text == 'Grooming snow close to Kirkintilloch'
    }

    // tag::gru-setup[]
    @Rule Gru gru = Gru.equip(Grails.steal(this)).prepare {
        include UrlMappings
        include UserInterceptor
    }
    // end::gru-setup[]


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
