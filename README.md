# 🛰️ LaBoulangerie API

Web API for La Boulangerie Minecraft Server.
Access public data from Bukkit, Towny, LaBoulangerieMMO and other with ease.

## 📙 Docs

Docs available at http://api.laboulangerie.net.

### 🔌 Websockets

Websockets are not supported by Swagger, this is why there are documented right below.

Path: `/ws/towny`
You can connect to this websocket to receive events from Towny, like :

-   <details>
      <summary>NewTownEvent</summary>
      Triggered when a town is created
      
      *Example*
      ```json
      {
        "event": "NewTownEvent"
        "data": {
          "town": {
            "name": "CubeCity",
            "uuid": "1bb33d34-8553-401c-aaf8-6cd6f00d1cd4"
          } 
        }
      }
      ```
      You can then make a request to /town/UUID to get info on this town.
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
            "uuid": "48e344cf-2d30-4d80-a918-89c56a8393c3"
          } 
        }
      }
      ```
      You can then make a request to /nation/UUID to get info on this nation.
    </details>
-   <details>
      <summary>DeleteTownEvent</summary>
      Triggered when a town is deleted
      
      *Example*
      ```json
      {
        "event": "DeleteTownEvent"
        "data": {
          "town": {
            "name": "Saint-René Le Bon",
            "uuid": "a8cc6321-2b53-41c7-8644-36524cc6e96b"
          },
          "mayor": {
            "name": "Eomelius",
            "uuid": "ad24c6ae-0e5c-4fbd-9968-ddd4d71ef640"
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
            "uuid": "a8cc6321-2b53-41c7-8644-36524cc6e96b"
          },
          "king": {
            "name": "ZeMarshadow",
            "uuid": "ad24c6ae-0e5c-4fbd-9968-ddd4d71ef640"
          }
        }
      }
      ```
    </details>
