package org.eclipse.scout.contacts.server.edu;

import org.eclipse.scout.contacts.server.edu.platform01.ClassInventoryTest;
import org.eclipse.scout.contacts.server.edu.platform02.BeanManagerTest;
import org.eclipse.scout.contacts.server.edu.platform03.ReplacingBeansTest;
import org.eclipse.scout.contacts.server.edu.platform04.Bean_PizzaServiceTest;
import org.eclipse.scout.contacts.server.edu.platform05.ConfigurationTest;
import org.eclipse.scout.contacts.server.edu.platform06.BeanDecorationTest;
import org.eclipse.scout.contacts.server.edu.testing01.PersonServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * <h3>{@link EduTestSuite}</h3>
 *
 * @author aho
 */
@RunWith(Suite.class)
@SuiteClasses({
    ClassInventoryTest.class,
    BeanManagerTest.class,
    ReplacingBeansTest.class,
    Bean_PizzaServiceTest.class,
    ConfigurationTest.class,
    BeanDecorationTest.class,
    PersonServiceTest.class

})
public class EduTestSuite {

}
