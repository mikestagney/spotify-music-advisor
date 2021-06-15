# Spotify Music Advisor

Command line app that uses the Spotify API and OAuth to return music recommendations to the user.

## Things learned 

Creating a REST controller with Java's HttpServer class, instead of Spring Boot.

Using OAuth for user authentication.

Using the GSON library to parse JSON strings.

Using generic programing to create one method that will work on collections of different objects.

### Details

User options:

Before the user can do anything, the user must get authenticated:

* The user is given a link to follow and confirm access
* Spotifiy's API will send an authorization code to the App's server
* The App uses the code to request an access token from Spotify's API
* If all goes well, the user is authenticated

Once authenticated in, the options are:

* featured - a list of Spotify-featured playlists with their links fetched from API
* new - a list of new albums with artists and links on Spotify
* categories - a list of all available categories on Spotify (just their names)
* playlists C_NAME(name of a category) - The list contains playlists of this category and their links on Spotify
* next - go forward one page on the recommendation list
* prev - go back one page on the recommendation list
* exit - quit the app

#### The directory contains 9 files: 

* Main.java - sets up the UserMenu object and passes the args array to it
* UserMenu.java - handles all user input and controls all app `activity
* WebConnection.java - class that handles all HTTP requests, responses and runs the server that gets the access code
* NewAlbums.java - parses the JSON string for new albums and stores them as Album objects in an array list
* PlaylistParser.java - parses the JSON string for featured or detailed playlists and stores them as Playlist objects in an array list
* CategoryArchive.java - parses the JSON string for playlist categories and stores them as Category objects in an array list
* Album.java - class for Album objects
* Playlist.java - class for Playlist objects 
* Category.java - class for Category objects

Fourteenth project created for JetBrains Academy Java Developer course - hard level project.

### Sample input and output:

\> new\
Please, provide access for application.\
\> auth\
use this link to request the access code:\
https://accounts.spotify.com/authorize?client_id=6a3cee939e094944a5b8c547da47dba2&redirect_uri=http://localhost:8080&response_type=code \
waiting for code...\
code received\
Making http request for access_token...\
Success!\
\> new\
OT ALL HEROES WEAR CAPES\
[Metro Boomin, Travis Scott, 21 Savage]\
https://open.spotify.com/album/1zNr37qd3iZJ899byrTkcj

I Used To Know Her - Part 2 - EP\
[H.E.R.]\
https://open.spotify.com/album/46imFLgb9fR1Io6EoPYeQh

The Last Rocket\
[Takeoff]\
https://open.spotify.com/album/5XRCcUfwtLNQflDd9cfz4U

Interstate Gospel\
[Pistol Annies]\
https://open.spotify.com/album/0IXxmmlfSQxgJNWnNjHhgJ

El Mal Querer\
[ROSALÍA]\
https://open.spotify.com/album/355bjCHzRJztCzaG5Za4gq

\-\-\-PAGE 1 OF 5\-\-\-\
\> prev\
No more pages.\
\> next\
Mountains\
[Sia, Diplo, Labrinth]\
https://open.spotify.com/album/3dB0bCgmpEgCSr3aU1bOtv

Moving Pictures\
[Rush]\
https://open.spotify.com/album/2xg7iIKoSqaDNpDbJnyCjY

Shootin Shots (feat. Ty Dolla $ign & Tory Lanez)\
[Trey Songz, Ty Dolla $ign]\
https://open.spotify.com/album/6Erhbwa5HmDwuzYacUpLPr

Runaway\
[Lil Peep]\
https://open.spotify.com/album/38sesm68q3lg21o6Lpzslc

RESET\
[Moneybagg Yo]\
https://open.spotify.com/album/547DJFUYOl2SBYJbo2jZX1

\-\-\-PAGE 2 OF 5\-\-\-\
\> categories\
Top Lists\
Mood\
Chill\
Hip-Hop\
Electronic/Dance\
\-\-\-PAGE 1 OF 10\-\-\-\
\> next\
Kids & Family\
Rock\
Indie\
Happy Holidays\
Workout\
\-\-\-PAGE 2 OF 10\-\-\-\
\> exit
