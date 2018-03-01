package gritter


import grails.rest.*
import grails.converters.*

class StatusController {

	static responseFormats = ['json']
	
    def index() {
        [states: Status.list(params)]
    }
}
