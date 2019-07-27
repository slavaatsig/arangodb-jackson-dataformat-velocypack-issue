package com.example.issue

import com.arangodb.ArangoCollectionAsync
import com.arangodb.ArangoDBAsync
import com.arangodb.VelocyJack
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking

data class Record(
        val _key: String,
        val _rev: String,
        val _id: String,
        val title: String,
        val answer: Int
)


fun main() {
    runBlocking {
        val working: ArangoCollectionAsync = ArangoDBAsync.Builder().user("root").password("root")
                .build()
                .db("playground").collection("test")

        val failing: ArangoCollectionAsync = ArangoDBAsync.Builder().user("root").password("root")
                .setSerializer(VelocyJack().apply { configure { it.registerModule(KotlinModule()) } })
                .build()
                .db("playground").collection("test")

        try {
            print("Without VelocyJack: ")
            println(working.getDocument("record", Any::class.java).await())

            print("With VelocyJack: ")
            println(failing.getDocument("record", Record::class.java).await())
        } catch (error: Exception) {
            println(error)
        }

        /**
         * Expect something like:
         *
         * Without VelocyJack: {answer=42, _rev=_ZBJJ9SG--_, _id=test/record, _key=record, title=The ultimate answer is}
         * With VelocyJack: com.arangodb.ArangoDBException: java.util.concurrent.ExecutionException:
         * com.arangodb.ArangoDBException: Response Code: 400
         */
    }
}
