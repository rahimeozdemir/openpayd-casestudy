# openpayd-casestudy

### technologies project is created with:
* maven 3.9.8
* java 21
* docker 27.0.3

### build:
#### todolist application:
* to create jar file in target folder run 'mvn clean package' command
* download docker desktop to local machine https://www.docker.com/products/docker-desktop/
* run following docker commands
    * docker build -t openpayd-app .
    * docker run -p 8090:8080 openpayd-app
* go to [http://localhost:8090/swagger-ui/index.html#/](http://localhost:8090/swagger-ui/index.html#/)

### used external api
* https://currencylayer.com

### database
* used in memory database called h2
* go to h2 db console via http://localhost:8080/h2-console
* data source information
  * url: jdbc:h2:mem:foreignexchangehistory
  * username: superuser
  * password: password

### api usage:
* exchange-rate
  * with this endpoint we could calculate the exchange rate between two given currencies
  * the response returned from this api is put into cache for 1 minute. We used caffeine in memory cache
* convert
  * with this endpoint we could convert a specific amount from one currency to another
* conversation-history
  * with this endpoint we could query the history of currency conversions. This api return paginated list of converted 
  currencies
* We can view the sample request and response models used in the API in Swagger
