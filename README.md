# ms-proxy-service

INTRODUCTION
A Simple Proxy Service capable of routing the requests to the destination server and returns the response received.

USER GUIDE
Proxy service can limit the number of requests from a client and time out for each request. The configurations are achieved using environment variable configuration,
1.	io.anbu.proxy.user.limit: default value - 50
Controls the number of requests for a client for the time mentioned in io.anbu.proxy.limit.reset.minutes.

2.	io.anbu.proxy.timeout.seconds: default value – 5
Controls the timeout settings of each https request raised by the proxy server. 

3.	io.anbu.proxy.limit.reset.minutes: default value – 1
Time after which the request count will be reset for a client 

DEVELOPER GUIDE
1.	Import the project as a Maven Project in any IDE (Eclipse / IntelliJ IDEA). 
2.	This is a SPRING BOOT project, hence can be executed directly with the embedded tomcat server by running the main function as a JAVA application from the IDE.
3.	Run mvn clean install to generate the packaged binary “proxy-service-DEV.0.0-SNAPSHOT.zip” for deployment, which will be available in target folder.
4.	Environment Configurations are present in “ms-proxy-service.yml” docker compose file.

DEPLOYMENT INSTRUCTIONS
Prerequisite: Install docker and turn on Linux containers.
1.	Proxy Service is available as a single release bundle “proxy-service-DEV.0.0-SNAPSHOT.zip”
2.	Unzip the file. Refer Developer Guide section to generate the package.
3.	Execute ms-proxy-service.bat / ms-proxy-service.sh up –build -d
4.	Proxy server will be up and running in the port 8080
5.	Refer the API documentation for the end points related to the proxy service.
