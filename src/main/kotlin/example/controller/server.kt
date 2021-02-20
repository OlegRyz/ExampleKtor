package example.controller

import example.specifications.StateSpecification
import example.statemachine.StateMachine
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.html.*

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            +"Hello from Ktor"
        }
    }
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        routing {
            install(ContentNegotiation) {
                gson()
            }
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            put("/state") {
                val request = call.receive<StateSpecification>()
                StateMachine.update(request.state)
                call.respond(HttpStatusCode.OK)
            }
        }
    }.start(wait = true)
}