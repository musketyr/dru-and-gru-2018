package gritter

import grails.gorm.transactions.Transactional

@Transactional
class StatusService {

    List<Status> findTopStates(Date from, Date to, int max) {
        Status.findAllByCreatedBetween(from, to).sort(false) { a, b ->
            a.engagements?.size() <=> b.engagements?.size()
        }.reverse().take(max)
    }

}
