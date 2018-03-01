package gritter

class Engagement {

    User user
    Status status

    static belongsTo = [status: Status]

    static constraints = {
        status validator: { val, obj ->
            val.user != obj.user
        }
    }

    static mapping = {
        user column: '`user`'
    }
}
