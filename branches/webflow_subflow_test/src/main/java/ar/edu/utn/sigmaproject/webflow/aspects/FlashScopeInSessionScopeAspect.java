package ar.edu.utn.sigmaproject.webflow.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.core.collection.SharedAttributeMap;

/**
 * User: Gian Franco Zabarino
 * Date: 01/10/12
 * Time: 17:49
 */
@Aspect
public class FlashScopeInSessionScopeAspect {

	private static final String FLASH_SCOPE_ATTRIBUTE = "flashScope";

	@Pointcut("execution(org.springframework.webflow.core.collection.MutableAttributeMap org.springframework.webflow.engine.impl.FlowExecutionImpl.getFlashScope())")
	public void getFlashScopePointcut(){}

	@Around("getFlashScopePointcut()")
	public Object getFlashPointReplacement(ProceedingJoinPoint pjp) throws Throwable {
		SharedAttributeMap sessionMap = ExternalContextHolder.getExternalContext().getSessionMap();
		MutableAttributeMap flashScope;
		synchronized (sessionMap.getMutex()) {
			flashScope = (MutableAttributeMap) sessionMap.get(FLASH_SCOPE_ATTRIBUTE);
			if (flashScope == null) {
				flashScope = new LocalAttributeMap();
				sessionMap.put(FLASH_SCOPE_ATTRIBUTE, flashScope);
			}
		}
		return flashScope;
	}
}
