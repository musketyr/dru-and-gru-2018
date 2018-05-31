package hello.world

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get


@Controller("/hello")
class HelloController {

    @Get("/")
    String index() {
        return "Hello World"
    }
}
