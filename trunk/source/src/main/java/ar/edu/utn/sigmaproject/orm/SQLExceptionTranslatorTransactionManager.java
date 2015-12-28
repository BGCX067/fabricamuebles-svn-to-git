package ar.edu.utn.sigmaproject.orm;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import java.sql.SQLException;

/**
 * User: Gian Franco Zabarino
 * Date: 26/10/12
 */
public class SQLExceptionTranslatorTransactionManager extends HibernateTransactionManager {

    private SQLExceptionTranslator sqlExceptionTranslator;

    public SQLExceptionTranslatorTransactionManager(SQLExceptionTranslator sqlExceptionTranslator) {
        this.sqlExceptionTranslator = sqlExceptionTranslator;
    }

    @Override
    protected DataAccessException convertHibernateAccessException(HibernateException ex) {
        DataAccessException dataAccessException;
        if (ex.getCause() instanceof SQLException) {
            dataAccessException = sqlExceptionTranslator.translate(null, null, (SQLException) ex.getCause());
        } else {
            dataAccessException = super.convertHibernateAccessException(ex);
        }
        return dataAccessException;
    }
}
