package ar.edu.utn.sigmaproject.service;

import ar.edu.utn.sigmaproject.dao.StateDao;
import ar.edu.utn.sigmaproject.domain.City;
import ar.edu.utn.sigmaproject.domain.State;
import ar.edu.utn.sigmaproject.domain.filter.DefaultEntityFilter;
import ar.edu.utn.sigmaproject.domain.filter.SortCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Gian Franco Zabarino
 * Date: 04/09/12
 */
@Service
public class AddressService {

    @Autowired
    private StateDao stateDao;

    @Transactional(readOnly = true)
    public State findStateByUid(String entityUid) {
        return stateDao.getByUid(entityUid);
    }

    @Transactional(readOnly = true)
    public City findCityByUid(String entityUid) {
        return stateDao.findCityByUid(entityUid);
    }

    @Transactional(readOnly = true)
    public List<State> findAllStates() {
        DefaultEntityFilter<State> filter = new DefaultEntityFilter<State>();
        filter.addSort(new SortCriteria("name"));
        return stateDao.getList(filter);
    }

    @Transactional(readOnly = true)
    public List<City> findStateCities(State state) {
        return stateDao.findStateCities(state);
    }

    @Transactional
    public void saveCity(City editedCity) {
        if (editedCity.getId() == null) {
            // Add the city to the state collection if new
            editedCity.getState().getCities().add(editedCity);
        }
        stateDao.saveCity(editedCity);
    }

    @Transactional
    public void saveState(State editedState) {
        stateDao.save(editedState);
    }

    @Transactional(readOnly = true)
    public void markStateForUpdate(State state) {
        stateDao.evict(state);
    }

    @Transactional(readOnly = true)
    public void markCityForUpdate(City city) {
        stateDao.evictCity(city);
    }
}
