package org.eclipse.scout.contacts.server.edu.testing01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.scout.contacts.server.person.PersonService;
import org.eclipse.scout.contacts.server.sql.DatabaseSetupService;
import org.eclipse.scout.contacts.server.sql.DerbySqlService;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.rt.platform.BEANS;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for
 * <h3>{@link PersonService}</h3>
 * <p>
 * TODO 6.01 Testing: add annotations
 */
public class PersonServiceTest {
  private PersonFormData testPersonData = new PersonFormData();

  @BeforeClass
  public static void setupDatabase() {
    BEANS.get(DatabaseSetupService.class).createPersonTable();
  }

  @AfterClass
  public static void destroyDBConnections() {
    BEANS.get(DerbySqlService.class).dropDB();
    BEANS.get(DerbySqlService.class).destroySqlConnectionPool();
  }

  @Before
  public void before() {
    testPersonData.getFirstName().setValue("Homer");
    testPersonData.getLastName().setValue("Simpson");
    testPersonData.getCity().setValue("Springfield");
  }

  @Test
  public void testCreate() {
    PersonFormData createdPerson = BEANS.get(IPersonService.class).create(testPersonData);
    assertEquals("Homer", testPersonData.getFirstName().getValue());
    assertEquals("Simpson", testPersonData.getLastName().getValue());
    assertEquals("Springfield", testPersonData.getCity().getValue());
    assertNotNull(createdPerson.getPersonId());
  }

  //TODO 6.02 Testing: Add Tests for other PersonService methods

}
