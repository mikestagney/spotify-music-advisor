# Spotify Music Advisor

Command line app that uses the Spotify API  and OAuth to return music recommendations to the user.

## Things learned 

Creating a REST controller with Java's HttpServer class, instead of Spring Boot.

Using OAuth for user authentication.

Using the GSON library to parse JSON strings.

Using generic programing to create one method that will work on collections of different objects.

### Details

User options:

Before the user can do anything, the user must get authenticated:

* The users is given a link to follow and confirm access
* Spotifiy's API will send an authorization code to the App's server
* The App uses the code to request an access token from Spotify's API
* If all goes well, the user is authenticated

Once authenticated in, the options are:

* featured - a list of Spotify-featured playlists with their links fetched from API
* new - a list of new albums with artists and links on Spotify
* categories - a list of all available categories on Spotify (just their names)
* playlists C_NAME (C_NAME = name of a category) - The list contains playlists of this category and their links on Spotify
* next - go forward one page on the recommendation list
* prev - go back one page on the recommendation list
* exit - quit the app

Fourteeth project created for JetBrains Academy Java Developer course - hard level project.

### Sample input and output:
