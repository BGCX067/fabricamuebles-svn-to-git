package ar.edu.utn.sigmaproject.webflow.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * User: Gian Franco Zabarino
 * Date: 01/10/12
 * Time: 17:49
 */
@Aspect
public class DebugHibernateAspect {

    private static Log logger = LogFactory.getLog(DebugHibernateAspect.class);

	@Pointcut("execution(* org.hibernate..*(..))")
	public void getEveryMethod(){}

    static int level = 0;

	@Around("getEveryMethod()")
	public Object debugEveryMethod(ProceedingJoinPoint pjp) throws Throwable {
        StringBuilder sb = new StringBuilder(repeatPoint(level) + "starting " + pjp.getSignature() + " (");
        for (Object obj : pjp.getArgs()) {
            sb.append(obj == null ? "null" : obj.toString()).append(" , ");
        }
        sb.append(")");
        logger.debug(sb.toString());
        level ++;
        Object o = null;
        Exception ex = null;
        try {
            o = pjp.proceed();
        } catch(Exception e) {
            ex = e;
        }
        level--;
        if (ex == null) {
            logger.debug(repeatPoint(level) + "ending " + pjp.getSignature() + " and returning: " + (o == null ? "null" : o.toString()));
            return o;
        } else {
            logger.debug(repeatPoint(level) + "ending " + pjp.getSignature() + " and THROWING: " + ex.getClass().getName());
            throw ex;
        }
	}

    private String repeatPoint(int times) {
        if (times == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append('.');
        }
        return sb.toString();
    }
}
