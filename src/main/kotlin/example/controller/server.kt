package example.controller

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import example.specifications.StateSpecification
import example.statemachine.StateMachine
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.jackson.*
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
            val stateMachine = StateMachine()
            install(ContentNegotiation) {
                jackson()
            }
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            put("/state") {
                try {
                    val request = call.receive<StateSpecification>()
                    if (stateMachine.update(request.state)) {
                        call.respond(HttpStatusCode.OK)
                    } else {
                        call.respond(HttpStatusCode.Conflict, "Cannot do transition to this state")
                    }
                } catch (e: InvalidFormatException) {
                    call.respond(HttpStatusCode.BadRequest, "Value is not a valid State")
                } catch (e: MissingKotlinParameterException) {
                    call.respond(HttpStatusCode.BadRequest, "StateSpecification is not valid")
                } catch (e: JsonParseException) {
                    call.respond(HttpStatusCode.BadRequest, "Body is not a valid json")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e)
                }
            }
        }
    }.start(wait = true)
}