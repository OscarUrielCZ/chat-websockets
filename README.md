## Chat Web Socket

This is a web socket chat, it means that users in conversation don't need to refresh their browser to get notifications.

Key Features of the app:

- Connected users are notified when a new user joins to conversation.
- All messaagees are stored in an SQL database.
- Messages are recovered from past conversations and showed to new users.
- This app has the 3 modules contenerized with docker (server, client and DB).

Technologies used for implementation:

- Java and Spring WebSockets
- Angular + NGINX server to deploy
- Postgresql
- Docker compose

**\*NOTE**: Angular container uses NGINX server, but it doesn't work properly until this commit

### How to run the program

#### Option 1. Run docker compose

With your docker compose installed, run the following command:

```
docker compose -f docker-compose.yml up
```

#### Option 2. Run components individually

{{ missing documentation }}
