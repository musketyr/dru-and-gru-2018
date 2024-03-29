package gritter

class Engagement {

    User user
    Status status
    Date created = new Date()

    static constraints = {
        status validator: { val, obj ->
            val.user != obj.user
        }
    }

    static mapping = {
        user column: '`user`'
    }

    @Override
    String toString() {
        return "$user liked status '$status'"
    }
}
