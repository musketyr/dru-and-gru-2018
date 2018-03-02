package gritter

class Status {

    Date created = new Date()
    User user
    String text

    static hasMany = [engagements: Engagement]

    static constraints = {
        text size: 1..255
    }

    @Override
    String toString() {
        return "$user.username: $text"
    }
}
