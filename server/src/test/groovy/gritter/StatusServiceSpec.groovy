package gritter

import com.agorapulse.dru.Dru
import com.agorapulse.dru.PreparedDataSet
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import groovy.json.JsonSlurper
import org.junit.Ignore
import org.junit.Rule
import spock.lang.IgnoreRest
import spock.lang.Specification

import java.text.DateFormat
import java.text.SimpleDateFormat

class StatusServiceSpec extends Specification implements ServiceUnitTest<StatusService>, DataTest {

    static PreparedDataSet statuses = Dru.prepare {
        from 'statuses.json', {
            map {
                to Status, {
                    ignore 'engagementsCount'
                }
            }
        }
    }

    @Rule Dru dru = Dru.plan {
        include statuses
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

    void 'get top statuses'() {
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




    @Ignore
    void 'load from JSON'() {
        when:
            List statuses = new JsonSlurper().parse(getStatusesSource())
        then:
            statuses
            statuses.size() == 25

        when:
            mockDomains User, Status, Engagement
            for (status in statuses) {
                new Status(status).save(failOnError: true)
            }
        then:
            noExceptionThrown()
            Status.list().size() == 25
            User.list().size() == 13
            Engagement.list().size() == 133
        when:
            Status status = Status.get(117)
        then:
            status
            status.user
            status.user.username == 'John Sno'
            status.text == 'Spreading some salt around Airdrie'
            status.engagements
            status.engagements.size() == 3
    }

    private InputStream getStatusesSource() {
        StatusServiceSpec.getResourceAsStream('StatusServiceSpec/statuses.json')
    }

}
