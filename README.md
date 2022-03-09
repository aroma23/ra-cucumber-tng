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
Where to see restassured logs: logs/ra.log
```

####Author
```commandline
Muthukumar Ramaiyah
linkedin: https://www.linkedin.com/in/muthukumar-ramaiyah-785673a0/ 
```

[comment]: <> (RestAssured specific)
<!--https://techbeacon.com/app-dev-testing/how-perform-api-testing-rest-assured?amp-->
[comment]: <> (https://github.com/rest-assured/rest-assured/wiki/Usage)

[comment]: <> (Sample Applications)
[comment]: <> (http://ergast.com/mrd/)
[comment]: <> (https://github.com/json-path/JsonPath)

[comment]: <> (Matchers)
[comment]: <> (http://hamcrest.org/JavaHamcrest/javadoc/2.2/)
[comment]: <> (https://stackoverflow.com/questions/51130241/test-if-an-array-contains-an-element-from-another-array-with-hamcrest)

[comment]: <> (JsonPath Specifics)
[comment]: <> (https://support.smartbear.com/alertsite/docs/monitors/api/endpoint/jsonpath.html)
[comment]: <> (https://github.com/json-path/JsonPath)
[comment]: <> ($.data[?&#40;@.email =~ /.*el.*/i&#41;])
[comment]: <> ($.data[?&#40;@.first_name =~ /E.*/i&#41;])
[comment]: <> ($.data[?&#40;!&#40;@.first_name =~ /E.*/i&#41;&#41;])
[comment]: <> (@.data[*].email)
[comment]: <> (https://www.youtube.com/watch?v=dLJBVCxnziM)
[comment]: <> (https://stackoverflow.com/questions/12585968/how-to-filter-by-string-in-jsonpath)
[comment]: <> (https://rows.com/docs/filtering-with-jsonpath)
[comment]: <> (https://support.smartbear.com/alertsite/docs/monitors/api/endpoint/jsonpath.html)
[comment]: <> (https://docs.oracle.com/cd/E60058_01/PDF/8.0.8.x/8.0.8.0.0/PMF_HTML/JsonPath_Expressions.htm)
[comment]: <> (https://docs.hevodata.com/sources/sdk-&-streaming/rest-api/writing-jsonpath-expressions/)
[comment]: <> (http://jsonpath.herokuapp.com/?path=$..book[?&#40;@.author%20=~%20/.*REES/i&#41;])