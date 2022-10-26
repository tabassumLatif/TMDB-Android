
# TMDB Movies

Tmdb app with MVVM and clean code architecture

## Architecture and Tech-stack

* Built on MVVM architecture pattern
* Following Clean Architecture approach
* Uses [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/), specifically ViewModel, LiveData and Room.
* Completely offline ready. movie_db uses [Room](https://developer.android.com/topic/libraries/architecture/room) for managing a local SQLite database, which means that if you have seen some content already while you were online, you won't need an internet connection to see it again
* Uses [Retrofit](https://square.github.io/retrofit/) for making API calls.
* Uses [Glide](https://github.com/bumptech/glide) for image loading.

## Features
* Discover Top Rated and Popular movies on TMDB.
* Search for movies
* User can favorite movies 
* View banner image, votes release date, revenue and movie details inside the app.
* Works offline by caching data into a database.

