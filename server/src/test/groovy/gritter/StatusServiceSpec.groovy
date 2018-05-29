package gritter

import com.agorapulse.dru.Dru
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import org.junit.Rule
import spock.lang.Specification

import java.text.DateFormat
import java.text.SimpleDateFormat

class StatusServiceSpec extends Specification implements ServiceUnitTest<StatusService>, DataTest {

    @Rule Dru dru = Dru.plan {
        include StatusDataSets.statuses
    }

    void setup() {
        dru.load()
    }

    void 'get top statuses'() {
        given:
            DateFormat format = new SimpleDateFormat('yyyy-MM-dd')
        when:
            List<Status> statuses = service.findTopStatuses(
                    format.parse('2018-02-01'),
                    format.parse('2019-02-01'),
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
