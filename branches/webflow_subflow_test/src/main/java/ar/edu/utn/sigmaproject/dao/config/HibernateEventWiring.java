package ar.edu.utn.sigmaproject.dao.config;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HibernateEventWiring {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private HibernateListener hibernateListener;
	
	@PostConstruct
	public void registerListeners() {
		EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
	            EventListenerRegistry.class);
		registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(hibernateListener);
	    registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(hibernateListener);
	}
}
