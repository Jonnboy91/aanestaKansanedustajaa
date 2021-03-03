# Äänestä Kansanedustajaa

This is an android app school project (using Android Studio and Kotlin as the programming language).

Main goals for the app was to be able to show a list of parliament members and give them a vote either +1 or -1 and the data should be saved somewhere.
LiveData, ViewModel, room DB, Retrofit/Moshi, RecyclerView, WorkManager, etc. was used on this project, so overall it was a great project to learn different mechanisms and architecture

I get the Parliament Members data from an public API (https://avoindata.eduskunta.fi/api/v1/seating/).

Using that data I have created an app where you can choose first which parties members you want to see and after that you can click on the member itself and give him/her a vote.

For the voting I have created my own API, which at the moment I have set up using our schools webdisk and phpMyAdmin where we have our schools SQL (http://users.metropolia.fi/~jonne/eduskunta/?action=getall). Voting is done by comparing the hetekaID of the parliament member to the hetekaID on the voting database.
For this I got help from my school mate https://github.com/tuoku


## Example of using my vote API 
#### Getting the points
Getting everyones hetekaID & score from the database:
`GET http://users.metropolia.fi/~jonne/eduskunta/?action=getall`  
Returns:  
```
[{
    "hetekaID": "1297",
    "score": "4"
}
...
{
  "hetekaID": "670",
  "score": "0"
}]
```
#### Vote up  
Voting Erkki Tuomioja (hetekaID 357) up:  
`GET users.metropolia.fi/~jonne/eduskunta?id=357&action=plus`  
Returns:  
`{"message":"success"}`  

#### Vote down
Voting Katja Hänninen (hetekaID 1276) down:  
`GET users.metropolia.fi/~jonne/eduskunta?id=1276&action=minus`  
Returns:  
`{"message":"success"}`  
