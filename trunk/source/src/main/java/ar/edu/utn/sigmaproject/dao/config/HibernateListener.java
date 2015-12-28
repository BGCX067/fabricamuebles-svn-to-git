package ar.edu.utn.sigmaproject.dao.config;

import java.util.Arrays;
import java.util.Date;

import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ar.edu.utn.sigmaproject.domain.IdentificableEntity;

@Component
public class HibernateListener implements PreInsertEventListener, PreUpdateEventListener {

	private static final long serialVersionUID = -6346132015000395354L;
	
	private final static Logger logger = LoggerFactory.getLogger(HibernateListener.class);

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		if (event.getEntity() instanceof IdentificableEntity) {
			IdentificableEntity entity = (IdentificableEntity) event.getEntity();
			String[] propertyNames = event.getPersister().getPropertyNames();
            Object[] state = event.getState();
            Date now = new Date();
            // inserts
			setValue(state, propertyNames, "dateCreated", now, entity);
            setValue(state, propertyNames, "lastModified", now, entity);
            entity.setDateCreated(now);
            entity.setLastModified(now);
		}
		return false;
	}

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		if (event.getEntity() instanceof IdentificableEntity) {
			IdentificableEntity entity = (IdentificableEntity) event.getEntity();
			String[] propertyNames = event.getPersister().getPropertyNames();
            Object[] state = event.getState();
            Date now = new Date();
            // inserts
			setValue(state, propertyNames, "dateCreated", entity.getDateCreated(), entity);
			// updates
            setValue(state, propertyNames, "lastModified", now, entity);
            entity.setLastModified(now);
		}
		return false;
	}
	
	void setValue(Object[] currentState, String[] propertyNames,
			String propertyToSet, Object value, Object entity) {
		int index = -1;
		for (int i = 0; i < propertyNames.length; i++) {
			if (propertyNames[i].equals(propertyToSet)) {
				index = i;
				break;
			}
		}
		if (index >= 0) {
			currentState[index] = value;
		} else {
			logger.error("Field '" + propertyToSet + "' not found on entity '"
					+ entity.getClass().getName() + "'.");
		}
	}
}