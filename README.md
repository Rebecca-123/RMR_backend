# MR. R Backend

## API Documentation
Base URL: mrr.rebeccaaa.tk

**Team API**: ``/api/team``
| endpoint | description | 
| - | - |
| GET: ``/`` | get teams |
| GET: ``/{id}`` | get info for particular team, use for profile page |
| POST: ``/delete/{id}`` | delete user, probably won't use this feature for now |
| POST: ``/post`` | create new team, put team info as json in requestion body, example below |
| POST: ``/addMember/{id}`` | add member to given team, pass user object in request body, see below |
| POST: ``/search`` | search for user |

Sample team JSON object: 
```
{
    "bigteam": "yay",
    "email": "yay@email.com",
    "password": "secure",
    "period": 2
}
```

Sample user JSON object: 
```
{
    "name": "bob",
    "githubId": "bob123",
    "blog": "str"
}
```



**Assignments API**: ``/api/review`` (Used View Controller for this particulat POJO
| endpoint | description | 
| - | - |
| GET: ``/`` | get all assignments for the team |
| GET: ``reviews/{id}`` | get info for particular assignment |
| POST: ``/addreview/{id}`` | adding assignments to a particular team |

Sample team JSON object: 
```
{
    "assignment": "Assignment 1"
     "Team": "c"
    "score": "2.7"
    "ticket": "review ticket"
    "comments": "something"
}
```

**Tasks API**: ``/api/task`` 
| endpoint | description | 
| - | - |
| GET: ``/`` | get tasks for each specific person in the team, given the id |
| PUT: ``finish/{id}`` | when the task is finished, can check off that the person finished the task |
| PUT: ``/progress/{id}`` | when the task is being worked on, can check off that the person making progress on the task |

Sample team JSON object: 
```
{
    "id": "42",
    "task": "AWS Deployment",
    "finished": "false",
    "progress": "true"
}
```
