package gritter

import grails.plugin.json.view.test.JsonRenderResult
import grails.views.json.test.JsonViewUnitTest
import net.javacrumbs.jsonunit.fluent.JsonFluentAssert
import spock.lang.Specification

class StatusGsonSpec extends Specification implements JsonViewUnitTest {

    void 'test index rendered'() {
        when:
            Map<String, List<Status>> model = [statuses: [new Status(
                    user: new User(username: 'Vlad'),
                    text: 'Hi all!'
            )]]
            JsonRenderResult result = render(view: "/status/index", model: model)
        then:
            result.json instanceof List
            result.json.size() == 1
            result.json[0].user
            result.json[0].user.username == 'Vlad'
            result.json[0].text == 'Hi all!'
            result.json[0].engagements instanceof List
            result.json[0].engagementsCount == 0
    }

    void 'test index rendered with json unit'() {
        when:
            Map<String, List<Status>> model = [statuses: [new Status(
                    user: new User(username: 'Vlad'),
                    text: 'Hi all!'
            )]]
            JsonRenderResult result = render(view: "/status/index", model: model)
        then:
            JsonFluentAssert.assertThatJson(result.jsonText).isEqualTo('''[
                {
                    "created" : "${json-unit.ignore}",
                    "text" : "Hi all!",
                    "user" : {
                        "username" : "Vlad"
                    },
                    "engagementsCount" : 0,
                    "engagements" : []
                }
            ]''')
    }

}
