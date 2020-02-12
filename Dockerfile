FROM openjdk:8u191-jre-alpine3.8
WORKDIR /usr/share/yummy_dir
ADD /target/selenium-docker-tests.jar        selenium-docker-tests.jar
ADD /target/libs                             libs
ADD /src/test/resources/run/run_two.xml      run_two.xml
ADD /src/test/resources/run/run_three.xml    run_three.xml
ENTRYPOINT java -cp selenium-docker-tests.jar:libs/* -DBROWSER=$BROWSER -DHUB_HOST=$HUB_HOST org.testng.TestNG $SUITE