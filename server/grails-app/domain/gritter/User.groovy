package gritter

class User {

    String username

    static constraints = {
        username size: 1..63
    }
}
