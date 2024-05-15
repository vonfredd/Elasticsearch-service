# Elasticsearch-service

#### This service runs on ``port 8006`` and is used with elasticsearch.

-----------------
### This service is responsible for searching through names:
* Endpoint ``/newuser`` makes a GET request to a userservice and must be connected to the service that provides the user information. The user information is saved in the elasticsearch-cluster.

Let's say we are provided with three users:
```
[
    {
        "name": "Olle",
        "userID": "11111"
    },
    {
        "name": "Ulla",
        "userID": "22222"
    },
    {
        "name": "Ella",
        "userID": "33333"
    }
]
    
```
* Endpoint ``/search`` takes a requestparam and searches for names ``THAT STARTS WITH`` the value of the requestparam.<br>
``/search?query=lla`` would not find any because no name starts with "lla".

* Endpoint ``/search/contains`` takes a requestparam and searches for names ``CONTAINING`` the value of the requestparam.<br>
``/search/contains?query=lla`` would find "Ulla" and "Ella" because they both contain "lla".

## Environment variables 
### spring.elasticsearch.uris=${ELASTICSEARCH_URI}
  - ELASTICSEARCH_URI is the location of the elasticsearch cluster. This is where the data will be saved and requested from by the endpoints.
  ```
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.13.0
    container_name: elasticsearch2
    restart: on-failure
    ports:
      - "9200:9200"
    volumes:
      - elastic_data:/usr/share/elasticsearch/data/
    environment:
      - xpack.security.enabled=false
      - discovery.type=single-node
  ```
 When running as a service with docker compose like the code above, the variable should be set to ``http://elasticsearch:9200`` since the servicename is elasticsearch and the port of the service is 9200.
  ___
### get.users.from.uri=${GET_USERS}
  - GET_USERS is the location where you will get the userdata from.
  ```
  usersapp:
    image: ghcr.io/chatgut/userservice:1.0
    restart: on-failure
    container_name: userservice
    ports:
      - "8002:8002"
    depends_on:
      - dbUsers
    environment:
      DB_URL: jdbc:mysql://dbUsers:3306/userService
      DB_USER: developer
      DB_PASSWORD: password
  ```
When running the userservice with docker compose like the code above, the variable should be set to ``http://usersapp:8002/users/all`` since the servicename is usersapp and the port of the service is 8002 and this particular user app has an endpoint called ``/users/all``
that returns a list of users.

## So with the two services above...
An implementation of this application as a service would look like this:

```
  search-service:
    image: search:1
    ports:
      - "8006:8080"
    environment:
      GET_USERS: http://usersapp:8002/users/all
      ELASTICSEARCH_URI: http://elasticsearch:9200
    restart: on-failure
```

## How to run
Clone the repository and run the following command:
```docker compose up``` to run with the examples above.
