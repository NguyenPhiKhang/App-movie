Popular Movies App
==================

[![Build Status](https://travis-ci.org/maksim-m/Popular-Movies-App.svg?branch=master)](https://travis-ci.org/maksim-m/Popular-Movies-App)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9d71713560374c938dba8a476ce8debf)](https://www.codacy.com/app/maksim-m/Popular-Movies-App)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/9d71713560374c938dba8a476ce8debf)](https://www.codacy.com/app/maksim-m/Popular-Movies-App)


## 🌟 Features
- Discover the most popular, the highest rated and the most rated movies
- Watch movie trailers and teasers
- Read reviews from other users
- Mark movies as favorites
- Search for movies
- Offline work
- Material design

## ✨ Screenshots
<table style="width:100%">
<tr>
    <th  width="33.3%"> <p align="center">
      <p>Main Screen</p>
       <img src="https://user-images.githubusercontent.com/45101536/72078593-f9749980-332b-11ea-9548-1add2ebc3c9b.gif"
            width="200" height="400"><br>
      </p>
  </th>
    <th width="33.3%"> <p align="center">
       <p>Movie Details</p>
       <img src="https://user-images.githubusercontent.com/45101536/72081510-342d0080-3331-11ea-8cd6-f5a69372590c.gif"
             width="200" height="400"><br>
  </p>
  </th>
  <th width="33.3%"> <p align="center">
       <p>Favorites Screen</p>
       <img src="https://user-images.githubusercontent.com/45101536/72080015-92a4af80-332e-11ea-805c-1f7e0f0a44fb.png"  width="200" height="400"><br>
      </p>
  </th>  
</tr>
<tr>
    <th  width="33.3%"> <p align="center">
      <p>Search Screen</p>
       <img src="https://user-images.githubusercontent.com/45101536/72077331-a568b580-3329-11ea-90fe-a697e270fd81.png"
            width="200" height="400"><br>
      </p>
  </th>
    <th width="33.3%"> <p align="center">
       <p>Player Screen</p>
       <img src="https://user-images.githubusercontent.com/45101536/72083339-380e5200-3334-11ea-9502-cd1230968f74.gif"
             width="200" height="400"><br>
  </p>
  </th>
  <th width="33.3%"> <p align="center">
       <p>Categories Screen Screen</p>
       <img src="https://user-images.githubusercontent.com/45101536/72079655-e6fb5f80-332d-11ea-9471-be6a9486e348.gif"  width="200" height="400"><br>
      </p>
  </tr>
</table>

## 🚀 Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
*   Android Studio 6.0+
*   Java JDK

### Project Overview
Most of us can relate to kicking back on the couch and enjoying
a movie with friends and family. In this project, you’ll build an app
to allow users to discover the most popular movies playing.

### Installing
<p>This app uses the API from [themoviedb.org](https://www.themoviedb.org/)</p>
<p>Follow these steps if you want to get a local copy of the project on your machine.</p>

#### 1. Clone or fork the repository by running the command below	
```
git https://github.com/NguyenPhiKhang/Popular_Movies_App.git
```
#### 2. Import the project in AndroidStudio, and add API Key
1.  In Android Studio, go to File -> New -> Import project
2.  Follew the dialog wizard to choose the folder where you cloned the project and click on open.
3.  Android Studio imports the projects and builds it for you.
4.  Add TheMovieDb API Key inside `gradle.properties` file.

```
MyTheMovieDBAPIToken = <API_KEY>
```

## 🤝 How to Contribute
1.  Fork it
2.  Create your feature branch (git checkout -b my-new-feature)
3.  Commit your changes (git commit -am 'Add some feature')
4.  Push to the branch (git push origin my-new-feature)
5.  Create new Pull Request

## 📃 Libraries used
*   [AndroidX](https://developer.android.com/jetpack/androidx/) - Previously known as 'Android support Library'
*   [Glide](https://github.com/bumptech/glide) - for loading and caching images 
*   [Retrofit 2](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc. 
*   [Gson](https://github.com/google/gson) - for serialization/deserialization Java Objects into JSON and back
*   [OkHttp](https://github.com/square/okhttp)
*   [CircleImageView](https://github.com/hdodenhof/CircleImageView)
*   [MaterialFavoritesButton](https://github.com/IvBaranov/MaterialFavoriteButton)
*   [MultiSnapRecyclerView](https://github.com/TakuSemba/MultiSnapRecyclerView)
*   [CarouselView](https://github.com/sayyam/carouselview)

## **References**
* [Sooshin](https://github.com/sooshin/android-popular-movies-app)
* [YassinAJDI](https://github.com/YassinAJDI/PopularMovies)
* [Delaroy](https://github.com/delaroy/MoviesApp)
* ....

## 📝 License
---

    Copyright 2020 by Nguyen Phi Khang
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
