# L3 Java-2

## Table of Contents

+ [About](#about)
+ [Installation](#getting_started)
+ [Usage](#usage)

## About <a name = "about"></a>

This project was developed as an end project for a Java-2 course. It is composed of a graphical interface built with JavaFX with a small scene manager, and uses JSON files.

It is a game based on the weakest link, where 4 players have to answer questions (Round-Robin selection) on various topics, with an increasing difficulty, until there is only one player left.

## Getting Started <a name = "getting_started"></a>

### Prerequisites

#### JDK 14

Download and install the JDK 14. This version is **mandatory** It may be an auto-download in your IDE parameters (in SDK options). Otherwise, you can download [Open JDK 14](https://jdk.java.net/14/), store it in a known folder, and add it in the list of SDK in your IDE.

#### With Gradle

If you are using a personal installation of Gradle, verify that the version is at least 6.3.

#### Without Gradle

This section is only for those who don't want to use Gradle

##### JavaFX

Download [JavaFX 14](https://gluonhq.com/products/javafx/). It is the library used to handle the GUI.

##### org.json

Download the [org.json](https://github.com/stleary/JSON-java) library. It is used to easily store information in files when serialization is not possible.

### Installing

#### Cloning

First thing to do is to clone the repository: 

```
git clone git@github.com:nerstak/L3-Java2.git
```

If asked, select the JDK 14.

#### Without Gradle

##### Installing the libraries

First, place libraries in a known folder (`lib` is a good one), and add them to your Java project (for JavaFX, you have to select the inner `lib` folder). This manipulation mays depends on your development environment.

For JavaFX, you will need to go further and follow the [tutorial](https://openjfx.io/openjfx-docs/), depending on your IDE (check the section *Non-modular from IDE* corresponding to yours, from step 2). Be careful when adding the path in the VM options, it needs to be absolute.

#### With Gradle

Verify that the version of the JVM used by Gradle is the JDK 14 you downloaded. 

## Usage <a name = "usage"></a>

### Without Gradle

Run the application, using the main function located in `src/Project/Main.java` as a target. 

### Without Gradle

Build the application with the task `build.build`.

Run the application with the task `application.run`.
