package hello.world

import grails.gorm.annotation.Entity

@Entity
class Greetings {

    String language
    String greeting

    static constraints = {
        language minSize: 2, maxSize: 2
        greeting size: 1..255
    }
}
