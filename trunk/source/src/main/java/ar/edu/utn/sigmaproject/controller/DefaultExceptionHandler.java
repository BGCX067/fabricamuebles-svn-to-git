package ar.edu.utn.sigmaproject.controller;

import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.webflow.engine.FlowExecutionExceptionHandler;
import org.springframework.webflow.engine.RequestControlContext;
import org.springframework.webflow.engine.Transition;
import org.springframework.webflow.engine.support.DefaultTargetStateResolver;
import org.springframework.webflow.execution.FlowExecutionException;

/**
 * User: franco
 * Date: 14/09/12
 */
public class DefaultExceptionHandler implements FlowExecutionExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean canHandle(FlowExecutionException exception) {
        return true;
    }

    @Override
    public void handle(FlowExecutionException exception, RequestControlContext context) {
        Throwable cause = exception.getCause();
        MessageContext messageContext = context.getMessageContext();
        messageContext.clearMessages();
        if (cause instanceof OptimisticLockingFailureException) {
            messageContext.addMessage(new MessageBuilder()
                    .error()
                    .defaultText(messageSource.getMessage("admin.message.optimisticLockingFailure", null,
                            context.getExternalContext().getLocale()))
                    .build());
        } else if (cause instanceof DuplicateKeyException) {
            messageContext.addMessage(new MessageBuilder()
                    .error()
                    .defaultText(messageSource.getMessage("admin.message.duplicateKeyException", null,
                            context.getExternalContext().getLocale()))
                    .build());
        } else if (cause instanceof DataIntegrityViolationException) {
            messageContext.addMessage(new MessageBuilder()
                    .error()
                    .defaultText(messageSource.getMessage("admin.message.dataIntegrityViolationException", null,
                            context.getExternalContext().getLocale()))
                    .build());
        } else if (cause instanceof StaleObjectStateException) {
            messageContext.addMessage(new MessageBuilder()
                    .error()
                    .defaultText(messageSource.getMessage("admin.message.staleObjectState", null,
                            context.getExternalContext().getLocale()))
                    .build());
        } else {
            messageContext.addMessage(new MessageBuilder()
                    .error()
                    .defaultText(messageSource.getMessage("admin.message.genericErrorMessage", new Object[] {cause},
                            context.getExternalContext().getLocale()))
                    .build());
        }
        String originatingViewStateId = (String) context.getFlowScope().get("previousViewStateId");
        context.execute(new Transition(new DefaultTargetStateResolver(originatingViewStateId)));
        // context.getExternalContext().requestFlowExecutionRedirect();
    }
}
