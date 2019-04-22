# Dino Game by Denis Tkachev and Semeon Korolev


This program interfaces arduino with accelerometer to play game of T-Rex.

### Build and Run

Firstly, build the code with maven:

    mvn clean package

If everything is OK, folder `target` is created and the executable `denis-dino.jar` in it.

Run the executable:

    java -jar target/denis-dino.jar

Alternatively you can run chart viewer

    java -jar target/denis-dino.jar chart

In this case also data from file could be fed to the chart:

    java -jar target/denis-dino.jar chart filename.txt
