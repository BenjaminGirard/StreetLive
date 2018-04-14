# Available requests' list

This is the list of every endpoints that you can reach on the server, thanks to the tornado loop and the MUse library for communicate with the StreetLive Database.

# User

Remember that many other requests will probably appear over time, and at any time these requests may change.

## Create user

You reach the endpoint : **/user/create**
with the following parameters :
||Name|Optionnal|Type|
|-|-|-|-
|Firstname|`fn`|False|string
|Lastname|`ln`|False|string
|Mail|`uma`|False|string
|Birthday|`ub`|False|string (dd/MM/yyyy)
|Mobile|`umo`|True|string

- If success, this request will answer you with "200"
- If fail because of mandatory variable not provided, this will answer "400"
- If fail because user yet exists, this will answer "305"

## Connect user

This request permit you to connect as a user.
For this, reach the endpoint : **/user/connect**
with the following parameters :

||Name|Optionnal|Type|
|-|-|-|-
|User id|id|False|string
|User password|pwd|False|string

- If success, this request will answer a JSON of the user's information from the database
- If fail, this request will just answer an empty array {}
