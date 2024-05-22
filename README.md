# modern-renting-service

## How to run containerized services
```console
# Build up jar file locally
./mvnw clean install -DskipTests -Dspotless.check.skip=true

docker build . 

docker run -p 8080:<PORT> <CONTAINER_ID>
```