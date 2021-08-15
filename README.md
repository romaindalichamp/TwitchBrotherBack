# TwitchBrotherBack

Java Micro Service with 
- a Twitch Client to Poll Twitch API as fast as possible / authorized
- a websocker server pushing the results of the streaming datas to any connected client
- You can download and install the client (Angular 12) to consult all the analytics here: https://github.com/fukakai/TwitchBrother

## SonarCloud

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fukakai_TwitchBrotherBack&metric=alert_status)](https://sonarcloud.io/dashboard?id=fukakai_TwitchBrotherBack)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fukakai_TwitchBrotherBack&metric=coverage)](https://sonarcloud.io/dashboard?id=fukakai_TwitchBrotherBack)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=fukakai_TwitchBrotherBack&metric=security_rating)](https://sonarcloud.io/dashboard?id=fukakai_TwitchBrotherBack)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=fukakai_TwitchBrotherBack&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=fukakai_TwitchBrotherBack)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=fukakai_TwitchBrotherBack&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=fukakai_TwitchBrotherBack)

- deployed on Heroku here: https://dashboard.heroku.com/apps/twitch-brother-back

## Run in local

- you may need to install if not OpenJdk: https://openjdk.java.net/install/
- and maven: https://maven.apache.org/download.cgi
- define following environment variables: - will be provided to you by email
  - TWITCH_CLIENT_ID: {twitchClientSecret}
  - TWITCH_CLIENT_SECRET={twitchClientSecret}
  - ALLOWED_ORIGIN=http://localhost:4200
- to deploy: `mvn spring-boot:run` on port 8080 SonarQube Analysis: `sonar-scanner` (with SONAR_TOKEN
environment variable)
