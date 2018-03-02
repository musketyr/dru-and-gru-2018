package gritter

import com.agorapulse.dru.Dru
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import org.junit.Rule
import spock.lang.Specification

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime

class StatusServiceSpec extends Specification implements ServiceUnitTest<StatusService>, DataTest {

    @Rule Dru dru = Dru.plan {
        from 'statuses.json', {
            map {
                to Status, {
                    ignore 'engagementsCount'
                }
            }
        }
    }

    void setup() {
        dru.load()
    }

    void 'test loaded'() {
        expect:
            dru.report.empty
            Status.list().size() == 25
            User.list().size() == 13
            Engagement.list().size() == 133

        when:
            Status first = dru.findByTypeAndOriginalId(Status, 117)
        then:
            first.user
            first.user.username == 'John Sno'
            first.text == 'Spreading some salt around Airdrie'
            first.engagements
            first.engagements.size() == 3
    }

    void 'get top states'() {
        given:
            DateFormat format = new SimpleDateFormat('yyyy-MM-dd')
        when:
            List<Status> statuses = service.findTopStates(
                    format.parse('2018-02-01'),
                    format.parse('2018-03-20'),
                    50
            )
        then:
            statuses
            statuses.size() == 19
        when:
            Status first = statuses[0]
        then:
            first.user
            first.user.username == 'Sprinkles'
            first.text == 'Refueling before leaving Larkhall'
            first.engagements
            first.engagements.size() == 11
    }

}
