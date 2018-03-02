package gritter

class Engagement {

    User user
    Status status
    Date dateCreated

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
