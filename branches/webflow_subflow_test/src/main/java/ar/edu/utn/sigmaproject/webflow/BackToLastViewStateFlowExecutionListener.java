package ar.edu.utn.sigmaproject.webflow;

import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.execution.FlowExecutionListenerAdapter;
import org.springframework.webflow.execution.FlowSession;
import org.springframework.webflow.execution.RequestContext;

import java.util.LinkedList;

public class BackToLastViewStateFlowExecutionListener
        extends FlowExecutionListenerAdapter {

    private String viewStatesName = "GLOBAL_BACK_LISTENER_VIEW_STATES";
    private String backEventId = "back";

    public void setViewStatesName(final String viewStatesName) {
        this.viewStatesName = viewStatesName;
    }

    public void setBackEventId(final String backEventId) {
        this.backEventId = backEventId;
    }

    @Override
    public void sessionStarted(final RequestContext context,
                               final FlowSession session) {
        session.getScope().put(viewStatesName, new LinkedList<String>());
    }

    private LinkedList<String> getViewStates(final RequestContext context) {
        @SuppressWarnings("unchecked") final LinkedList<String> viewStates = (LinkedList<String>) context.getFlowScope().get(viewStatesName);
        if (viewStates == null) throw new IllegalStateException("viewStates is null");
        return viewStates;
    }

    @Override
    public void stateEntered(final RequestContext context,
                             final StateDefinition previousState,
                             final StateDefinition state) {
// If there's no previous ViewState, there's nothing we can do...
        if (!(previousState instanceof ViewState))
            return;

// If we're entering the same state (due to a reload or
// binding error), ignore that...
        if (previousState.getId().equals(state.getId()))
            return;

        final LinkedList<String> viewStates = getViewStates(context);

        final String previousStateId;

        if (context.getCurrentEvent().getId().equals(backEventId)) {
            viewStates.removeLast();
            previousStateId = viewStates.isEmpty() ? "" : viewStates.getLast();
        } else {
            previousStateId = previousState.getId();
            viewStates.add(previousStateId);
        }

        context.getFlowScope().put("previousViewStateId", previousStateId);
    }

}