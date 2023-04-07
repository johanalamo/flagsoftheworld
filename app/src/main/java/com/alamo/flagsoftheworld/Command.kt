package com.alamo.flagsoftheworld

import androidx.compose.runtime.MutableState
import com.alamo.flagsoftheworld.model.ImageProperty

sealed class Command {
    abstract fun execute()
    class CommandEnlarge(private val imageProperty: MutableState<ImageProperty>): Command() {
        override fun execute() {
            imageProperty.value = imageProperty.value.copy(height = imageProperty.value.height + 10)
        }
    }
    class CommandReduce(private val imageProperty: MutableState<ImageProperty>): Command() {
        override fun execute() {
            imageProperty.value = imageProperty.value.copy(height = imageProperty.value.height - 10)
        }
    }
    class CommandRotateCounterClockwise(private val imageProperty: MutableState<ImageProperty>): Command() {
        override fun execute() {
            imageProperty.value = imageProperty.value.copy(rotate = imageProperty.value.rotate - 10)
        }
    }
    class CommandRotateClockwise(private val imageProperty: MutableState<ImageProperty>): Command() {
        override fun execute() {
            imageProperty.value = imageProperty.value.copy(rotate = imageProperty.value.rotate + 10)
        }
    }


}

