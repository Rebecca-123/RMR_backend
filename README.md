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
    "bigteam": "yay",
    "email": "yay@email.com",
    "password": "secure",
    "period": 2
}
```
