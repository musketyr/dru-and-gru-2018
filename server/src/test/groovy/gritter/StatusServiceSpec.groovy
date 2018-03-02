package gritter

import com.agorapulse.dru.Dru
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import org.junit.Rule
import spock.lang.Specification

class StatusServiceSpec extends Specification implements ServiceUnitTest<StatusService>, DataTest {

    @Rule Dru dru = Dru.plan {
        from 'states.json', {
            map {
                to Status, {
                    ignore 'engagementsCount'
                }
            }
        }
    }

    void 'test loaded'() {
        expect:
            dru.report.empty
        when:
            dru.load()
        then:
            Status.list().size() == 25
            User.list().size() == 12
            Engagement.list().size() == 152

        when:
            Status first = dru.findByTypeAndOriginalId(Status, 200)
        then:
            first.user
            first.user.username == 'Gritty Gritty Bang Bang'
            first.text == 'Getting rid off snow after leaving Greenock'
            first.engagements
            first.engagements.size() == 5
    }

}
