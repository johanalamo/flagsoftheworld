# Flags of the world

This is a Application built for learning, implementing the newest developing tools and technologies
for android

## Tecnologies
* Jetpack compose
* Kotlin flows
* Kotlin Coroutines
* SqlDelight
* Retrofit
  
## Architecture
* MVVM 

## Next steps
* UI:
  * Design and implement the detail view
  * Add a filter dialog
  * Add a search input
* Architectural
  * Add koin for dependency injection
  * put every UI element to run in Main thread (even viewmodels)
  * put every non-ui element to run in a default thread with withConext function,
    * review viewmodels and UseCases

<!-- 
//decidido.. a usar dos
//info about countries: https://restcountries.com/

// TODO: implement this to check the internet connection
// val Context.isConnected: Boolean
//    get() {
//        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
//            .activeNetworkInfo?.isConnected == true
//    }
//  MUCH BETTER WOULD BE TO APPLY THIS:
//      https://developer.android.com/training/monitoring-device-state/connectivity-status-type
//TODO:
//agregar un todo que es un test para cuando tenga que manejar cosas de tiempo
//por ejemplo, cuando guarde la lista de paises en cache, la debo actuaizar en
//memoria solo si pasaron tres horas, de lo contrario utilizo el cache,
//y eso es un test para probar cosas del tiempo que aprendi en el curso de
//udemy de paypal


// TODO: try with mutation tests, and review the coverage percentage
//2. para las banderas (hay dos)
//esta que encontre
////country flags: https://countryflagsapi.com/
//
//y este que es la usada por restcountries.com para las flags
//https://flagcdn.com/
//tambien tienen el escudo, y referencia a google maps y openstree

//Think in how to use all of them above in the same project making sense



/* TODO:

add filter dialog (favorites)
add searching dialog

filter dialog example

+-------------------------------------+
| what to show
|    show all
|    show favorites
|show as
|    list
|    grid
|
|
|
+-------------------------------------+



learn

koin
gradle
readme.md
testing
testing jetpack compose
koin dependency injection

*/

-->