package com.alamo.flagsoftheworld

class Student(val course: Course, val friend: Friend) {
    fun beSmart() {
        course.study()
        friend.hangout()
    }
}

class Course() {
    fun study() {
        println("Avilan: We are studying2")
    }
}

class Friend() {
    fun hangout() {
        println("Avilan: We are hanging out2")
    }
}