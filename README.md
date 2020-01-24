# Secret Santa
![GitHub release (latest by date)](https://img.shields.io/github/v/release/mschoeffel/SecretSanta)
[![Travis (.com)](https://img.shields.io/travis/com/mschoeffel/SecretSanta)](https://travis-ci.com/mschoeffel/SecretSanta)
[![Deployed on Heroku](https://img.shields.io/badge/heroku-deployed-blueviolet.svg?logo=heroku)](https://mschoeffel-secret-santa.herokuapp.com/)
[![GitHub](https://img.shields.io/github/license/mschoeffel/SecretSanta)](https://github.com/mschoeffel/SecretSanta/blob/master/LICENSE)

[![versionjava](https://img.shields.io/badge/jdk-8,_9,_11-brightgreen.svg?logo=java)](https://github.com/spring-projects/spring-boot)
[![versionvuejs](https://img.shields.io/badge/dynamic/json?color=brightgreen&url=https://raw.githubusercontent.com/mschoeffel/SecretSanta/master/Presentation/package.json&query=$.dependencies.vue&label=vue&logo=vue.js)](https://vuejs.org/)
[![versionwebpack](https://img.shields.io/badge/dynamic/json?color=brightgreen&url=https://raw.githubusercontent.com/mschoeffel/SecretSanta/master/Presentation/package-lock.json&query=$.dependencies.webpack.version&label=webpack&logo=webpack)](https://webpack.js.org/)
[![versionjest](https://img.shields.io/badge/dynamic/json?color=brightgreen&url=https://raw.githubusercontent.com/mschoeffel/SecretSanta/master/Presentation/package-lock.json&query=$.dependencies.jest.version&label=jest&logo=jest)](https://jestjs.io/)
[![versionnightwatch](https://img.shields.io/badge/dynamic/json?color=brightgreen&url=https://raw.githubusercontent.com/mschoeffel/SecretSanta/master/Presentation/package-lock.json&query=$.dependencies.nightwatch.version&label=nightwatch)](http://nightwatchjs.org/)
[![versionspringboot](https://img.shields.io/badge/dynamic/xml?color=brightgreen&url=https://raw.githubusercontent.com/mschoeffel/SecretSanta/master/pom.xml&query=%2F%2A%5Blocal-name%28%29%3D%27project%27%5D%2F%2A%5Blocal-name%28%29%3D%27parent%27%5D%2F%2A%5Blocal-name%28%29%3D%27version%27%5D&label=springboot)](https://github.com/spring-projects/spring-boot)

## Description
A simple app to manage your secret santa group. 

## Requirements
To run this application java needs to be installed.

## How to run it
1. Download the jar from the latest release.
2. Run the downloaded jar form the command line with jre/jdk: `java -Xmx1024m -Dserver.port=PORT_YOU_WANT -jar /path/to/the/jar.jar`.
3. The application is booting up and can be called on the given port vom the commandline(`PORT_YOU_WANT`).

## Technology

### Techstack
Separated frontend(Vue.js) from the backend(SpringBoot) and tried to separate backend layer into Controller, Client and Server.
Connection all together using Maven packaging and REST.

### Testing
Also tried adding all possible tests to the front- and backend.
In the frontend: Vue jest unit tests, Nightwatch e2e tests and in the backend: Controller test and Service unit test.

### CI/CD
Furthermore added CI/CD pipeline using Travis CI and Heroku.

### Database
Locally it uses H2 Database but can also be used with PostgreSQL therefore you need to add the following Arguments to the commandline starting the jar:
* `-Dspring.datasource.url` = jdbc:postgresql://**YourPostgresHerokuHostNameHere**:**Port**/**YourPostgresHerokuDatabaseNameHere**
* `-Dspring.datasource.username` = YourPostgresHerokuUserNameHere
* `-Dspring.datasource.password` = YourPostgresHerokuPasswordHere
* `-Dspring.datasource.driver-class-name` = `org.postgresql.Driver`
* `-Dspring.jpa.database-platform` = `org.hibernate.dialect.PostgreSQLDialect`
* `-Dspring.datasource.type` = `org.apache.tomcat.jdbc.pool.DataSource`
* `-Dspring.jpa.hibernate.dll-auto` = `update`

-----

&copy; mschoeffel