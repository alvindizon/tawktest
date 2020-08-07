## tawktest
An app that allows users to search, view, and store notes about Github users. 


## Libraries/frameworks used
- [Android Jetpack](https://developer.android.com/jetpack)  
	- [Room](https://developer.android.com/jetpack/androidx/releases/room)  
	- [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)  
		- ViewModel  
		- LiveData
	- [Databinding](https://developer.android.com/jetpack/androidx/releases/databinding)  
	- [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
	- [Paging 3 library](https://developer.android.com/jetpack/androidx/releases/paging#3.0.0-alpha03)
- [Dagger](https://github.com/google/dagger)  
- [RxJava2](https://github.com/ReactiveX/RxJava)  
	- [RxKotlin](https://github.com/ReactiveX/RxKotlin)  
- [Retrofit](https://github.com/square/retrofit)  
- [Moshi](https://github.com/square/moshi)  
- [Glide](https://github.com/bumptech/glide)  
- [glide-transformations](https://github.com/wasabeef/glide-transformations)  
- [AndroidVeil](https://github.com/skydoves/AndroidVeil)  
- [JUnit4](https://github.com/junit-team/junit4)  
- [Mockito](https://github.com/mockito/mockito)

## Notes about the app
This app tries to follow the MVVM + Repository pattern as it is commonly known in Android. It makes use of the Android's [Paging 3 library](https://developer.android.com/jetpack/androidx/releases/paging#3.0.0-alpha03), which is still in alpha as of this writing, for processing and displaying the list of Github users. This app also makes use of [AndroidVeil](https://github.com/skydoves/AndroidVeil), in order to display an optimistic UI/shimmer skeleton UI instead of a progress bar for indicating progress, and  [glide-transformations](https://github.com/wasabeef/glide-transformations) for inverting image colors. Searching is only limited for users that were already loaded and cached by the adapter--I didn't get to implement filtering based on the notes saved on the DB. Plus I think a separate screen should be created instead for searching notes. I tried adding unit tests, but only got to add a unit test for the DAO/Database part, as well as some functionality of the ```ProfileViewModel```. In the future, I'll study how to test classes that make use of the Paging 3 library. 