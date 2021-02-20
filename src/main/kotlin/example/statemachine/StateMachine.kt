package example.statemachine

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

    fun update(state: State) = if (state.isValid) {
        internalState = state
        true
    } else {
        false
    }
}