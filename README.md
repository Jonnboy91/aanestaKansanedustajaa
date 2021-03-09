# Äänestä Kansanedustajaa

This is an android app school project (using Android Studio and Kotlin as the programming language).

Main goals for the app was to be able to show a list of parliament members and give them a vote either +1 or -1 and the data should be saved somewhere.
LiveData, ViewModel, room DB, Retrofit/Moshi, RecyclerView, WorkManager, etc. was used on this project, so overall it was a great project to learn different mechanisms and architecture

I get the Parliament Members data from an public API (https://avoindata.eduskunta.fi/api/v1/seating/).

Using that data I have created an app where you can choose first which parties members you want to see and after that you can click on the member itself and give him/her a vote and after that you will be able to leave a comment if you want to.

For the voting I have created my own API, which at the moment I have set up using our schools webdisk and phpMyAdmin where we have our schools SQL (http://users.metropolia.fi/~jonne/eduskunta/?action=getall). Voting is done by comparing the hetekaID of the parliament member to the hetekaID on the voting database.
For this I got help from my school mate https://github.com/tuoku

Also implemented same kind of system for comments, made an API same way as with the voting data.

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


## Photos

Few screenshots that have been taken from the Android Studio Emulator

First view: Different parties

![PartyList](https://user-images.githubusercontent.com/58616855/109813759-6c2e8b80-7c36-11eb-9139-cb9caa1ee1cc.png)

Second view: Members under chosen party

![MemberList](https://user-images.githubusercontent.com/58616855/109813772-6fc21280-7c36-11eb-9bea-6fb001508ff7.png)

Third view: Details of the member chosen

![MemberDetails2](https://user-images.githubusercontent.com/58616855/110214328-c5075980-7eac-11eb-954a-a7195ddb6c26.png)

Fourth view: Once you have voted, app will prompt for a comment for the vote

![MemberDetailsAfterVote](https://user-images.githubusercontent.com/58616855/110214350-d8b2c000-7eac-11eb-9ced-a19af1dd7c3c.png)

Fifth view: Comments in a recyclerview

![Comments](https://user-images.githubusercontent.com/58616855/110214376-f8e27f00-7eac-11eb-8c03-9f99fe9161ac.png)

