google-topic-analyzer
=====================

Google+ topic analyzer. Searches through G+ activities for topics, then sends those to correlators.

INPUT
-----------------

Accepted Content-Type:
```
application/json
```

Hit *PUT* at: 

```
/api/{pairId}
```

with list of activities:

[
    {"id": "123", "content": "#test1 #test2"},
    {"id": "456", "content": "#test3 #test4"}
]

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
