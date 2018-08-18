FROM java:8
ADD /build/libs/links-0.0.1-SNAPSHOT.jar links-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "links-0.0.1-SNAPSHOT.jar"]
