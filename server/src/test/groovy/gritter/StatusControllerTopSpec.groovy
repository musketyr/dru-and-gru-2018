package gritter

import com.agorapulse.dru.Dru
import com.agorapulse.gru.Gru
import com.agorapulse.gru.grails.Grails
import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import org.junit.Rule
import spock.lang.Specification

class StatusControllerTopSpec extends Specification
        implements ControllerUnitTest<StatusController>, DataTest {

    @Rule Dru dru = Dru.plan {
        include StatusDataSets.statuses
    }

    void setup() {
        dru.load()
    }

    @Rule Gru gru = Gru.equip(Grails.steal(this)).prepare {
        include UrlMappings
    }

    // tag::top-statuses[]
    void 'top statuses'() {
        given:
            StatusService statusService = Mock(StatusService)                   // <1>
            controller.statusService = statusService                            // <2>

            Date from = StatusController.FORMAT.parse('2018-02-01')
            Date to = StatusController.FORMAT.parse('2018-03-01')
        when:
            gru.test {
                get '/status/top', {
                    params max: 10, from: '2018-02-01', to: '2018-03-01'
                }

                expect {
                    json 'topStatusesResponse.json'
                }
            }
        then:
            1 * statusService.findTopStatuses(from, to, 10) >> {                // <3>
                dru.findAllByType(Status).take(10)
            }
            gru.verify()                                                        // <4>
    }
    // end::top-statuses[]

}
