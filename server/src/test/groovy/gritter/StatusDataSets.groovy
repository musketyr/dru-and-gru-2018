package gritter

import com.agorapulse.dru.Dru
import com.agorapulse.dru.PreparedDataSet

class StatusDataSets {

    static PreparedDataSet statuses = Dru.prepare {                             // <1>
        from 'statuses.json', {
            map {
                to Status
            }
        }
    }

}
