# Äänestä Kansanedustajaa

This is an android app school project (using Android Studio and Kotlin as the programming language).

Main goals for the app was to be able to show a list of parliament members and give them a vote either +1 or -1 and the data should be saved somewhere.
LiveData, ViewModel, room DB, Retrofit/Moshi, RecyclerView, WorkManager, etc. was used on this project, so overall it was a great project to learn different mechanisms and architecture

I get the Parliament Members data from an public API (https://avoindata.eduskunta.fi/api/v1/seating/).

Using that data I have created an app where you can choose first which parties members you want to see and after that you can click on the member itself and give him/her a vote.

For the voting I have created my own API, which at the moment I have set up using our schools webdisk and phpMyAdmin where we have our schools SQL (http://users.metropolia.fi/~jonne/eduskunta/?action=getall). For this I got help from my school mate https://github.com/tuoku
