google-topic-analyzer
=====================

Google+ topic analyzer. Searches through G+ activities for topics, then sends those to correlators.

INPUT
-----------------

Accepted Content-Type:
```
application/vnd.com.ofg.google-topic-analyzer.v1+json
```

Hit *PUT* at: 

```
/api/{pairId}
```

with list of activities:

{
    [
        {
          "kind": "plus#activity",
          "etag": etag,
          "title": string,
          "published": datetime,
          "updated": datetime,
          "id": string,
          "url": string,
          "actor": {
            "id": string,
            "displayName": string,
            "name": {
              "familyName": string,
              "givenName": string
            },
            "url": string,
            "image": {
              "url": string
            }
          },
          "verb": string,
          "object": {
            "objectType": string,
            "id": string,
            "actor": {
              "id": string,
              "displayName": string,
              "url": string,
              "image": {
                "url": string
              }
            },
            "content": string,
            "originalContent": string,
            "url": string,
            "replies": {
              "totalItems": unsigned integer,
              "selfLink": string
            },
            "plusoners": {
              "totalItems": unsigned integer,
              "selfLink": string
            },
            "resharers": {
              "totalItems": unsigned integer,
              "selfLink": string
            },
            "attachments": [
              {
                "objectType": string,
                "displayName": string,
                "id": string,
                "content": string,
                "url": string,
                "image": {
                  "url": string,
                  "type": string,
                  "height": unsigned integer,
                  "width": unsigned integer
                },
                "fullImage": {
                  "url": string,
                  "type": string,
                  "height": unsigned integer,
                  "width": unsigned integer
                },
                "embed": {
                  "url": string,
                  "type": string
                },
                "thumbnails": [
                  {
                    "url": string,
                    "description": string,
                    "image": {
                      "url": string,
                      "type": string,
                      "height": unsigned integer,
                      "width": unsigned integer
                    }
                  }
                ]
              }
            ]
          },
          "annotation": string,
          "crosspostSource": string,
          "provider": {
            "title": string
          },
          "access": {
            "kind": "plus#acl",
            "description": string,
            "items": [
              {
                "type": string,
                "id": string,
                "displayName": string
              }
            ]
          },
          "geocode": string,
          "address": string,
          "radius": string,
          "placeId": string,
          "placeName": string,
          "location": {
            "kind": "plus#place",
            "displayName": string,
            "position": {
              "latitude": double,
              "longitude": double
            },
            "address": {
              "formatted": string
            }
          }
        }
    ]
}

OUTPUT
-----------------

And it will hit collectors at /{pairId} with topics extracted from google activities
```

[
    {
        "activityId" : "12344",
        "topics" : [ "topic1", "topic2" ]
    }
]

[![Build Status](https://travis-ci.org/microhackaton/google-topic-analyzer.svg?branch=master)](https://travis-ci.org/microhackaton/google-topic-analyzer)
