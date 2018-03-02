package gritter


import grails.rest.*
import grails.converters.*

class StatusController {

    static responseFormats = ['json']

    def index() {
        Map queryParameters = [
                max: params.int('max', 25),
                offset: params.int('offset', 0),
                sort: 'created',
                order: 'desc'
        ]
        [states: Status.list(queryParameters)]
    }
}
