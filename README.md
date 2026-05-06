TWITTER CLONE
    Twitter Project using Java and Spring Boot featuring user management,tweeting and follow system.

TECHNOLOGIES
    -> Java
    -> Spring Boot
    -> PostgreSQL
    ->Gradle
    ->Docker
    ->Junit

FEATURES
    ->User Registration
    ->Post Tweets
    ->Follow/Unfollow
    ->View Timeline
    
DOCKER SETUP
     Containerized the application using Docker
  BUILD DOCKER IMAGE
     docker build -t user/twitter-service
  DOCKER RUN
     docker run -d -p 8080:8080 --name twitter-service localhost:5000/user/twitter-service:latest
  # Create a network
     docker network create my-app-network

  # Start postgres on that network
     docker run -d --name postgres --network twitter-network -e POSTGRES_DB=twitterdb -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5433:5432  postgres:15

  # Start springboot containers on same network
     docker run -d -p 8081:8080 --name twitter-service-1 --network twitter-network -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/twitterdb -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=postgres localhost:5000/sindhuja664/twitter-service:latest

     docker run -d -p 8082:8080 --name twitter-service-2 --network twitter-network -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/twitterdb -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=postgres localhost:5000/sindhuja664/twitter-service:latest
