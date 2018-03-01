package gritter

class Status {

    Date dateCreated
    User user
    String text

    static hasMany = [engagements: Engagement]

    static constraints = {
        text size: 1..255
    }
}
