package example.statemachine

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


internal class StateMachineTest {

    @Test
    fun testUpdate_WhenStopped_TransitsToStarted() {
        val testable = StateMachine(State.Stopped)

        val result = runBlocking { testable.update(State.Started) }

        assertTrue(result)
    }


    @Test
    fun testUpdate_WhenStopped_DoesNotTransitToStarting() {
        val testable = StateMachine(State.Stopped)

        val result = runBlocking { testable.update(State.Starting) }

        assertFalse(result)
    }

    @Test
    fun testUpdate_WhenWaiting_TransitsToExecuting() {
        val testable = StateMachine(State.Waiting)

        val result = runBlocking { testable.update(State.Executing) }

        assertTrue(result)
    }

    @Test
    fun testUpdate_WhenWaiting_TransitsToStopping() {
        val testable = StateMachine(State.Waiting)

        val result = runBlocking { testable.update(State.Stopping) }

        assertTrue(result)
    }

    @Test
    fun testUpdate_WhenStopping_DoesNotTransitToStopped() {
        val testable = StateMachine(State.Stopping)

        val result = runBlocking { testable.update(State.Stopped) }

        assertFalse(result)
    }

    @Test
    fun testUpdate_WhenStopping_DoesNotTransitToItself() {
        val testable = StateMachine(State.Stopping)

        val result = runBlocking { testable.update(State.Stopping) }

        assertFalse(result)
    }
}