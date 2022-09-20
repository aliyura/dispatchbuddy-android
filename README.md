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

- Dependency injection (with Hilt)

- Google Material Design library

- Android architecture components to share ViewModels during configuration changes

- Edge To Edge Configuration

- Resource defaults

- colors.xml - colors for the entire project

- styles.xml - widget styles

- styles-text.xml - text appearances

- ViewModel - stores and manages UI-related data in a lifecycle conscious way

- Lifecycle- it performs an action when the life cycle state changes

- Retrofit - For networking

- Room - for offline caching

- ViewBinding - binding data to views

- [100% Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations


- [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))

- `App` module - this is the main module. It contains code that wires multiple modules together like dependency injection setup and fundamental application configuration like retrofit configuration
