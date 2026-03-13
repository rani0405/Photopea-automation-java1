# Photopea Automation using Playwright (Java)

## Overview

This project automates basic image editing workflows on Photopea using Playwright with Java.

The automation performs the following actions:

* Opens Photopea
* Creates shapes (rectangle, circle, triangle)
* Adds text to the canvas
* Performs drag and drop inside the editor
* Loads an external image
* Removes the background
* Exports the project as:

  * PNG
  * JPG
  * PSD
* Captures a preview screenshot
* Validates that exported files exist

---

## Tech Stack

* Java
* Playwright
* Maven

---

## Project Structure

PhotopeaAutomation1_qa-assignment
│
├── src/main/java/Package/photopeaAssignment.java
├── src/main/java/Package/photopeaAutomationAss.java
├── exports/
│   ├── preview.png
│   ├── output.png
│   ├── output.jpg
│   └── output.psd
├── pom.xml
└── README.md

---

## Prerequisites

Make sure the following are installed:

* Java 17+
* Maven
* Playwright

Install Playwright browsers:

mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"

---

## How to Run the Project

Clone the repository:

git clone (https://github.com/rani0405/Photopea-automation-java1.git)

Navigate to project folder:

cd photopea-automation

Run the automation:

mvn compile
mvn exec:java -Dexec.mainClass="Package.photopeaAssignment"

---

## What the Automation Tests

1. Drag and drop functionality in Photopea editor
2. Export functionality for PNG, JPG, and PSD
3. Preview screenshot generation
4. Validation that exported files are downloaded successfully

---



## Video Walkthrough

Video :

Recorded using https://cap.so
Video Link:https://cap.so/s/7jjhmsj13fsbcjn


## Notes

The project follows good Git practices with multiple commits instead of a single commit as required by the assignment.

---

## Author

Rani Suresh Nikhade
