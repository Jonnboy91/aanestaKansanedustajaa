# Äänestä Kansanedustajaa

This is an android app school project (using Android Studio and Kotlin as the programming language).

Main goals for the app was to be able to show a list of parliament members and give them a vote either +1 or -1 and the data should be saved somewhere.
LiveData, ViewModel, room DB, Retrofit/Moshi, RecyclerView, WorkManager, etc. was used on this project, so overall it was a great project to learn different mechanisms and architecture

I get the Parliament Members data from an public API (https://avoindata.eduskunta.fi/api/v1/seating/).

Using that data I have created an app where you can choose first which parties members you want to see and after that you can click on the member itself and give him/her a vote.

For the voting I have created my own API, which at the moment I have set up using our schools webdisk and phpMyAdmin where we have our schools SQL (http://users.metropolia.fi/~jonne/eduskunta/?action=getall). Voting is done by comparing the hetekaID of the parliament member to the hetekaID on the voting database.
For this I got help from my school mate https://github.com/tuoku

If you end up checking the code in a closer look, you can see that the VoteList is not used at the moment, it is because that is just something I have thought about that could be a cool feature, so that when you give a vote (+ or -) you would be prompted by a comment box and you could leave a reason to why you voted how you did and then that would be saved also in a database and then there would be an option to go see how everyone has voted and see the comments given. 

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

![MemberDetails](https://user-images.githubusercontent.com/58616855/109813775-72bd0300-7c36-11eb-9a2f-5eff281eabb9.png)
