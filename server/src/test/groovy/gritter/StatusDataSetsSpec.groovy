package gritter

import com.agorapulse.dru.Dru
import grails.testing.gorm.DataTest
import org.junit.Rule
import spock.lang.Specification

class StatusDataSetsSpec extends Specification implements DataTest {

    @Rule Dru dru = Dru.plan {
        include StatusDataSets.statuses                                         // <1>
    }

    void setup() {
        dru.load()
    }

    void 'test loaded'() {
        expect:
            Status.list().size() == 25                                          // <2>
            User.list().size() == 13
            Engagement.list().size() == 133

        when:
            Status first = dru.findByTypeAndOriginalId(Status, 117)             // <3>
        then:                                                                   // <4>
            first.user
            first.user.username == 'John Sno'
            first.text == 'Spreading some salt around Airdrie'
            first.engagements
            first.engagements.size() == 3
    }

}
