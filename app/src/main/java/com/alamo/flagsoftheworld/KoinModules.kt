package com.alamo.flagsoftheworld

import org.koin.dsl.module

val testingKoinModules = module {
    // Defines a Singleton of Course
    single {
        Course()
    }

    // Defines a Factory (creates new instance every time it is needed)
    factory {
        Friend()
    }

    // Defines a Factory (creates new instance every time it is needed)
    factory {
        Student(get(), get())
    }

}
