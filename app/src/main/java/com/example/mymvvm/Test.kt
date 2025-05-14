package com.example.mymvvm

import androidx.core.app.PendingIntentCompat.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking



//lap1-task1
//fun getNumbers() = flow<Int> {
//    for (i in 1..10) {
//        delay(1000)
//        emit(i)
//    }
//}
//
//suspend fun main(): Unit = coroutineScope {
//    getNumbers()
//        .filter { item -> item > 5 }
//        .map { item -> item * 2 }
//        .collect { println("The number is: $it") }
//}

//--------------------------------------------------------------------------------------------//
//
//fun produceFruits() = GlobalScope.produce<String> {
//    val fruitsArray = listOf("Apple", "Banana", "Pear", "Grapes")
//    for (fruit in fruitsArray) {
//        send(fruit)
//        if (fruit == "Pear") {
//            close()
//        }
//    }
//}
//
//fun main() = runBlocking {
//    val fruits = produceFruits()
//    fruits.consumeEach { println(it) }
//}

//--------------------------------------------------------------------------------------------//

// Alternative approach with manual channel closing
//fun main() = runBlocking {
//    val channel = Channel<String>()
//
//    GlobalScope.launch {
//        val fruitsArray = listOf("Apple", "Banana", "Pear", "Grapes")
//        for (fruit in fruitsArray) {
//            channel.send(fruit)
//            if (fruit == "Pear") {
//                channel.close()
//            }
//        }
//    }
//
//    while (!channel.isClosedForReceive) {
//        val fruit = channel.receive()
//        println(fruit)
//    }
//}

//--------------------------------------------------------------------------------------------//

//Lap1_task2
//fun generateEvenNumbers(): Flow<Int> = flow {
//    for (i in 2..40 step 2) {
//        emit(i)
//        delay(1000) // Emit every second
//    }
//}
//
//fun main() = runBlocking {
//    generateEvenNumbers()
//        .take(10)
//        .collect {
//            println(it)
//        }
//}
//--------------------------------------------------------------------------------------------//

//lap2-task1
//suspend fun main() :Unit= coroutineScope{
//    val coroutineScope = CoroutineScope(Dispatchers.IO)
//    val stateflow = MutableSharedFlow<Int>()
//        launch {
//        stateflow.collect{
//            println("first :$it")
//        }
//    }
//    launch {
//        stateflow.collect{
//            println("Second: $it")
//        }
//    }
//
//    stateflow.emit(1)
//    stateflow.emit(2)
//    stateflow.emit(3)
//    stateflow.emit(4)
//}
//--------------------------------------------------------------------------------------------//

// suspend fun main() :Unit= coroutineScope {
//   val stateflow = MutableStateFlow<String>(" ")
//    launch {
//        stateflow.collect{
//            println(it)
//        }
//    }
//
//    stateflow.emit("1")
//    stateflow.emit("2")
//    stateflow.emit("3")
//    stateflow.emit("4")
//    delay(1000)
//    stateflow.emit("5")
//    stateflow.emit("6")
//    delay(12)
//    stateflow.emit("7")
//    stateflow.emit("8")
//
//
//}