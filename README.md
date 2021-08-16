# TwitchBrotherBack

Java Micro Service with 
- a Twitch Client to Poll Twitch API as fast as possible / authorized
- a websocket server pushing the results of the streaming datas to any connected client
- a bar chart to compare datas betweens the games
- You have to download and install the client (Angular 12) to consult all the analytics (see Frontend section)

## SonarCloud

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fukakai_TwitchBrotherBack&metric=alert_status)](https://sonarcloud.io/dashboard?id=fukakai_TwitchBrotherBack)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fukakai_TwitchBrotherBack&metric=coverage)](https://sonarcloud.io/dashboard?id=fukakai_TwitchBrotherBack)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=fukakai_TwitchBrotherBack&metric=security_rating)](https://sonarcloud.io/dashboard?id=fukakai_TwitchBrotherBack)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=fukakai_TwitchBrotherBack&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=fukakai_TwitchBrotherBack)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=fukakai_TwitchBrotherBack&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=fukakai_TwitchBrotherBack)

- deployed on Heroku here: https://dashboard.heroku.com/apps/twitch-brother-back

## Pre-installation

- You may need to install if not OpenJdk: https://openjdk.java.net/install/
- Download and install Redis: https://github.com/microsoftarchive/redis/releases
  - Leave default values, as Hostname = `localhost` and port `6379`
  - Add redis repository to the `PATH` (Check box with MSI version)
  - with the default MSI version, windows starts autmatically a redis server as a service after installation
  - from any cmd enter `redis-cli`
  - to see the saved results, enter `KEYS *` or use a GUI Client: https://www.npmjs.com/package/redis-commander
- Download and install maven: https://maven.apache.org/download.cgi
 
## Installation
- Define following environment variables: - values will be provided to you by email
  - `TWITCH_CLIENT_ID:` ...
  - `TWITCH_CLIENT_SECRET`={twitchClientSecret}
  - `ALLOWED_ORIGIN`=http://localhost:4200
  - `GAMES_LIST`=`460630,497078,506274,490382`
    - you can modify this list by any game id, pick one Category here https://www.twitch.tv/ubisoft?lang=fr and look at "Category" in the URL
    - for example Watch Dogs is: https://www.twitch.tv/ubisoft/videos?filter=archives&category=512895
  - how to define theses variables in Intellij: https://www.jetbrains.com/help/objc/add-environment-variables-and-program-arguments.html#add-environment-variables
- `mvn clean install -U`

## Deploy
- `mvn spring-boot:run` on port `8080`, http:"//localhost:8080

## Server

This server is running correctly if the logs are looking like: 

![img.png](img.png)

## Frontend

You can copy and install the repository from: https://github.com/fukakai/TwitchBrother

![img_1.png](img_1.png)