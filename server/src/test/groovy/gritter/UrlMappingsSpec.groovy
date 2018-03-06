package gritter

import grails.testing.web.UrlMappingsUnitTest
import spock.lang.Specification

class UrlMappingsSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController StatusController
    }

    void 'check forward mapping'() {
        expect:
            assertForwardUrlMapping('/status', controller: 'status', action: 'index')
    }

}
