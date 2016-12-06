package org.eclipse.scout.contacts.server.edu.platform06.fixture;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.scout.rt.platform.ApplicationScoped;

/**
 * <h3>{@link LoggedBean}</h3>
 *
 * @author aho
 */
@ApplicationScoped
@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD,
    java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LoggedBean {

}
