# sa-rest-assured-qa

Objective: Provide simple, elegant, efficient and powerful tests/framework to validate component testing for simple webservices such as 
reqres.in

## Requirements
```commandline
java version 11
maven version 3.5.4
```

## Usage
```commandline
mvn clean test -Dcucumber.filter.tags=@SampleApplication

-Dtest.env=dev (optional its default in pom.xml)
mvn clean test -Dcucumber.filter.tags=@SampleApplication -Dtest.env=dev

override properties during runtime over CLI
mvn clean test -Dcucumber.filter.tags=@SampleApplication -Dsa.user=<<user>> -Dsa.pwd=<<pwd>>
```

##Optional params
```commandline
-DthreadCount=10
```

##cucumber-report
```commandline
Where to see reports: target/cucumber-reports/cucumber-html-reports/overview-features.html
Where to see test logs: logs/tests.log
Where to see feign logs: logs/feign.log
```

####Author
```commandline
Muthukumar Ramaiyah
linkedin: https://www.linkedin.com/in/muthukumar-ramaiyah-785673a0/ 
```







