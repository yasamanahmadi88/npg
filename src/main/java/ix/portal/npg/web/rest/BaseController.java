package ix.portal.npg.web.rest;

import org.springframework.web.server.ResponseStatusException;

import ix.portal.npg.web.rest.errors.BadRequestAlertException;

import ix.portal.npg.exception.DBException;
import ix.portal.npg.exception.Exceptions;
import ix.portal.npg.exception.IxssException;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Developer: Hossein Sadeghi (hsadeghi78@gmail.com)
 * Project : admin-console- 8/1/20
 */
public class BaseController {

    private final Logger log = LoggerFactory.getLogger(BaseController.class);

    public void handleException(Exception e) throws IxssException, DBException {
        if (e instanceof BadRequestAlertException) {
            throw (BadRequestAlertException) e;
        }

        if (e instanceof ResponseStatusException) {
            throw (ResponseStatusException) e;
        }
        log.debug("Handle Exception: {}", e.getMessage());
        // todo Must handle Following Exceptions in this file
        //  java.sql.SQLException;
        //  java.sql.SQLIntegrityConstraintViolationException;
        //  jakarta.persistence.EntityExistsException;
        //  jakarta.persistence.PersistenceException;
        //  jakarta.transaction.RollbackException;
        //  org.hibernate.HibernateException;
        //  org.hibernate.exception.ConstraintViolationException;
        //  org.springframework.dao.DataIntegrityViolationException;
        //  com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
        //  URISyntaxException

        if (e instanceof IxssException) {
            throw (IxssException) e;
        } else if (e instanceof org.springframework.dao.DataIntegrityViolationException) {
            Arrays
                .stream(Exceptions.values())
                .forEach(
                    exception -> {
                        if (e.getMessage().contains(exception.UKNAME())) {
                            throw new DBException(exception.CODE(), exception.MESSAGE(), e);
                        }
                    }
                );
            if (e.getCause().getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                if (e.getCause().getCause().getMessage().contains("child record found")) {
                    throw new DBException("object.have.child", "For This Entity Child Record Found", e);
                }
            }
            throw new DBException(e.getMessage(), e.getMessage(), e);
        } else if (e instanceof org.hibernate.exception.ConstraintViolationException) {
            throw new DBException(e.getMessage(), e.getMessage(), e);
        } else if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
            Arrays
                .stream(Exceptions.values())
                .forEach(
                    exception -> {
                        if (e.getMessage().contains(exception.UKNAME())) {
                            throw new DBException(exception.CODE(), exception.MESSAGE(), e);
                        }
                    }
                );
            throw new DBException(e.getMessage(), e.getMessage(), e);
        } else {
            throw new IxssException(e, e.getMessage(), e.getMessage());
        }
    }
}


