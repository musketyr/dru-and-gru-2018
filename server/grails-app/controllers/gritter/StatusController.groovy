package gritter

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.UNAUTHORIZED

class StatusController {

    static responseFormats = ['json']

    def index() {
        Map queryParameters = [
                max: params.int('max', 25),
                offset: params.int('offset', 0),
                sort: params.sort ?: 'created',
                order: params.order ?: 'desc'
        ]
        [statuses: Status.list(queryParameters)]
    }

    def save(Status status) {
        User user = currentUser

        if (!user) {
            render status: UNAUTHORIZED
            return
        }

        status.user = user
        status.validate()

        if (status.hasErrors()) {
            respond status.errors
            return
        }

        status.save flush: true

        respond status, [status: CREATED, view: 'show']
    }

    private User getCurrentUser() {
        request.getAttribute('user') as User
    }

}
