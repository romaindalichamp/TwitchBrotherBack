# TwitchBrotherBack
Backend API to save Twitch datas in a Redis Database

## About
- SonarCloud report: https://sonarcloud.io/dashboard?id=fukakai_TwitchBrotherBack
- production URL: {heroku}

## Run
Deploy: `mvn spring-boot:run` on port 8080
SonarQube Analysis: `sonar-scanner` (with SONAR_TOKEN environment variable) 

## Sonar
* Download Sonar Scanner for windows: https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-4.4.0.2170-windows.zip
* And add the `bin` directory to the %PATH% environment variable
* run with `sonar-scanner`