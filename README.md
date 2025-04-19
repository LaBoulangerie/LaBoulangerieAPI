# 🛰️ LaBoulangerie API

Web API for La Boulangerie Minecraft Server.
Access public data from Bukkit, Lands, LaBoulangerieMMO and other with ease.

## 📙 Docs

Docs available at http://api.laboulangerie.net.

### 🔑 Tokens

Some of the routes are only accessible with an API key. For now, there are only admin routes requiring API keys but in the future there might be new restricted routes for a selected set of users.

### 🔌 Websockets

Websockets are not supported by Swagger, this is why there are documented right below.

Path: `/ws/lands`
You can connect to this websocket to receive events from Lands, like :

-   <details>
      <summary>NewLandEvent</summary>
      Triggered when a land is created
      
      *Example*
      ```json
      {
        "event": "NewLandEvent"
        "data": {
          "land": {
            "name": "CubeCity",
            "id": "1bb33d34-8553-401c-aaf8-6cd6f00d1cd4"
          } 
        }
      }
      ```
      You can then make a request to /land/ULID to get info on this land.
    </details>
-   <details>
      <summary>NewNationEvent</summary>
      Triggered when a nation is created
      
      *Example*
      ```json
      {
        "event": "NewNationEvent"
        "data": {
          "nation": {
            "name": "BreadPain",
            "id": "48e344cf-2d30-4d80-a918-89c56a8393c3"
          } 
        }
      }
      ```
      You can then make a request to /nation/ULID to get info on this nation.
    </details>
-   <details>
      <summary>DeleteLandEvent</summary>
      Triggered when a land is deleted
      
      *Example*
      ```json
      {
        "event": "DeleteLandEvent"
        "data": {
          "land": {
            "name": "Saint-René Le Bon",
            "id": "a8cc6321-2b53-41c7-8644-36524cc6e96b"
          },
        }
      }
      ```
    </details>
-   <details>
      <summary>DeleteNationEvent</summary>
      Triggered when a nation is deleted
      
      *Example*
      ```json
      {
        "event": "DeleteNationEvent"
        "data": {
          "nation": {
            "name": "ZeUnion",
            "id": "a8cc6321-2b53-41c7-8644-36524cc6e96b"
          },
        }
      }
      ```
    </details>
