# Zoho Office Integrator Java SDK Examples

## Table Of Contents

* [Overview](#overview)
* [Environmental Setup](#environmental-setup)
* [Including the SDK in your project](#including-the-sdk-in-your-project)

## Overview

This code repository showcases examples that illustrate the utilization of the Java SDK for Zoho Office Integrator in order to engage with Office Suite Editors.

## Environmental Setup

JAVA SDK requires Java (version 8 and above) to be set up in your development environment.

## Including the SDK in your project

Java SDK is included in this project through Maven distribution. You can include the SDK to your project using:

1. **Maven**

    - pom.xml file.

    ```xml
    <repositories>
        <repository>
            <id>zoi-java-sdk</id>
            <url>https://maven.zohodl.com</url>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>com.zoho.officeintegrator</groupId>
            <artifactId>zoi-java-sdk</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
    ```

2. **Gradle**

    ```gradle
    repositories{
        maven { url "https://maven.zohodl.com" }
    }
    dependencies{
        implementation 'com.zoho.officeintegrator:zoi-java-sdk:1.0.0'
    }
     ```

3. **Download and Bundle SDK Jar in your project**
   
   [zoi-java-sdk-1.0.0.jar](https://maven.zohodl.com/com/zoho/officeintegrator/zoi-java-sdk/1.0.0/zoi-java-sdk-1.0.0.jar) - Please download this jar and include along with the following dependency jars in your project to run Office Integrator java sdk on your application.

### Dependency JARs

[commons-lang3-3.9.jar](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.9)

[commons-logging-1.1.3.jar](https://mvnrepository.com/artifact/commons-logging/commons-logging/1.1.3)

[httpclient-4.4.1.jar](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.4.1)

[httpcore-4.4.4.jar](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore/4.4.4)

[httpmime-4.5.3.jar](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpmime/4.5.3)

[json-20170516.jar](https://mvnrepository.com/artifact/org.json/json/20170516)

[mysql-connector-java-5.1.47-bin.jar](https://mvnrepository.com/artifact/mysql/mysql-connector-java/5.1.47)

[opencsv-5.0.jar](https://mvnrepository.com/artifact/com.opencsv/opencsv/5.0)

