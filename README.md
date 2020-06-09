# L3 Java-2

## Table of Contents

+ [About](#about)
+ [Installation](#getting_started)
+ [Usage](#usage)

## About <a name = "about"></a>

This project was developed as an end project for an advanced java course.

## Getting Started <a name = "getting_started"></a>

### Prerequisites

#### JDK 14

Download and install the JDK 14. This version is **mandatory** It may be an auto-download in your IDE parameters (in SDK options). Otherwise, you can download [Open JDK 14](https://jdk.java.net/14/), store it in a known folder, and add it in the list of SDK in your IDE.

#### JavaFX

Download [JavaFX 14](https://gluonhq.com/products/javafx/). It is the library used to handle the GUI.

#### org.json

Download the [org.json](https://github.com/stleary/JSON-java) library. It is used to easily store information in files when serialization is not possible.

### Installing

#### Cloning

First thing to do is to clone the repository: 

```
git clone git@github.com:nerstak/L3-Java2.git
```
If asked, select the JDK 14.

#### Installing the libraries

First, place libraries in a known folder (`lib` is a good one), and add them to your Java project (for JavaFX, you have to select the inner `lib` folder). This manipulation mays depends on your development environment.
For JavaFX, you will need to go further and follow the [tutorial](https://openjfx.io/openjfx-docs/), depending on your IDE (check the section *Non-modular from IDE* corresponding to yours, from step 2). Be careful when adding the path in the VM options, it needs to be absolute.

## Usage <a name = "usage"></a>

Run the application, using the main function located in `src/Project/Main.java` as a target.
