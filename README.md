# forexingester
Oanda FX data ingester

## To compile
$ mvn clean package

## To run
java -Dspring.profiles.active=<your profile> -jar forexingester-1.0-MASTER.jar --spring.cloud.config.uri=<config server addr>
