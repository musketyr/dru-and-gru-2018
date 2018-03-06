package gritter

import grails.util.Environment

class BootStrap {

    def init = { servletContext ->
        if (Environment.current in [Environment.TEST, Environment.DEVELOPMENT]) {
            // http://scotgov.maps.arcgis.com/apps/webappviewer/index.html?id=2de764a9303848ffb9a4cac0bd0b1aab

            List<String> names = [
                    'John Sno',
                    'Ice Queen',
                    'The Snow Buster',
                    'Snow Destroyer',
                    'Ready Spready Go',
                    'Gritty Gritty Bang Bang',
                    'Gritty Gonzales',
                    'Gritallica',
                    'Grittie McVittie',
                    'Plougher O\'Scotland',
                    'Sir Andy Furry',
                    'Sir Salter Scott',
                    'Sprinkles',
            ]

            List<String> activities = [
                    'Getting rid off snow',
                    'Spreading some salt',
                    'Clearing the road',
                    'Refueling',
                    'Lunch time',
                    'Time to get some coffee',
                    'Taking well deserved rest',
                    'Nothing to do',
                    'Please, be careful',
                    'Loading grit',
                    'Grooming snow',
                    'Melting snow',
                    'Melting ice',
                    'Blowing away snow',
                    'I\'ve seen some white walkers',
            ]

            List<String> directionsAndLocations = [
                    'in',
                    'near',
                    'around',
                    'close to',
                    'not far from',
                    'before leaving',
                    'after leaving',
            ]

            List<String> places = [
                    'Glasgow',
                    'Edinburgh',
                    'Aberdeen',
                    'Dundee',
                    'Paisley',
                    'East',
                    'Livingston',
                    'Hamilton',
                    'Cumbernauld',
                    'Dunfermline',
                    'Kirkcaldy',
                    'Ayr',
                    'Perth',
                    'Inverness',
                    'Kilmarnock',
                    'Coatbridge',
                    'Greenock',
                    'Glenrothes',
                    'Airdrie',
                    'Stirling',
                    'Falkirk',
                    'Irvine',
                    'Dumfries',
                    'Motherwell',
                    'Rutherglen',
                    'Wishaw',
                    'Cambuslang',
                    'Bearsden',
                    'Clydebank',
                    'Newton',
                    'Arbroath',
                    'Musselburgh',
                    'Bishopbriggs',
                    'Elgin',
                    'Renfrew',
                    'Bathgate',
                    'Bellshill',
                    'Alloa',
                    'Dumbarton',
                    'Kirkintilloch',
                    'Peterhead',
                    'Barrhead',
                    'Grangemouth',
                    'Blantyre',
                    'Kilwinning',
                    'Johnstone',
                    'Bonnyrigg',
                    'Penicuik',
                    'Viewpark',
                    'Erskine',
                    'Broxburn',
                    'Port',
                    'Larkhall',
            ]

            for (String name in names) {
                User.findOrCreateWhere(username: name).save(failOnError: true)
            }

            List<User> users = User.list()

            Random random = new Random()

            200.times {
                Status status = new Status(
                        user: users[random.nextInt(users.size())],
                        text: "${activities[random.nextInt(activities.size())]} ${directionsAndLocations[random.nextInt(directionsAndLocations.size())]} ${places[random.nextInt(places.size())]}",
                        created: new Date(System.currentTimeMillis() - (10L * Math.abs(random.nextInt())))
                ).save(failOnError: true)

                random.nextInt(users.size()).times {
                    User user = users[it]
                    if (user != status.user && !status.engagements.any { it.user == user }) {
                        status.addToEngagements(user: user, status: status).save(failOnError: true)
                    }
                }
            }

        }
    }
    def destroy = {
    }
}
