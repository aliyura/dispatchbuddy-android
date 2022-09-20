# Dispatchbuddy-android

DispatchBuddy is an online delivery service solution that connects users to dispatch riders available to move goods from one location to another.

## API Key

The app uses google maps to display location of users. Get an api key [here](https://developers.google.com/maps/documentation/android-sdk/get-api-key)

The app also uses Google Places Api to get autocomplete places suggestions. Get an api key [here](https://developers.google.com/maps/documentation/places/android-sdk/get-api-key)

* Create the file `secure.properties` in your project level directory, and then add the following code to the file. Replace `YOUR_API_KEY` with your API key.
```
    MAPS_API_KEY=YOUR_API_KEY
    GOOGLE_PLACES_API_KEY=YOUR_API_KEY

```
* Place it in `app/build.gradle`
```
android {
    defaultConfig {
       ...
         Properties properties = new Properties()
        properties.load(project.rootProject.file('secure.properties').newDataInputStream())
        manifestPlaceholders = [MAPS_API_KEY: "${properties.getProperty('MAPS_API_KEY')}"]
        buildConfigField(
                "String",
                "GOOGLE_PLACES_API_KEY",
                "${properties.getProperty('GOOGLE_PLACES_API_KEY')}"
        )
       ...
    }
    ...
}
```

## Features

* [Material Components][material]
* [Constraint Layout][constraint-layout]
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for image loading
* [Mockk][mockk] for mocking in tests
* [DaggerHilt][daggerhilt] for dependency injection
* [Room][room] for database
* [Kotlin Flow][flow] for concurrency
* [ViewModel][viewmodel] & [LiveData][livedata]
* [Navigation Architecture Component][nav]
* [Clean Architecture][clean-arch]
* [ViewBinding][viewbinding]
* [Kotlin Delegates][delegates]
* [Paging 3.0][paging-3]
* [Google Maps][maps]
* [Google Places][places]
* [Activity Results API][results]




[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[androidx]: https://developer.android.com/jetpack/androidx
[arch]: https://developer.android.com/arch
[espresso]: https://google.github.io/android-testing-support-library/docs/espresso/
[retrofit]: http://square.github.io/retrofit
[glide]: https://github.com/bumptech/glide
[mockk]: https://github.com/mockk/mockk
[daggerhilt]: https://github.com/google/dagger
[kotlin]: https://developer.android.com/kotlin
[material]: https://github.com/material-components/material-components-android/
[android-q]: https://developer.android.com/preview
[dark-theme]: https://developer.android.com/preview/features/darktheme
[constraint-layout]: https://developer.android.com/reference/android/support/constraint/ConstraintLayout
[rxjava2]: https://github.com/ReactiveX/RxJava
[room]: https://developer.android.com/topic/libraries/architecture/room
[paging-3]:https://developer.android.com/topic/libraries/architecture/paging/v3-overview
[maps]:https://developers.google.com/maps/documentation/android-sdk/overview
[places]:https://developers.google.com/maps/documentation/places/android-sdk/autocomplete
[livedata]:https://developer.android.com/topic/libraries/architecture/livedata
[viewmodel]:https://developer.android.com/topic/libraries/architecture/viewmodel
[datastore]:https://developer.android.com/topic/libraries/architecture/datastore
[flow]:https://kotlinlang.org/docs/reference/coroutines/flow.html
[clean-arch]:https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
[nav]:https://developer.android.com/guide/navigation/navigation-getting-started
[viewbinding]:https://developer.android.com/topic/libraries/view-binding
[delegates]:https://kotlinlang.org/docs/reference/delegated-properties.html
[results]:https://developer.android.com/training/basics/intents/result

## Unit Tests
Some unit tests are under the `app/src/test` and `app/src/androidTest` directories, to run them, click the test package and right click and select `Run all tests in...`
