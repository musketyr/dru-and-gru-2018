package gritter

import grails.gorm.transactions.Transactional

@Transactional
class StatusService {

    List<Status> findTopStates(Date from, Date to, int count) {

    }
}
