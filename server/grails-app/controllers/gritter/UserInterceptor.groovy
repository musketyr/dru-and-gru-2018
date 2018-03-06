package gritter

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class UserInterceptor {

    UserInterceptor() { matchAll() }

    boolean before() {
        String username = request.getHeader('User')

        if (username) {
            request.setAttribute('user', User.withNewSession { User.findByUsername(username) })
        }

        return true
    }

}
