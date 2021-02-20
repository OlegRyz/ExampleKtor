package example.statemachine

import kotlinx.coroutines.delay

class StateMachine(private var internalState:State = State.Stopped) {

    private val validTransitions = setOf(
        State.Stopped to State.Started,
        State.Started to State.Waiting,
        State.Waiting to State.Executing,
        State.Executing to State.Waiting,
        State.Waiting to State.Stopping,
    )

    private val State.isValid
        get() = (internalState to this) in validTransitions

    fun getState() = internalState

    suspend fun update(state: State) = if (state.isValid) {
        pretendToWorkHard()
        internalState = state
        true
    } else {
        pretendToWorkEvenHarder()
        false
    }

    private suspend fun pretendToWorkHard() = delay(500)
    private suspend fun pretendToWorkEvenHarder() = delay(1000)
}