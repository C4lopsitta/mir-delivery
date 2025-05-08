# mir-delivery

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need
  to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## ER Diagram
```mermaid
erDiagram

InventoryItem {
        uid VARCHAR(36) PK
        name VARCHAR()
        availableQuantity INTEGER
        img VARCHAR()
}

Classroom {
        uid VARCHAR(36) PK
        number USINT
        dispatchMissionGUID VARCHAR()
        returnMissionGUID VARCHAR()
}

Order {
        uid VARCHAR(36) PK
        userId VARCHAR(36) FK
        inventoryId VARCHAR(36) FK
        classroomId VARCHAR(36) FK
}
        
User {
        uid VARCHAR(36) PK
        name VARCHAR(32)
        surname VARCHAR(32)
        emailAddress VARCHAR(128) UK
        passwordHash VARCHAR()
        role USINT
}

MirBot {
        uid VARCHAR(36) PK
        name VARCHAR(64)
        ipv4 VARCHAR(16) UK
}
        
Order }o--|| InventoryItem : "For"
Order }o--|| Classroom : "To"
Order }o--|| User : "Made by"

```

### Notes for ER Diagram
- `Classroom.dispatchMissionGUID` is the mission GUID to reach a classroom

## Features

Here's a list of features included in this project:

| Name                                                                   | Description                                                                        |
|------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| [Routing](https://start.ktor.io/p/routing)                             | Provides a structured routing DSL                                                  |
| [Authentication](https://start.ktor.io/p/auth)                         | Provides extension point for handling the Authorization header                     |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)     | Provides automatic content conversion according to Content-Type and Accept headers |
| [kotlinx.serialization](https://start.ktor.io/p/kotlinx-serialization) | Handles JSON serialization using kotlinx.serialization library                     |
| [Exposed](https://start.ktor.io/p/exposed)                             | Adds Exposed database to your application                                          |

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
|-------------------------------|----------------------------------------------------------------------|
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

