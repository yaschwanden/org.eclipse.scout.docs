package org.eclipse.scout.contacts.server.edu.testing01;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.eclipse.scout.contacts.server.ServerSession;
import org.eclipse.scout.contacts.server.person.PersonService;
import org.eclipse.scout.contacts.server.sql.DatabaseSetupService;
import org.eclipse.scout.contacts.server.sql.DerbySqlService;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for
 * <h3>{@link PersonService}</h3>
 * <p>
 * TODO Testing 1.1: add annotations
 */
@RunWith(ServerTestRunner.class)
@RunWithServerSession(ServerSession.class)
@RunWithSubject("default")
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

  //TODO Testing 1.2 Testing: Add Tests for other PersonService methods
  @Test
  public void testLoad() {
    PersonFormData personData = load("prs02");
    assertThat(personData.getFirstName().getValue(), is("Rabbit"));
    assertThat(personData.getLastName().getValue(), is("White"));

  }

  @Test
  public void testStore() {
    PersonFormData personData = load("prs01");
    assertThat(personData.getFirstName().getValue(), is("Alice"));
    assertThat(personData.getLastName().getValue(), nullValue());
    personData.getLastName().setValue("Wood");
    BEANS.get(IPersonService.class).store(personData);
    // load again
    personData = load("prs01");
    assertThat(personData.getFirstName().getValue(), is("Alice"));
    assertThat(personData.getLastName().getValue(), is("Wood"));

  }

  private PersonFormData load(String personId) {
    PersonFormData personData = new PersonFormData();
    personData.setPersonId(personId);
    return BEANS.get(IPersonService.class).load(personData);
  }

}
