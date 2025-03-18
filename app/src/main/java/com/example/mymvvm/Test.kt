package com.example.mymvvm

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking


//
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




fun generateEvenNumbers(): Flow<Int> = flow {
    for (i in 2..40 step 2) {
        emit(i)
        delay(1000) // Emit every second
    }
}

fun main() = runBlocking {
    generateEvenNumbers()
        .take(10)
        .collect {
            println(it)
        }
}
