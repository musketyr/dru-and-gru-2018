package hello.world

import grails.gorm.services.Service


@Service(Greetings)
interface GreetingsService {

    Greetings findByLanguage(String language)

}
