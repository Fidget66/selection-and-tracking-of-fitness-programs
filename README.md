# Selection and tracking of fitness programs
<p align="center">
<img src="https://img.shields.io/badge/made%20by-Makul%20Raman-blue" >
<img src="https://img.shields.io/github/issues/Fidget66/selection-and-tracking-of-fitness-programs">
<img src="https://sonarcloud.io/api/project_badges/measure?project=Fidget66_selection-and-tracking-of-fitness-programs&metric=alert_status">
</p>

### Description
<img src="https://media.giphy.com/media/cUyvte0RxObfPTDF3A/giphy.gif">
Selection of fitness programs according to the parameters of the person and the duration of the course,
scheduling and reminder of classes. It is also possible to view information about the programs and add reviews.

### DB scheme
The structure of model links in our fitness app is shown below
![img.png](.img/FitnessAppDBSchema.png)

### Getting Started
    First, run:
    './mvnw clean install'
    Next:
    'docker-compose -f docker-compose.yml up -d --build'

and open link http://localhost:8122/fitnessweb-app

You can also use the Swagger UI at link http://localhost:8124/fitnessDB-app/swagger-ui


