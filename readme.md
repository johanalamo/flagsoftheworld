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
    * grid/vertical
  * Add a search input
  * Add total field
  * Add sticky header for continents
  * Add information about scrolling
    * example: 32/250
    * show bar scroll
    * see video 69 del curso de aristidevs en udemy
* Architectural
  * Add koin for dependency injection
  * put every UI element to run in Main thread (even viewmodels)
  * put every non-ui element to run in a default thread with withConext function,
    * review viewmodels and UseCases
a
Structure - Information Architecture


epa epa aqui ***********************************************************************
epa epa aqui ***********************************************************************
epa epa aqui ***********************************************************************
epa epa aqui ***********************************************************************
  tienes que hacer el diseño completo con wireframe (a mano) como lo indica el video 
35 del curso de diseño grafico

Figma te permite hacer wireframes

ten en cuenta que cada pantalla debe informar lo profundo que est'a en la navegacion, de donde viene,
hacia donde va, donde esta y que puede hacer ahi,
el usuario debe poder predecir que va a pasar despues de cada accion
labeling y navigation son muy importantes

surface - alignment
  revisar alineancio de los elementos en la pantalla principal tambien, es un principio del surface...
  tambien el nomre del contienente esta centrado, creo que quedaria mejor alineado a la izquierda
surface - proximity
  el titulo con el nombre del continene tiene la misma proximidad para arriba y para abajo, lo que
  se percibe como si fuese lo mismo.. hay que cambiarlo.


epa epa aqui ***********************************************************************
epa epa aqui ***********************************************************************
epa epa aqui ***********************************************************************
epa epa aqui ***********************************************************************



* Main Screen
  * Home
    * Country list
      * Data: continent, country name, country code, small flag
      * Functionality: select/unselect favorite, search/filter by name/code
      * Navigates to:
        * Country details
          * Data: name, oficial name, official code, currencies, capital, phone suffix,
            region, subregion, languages, latLng, borders, area, flag, map, population, gini, fifa
            timezone, coatOfArms
          * Functionality: select/unselect as a favorite
          * Navigates to:
              * Flag
              * CoatOfArms
              * Map
              * Border countries
  * Map (tentative, not designed yet)
  * Menu (tentative, not designed yet)


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
////country flags: https://countryflagsapi.com/   --> it seems it does not work anymore
//
//y este que es la usada por restcountries.com para las flags
//https://flagcdn.com/
// https://flagcdn.com/en/codes.json
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


*********  JIRA - DASHBOARD ************************************************************************
Current one:

010:     missed functionality:
Message when there is no network
add/remove to/from favorites


010: navigate back (el boton de abajo de android), no actualiza los favorites en la pantalla principal si este fue cambiado,
ej: entrar a Angola, presionar favorites, presionar atras (boton android), ver que en la principal no se activo.


El mensaje de no connection no esta funcionando...
     entrar al list
     desconectar a internet
     entrar a un pais cualquiera
     muestra el anterior y no da el mensaje de error de internet.
    revisar bien los mensajes de errores tanto en los viewmodels como en los usecases
        cuando se entra al details sin conexion, da el mensaje de error, pero si se reconecta y se presiona Retry, no funciona (al parecer no tiene el codigo del pais)

NEXT:

006: cambiar a Koin

009:
inject: countryDb, driver, country Database ,both viewmodels, imageLoader(see DotaInfo project): MainActivity
inject: all usecases in the viewmodels.




************************   BACKLOG **************************************

008: image loader does not work when a scroll is set in the column
010: missing fields: gini, currencies, languages, map, jcoat of arms
003: bottomBar does not show any answer when user tap on it

001: usar el snackbar hostState, en vez de crear uno nuevo:
hacer click muchas veces en un mismo favorite (add/ remove), y ver que aparecen infinitamente los mensajes
idea:
val scaffoldState = rememberBottomSheetScaffoldState()
scaffoldState.snackbarHostState.showSnackbar()

and check scaffoldState.snackbarHostState.currentSnackbarData because it contains info about the snackbar being shown

007: cambiar los useCase a suspend operator invoke()

008: Classe Message esta repetida en uicountrylist y uicountrydetails
009: funciones ShowSnackbar y ShowTemporalSnackbar estan repetidas en uicountrylist y uicountrydetails
010: snackbarHosting no esta bien manejado, cuando se activa y desactiva internet, los mensajes de error de conexion
     parecieran aparecer de forma aleatoria y en las pantallas que no son.

*/



-->