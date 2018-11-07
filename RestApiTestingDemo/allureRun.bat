call mvn -Dtest=TestAllureReport clean test
copy /y environment.properties .\target\allure-results
copy /y categories.json .\target\allure-results
allure serve .\target\allure-results