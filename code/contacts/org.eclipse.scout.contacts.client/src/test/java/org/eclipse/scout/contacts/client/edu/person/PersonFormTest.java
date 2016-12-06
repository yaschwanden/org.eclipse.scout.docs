package org.eclipse.scout.contacts.client.edu.person;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.eclipse.scout.contacts.client.person.PersonForm;
import org.eclipse.scout.contacts.shared.organization.IOrganizationLookupService;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

/**
 * Tests for
 * <h3>{@link PersonForm}</h3>
 */
//TODO Testing 2.1 Testing: Add testing annotations
//TODO AHO remove annotations
@RunWithSubject("anonymous")
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
public class PersonFormTest {

  @BeanMock
  private IPersonService personMockService;

  @BeanMock
  private IOrganizationLookupService organizationLookupService;

  @Before
  public void setup() {
    PersonFormData answer = new PersonFormData();
    answer.getFirstName().setValue("Bart");
    answer.getLastName().setValue("Simpson");
    when(personMockService.load(Matchers.any(PersonFormData.class))).thenReturn(answer);
    when(personMockService.store(Matchers.any(PersonFormData.class))).thenReturn(answer);
  }

  @Test
  public void testStartModify() {
    PersonForm form = new PersonForm();
    form.startModify();

    Assert.assertEquals("Bart", form.getFirstNameField().getValue());
    Assert.assertEquals("Simpson", form.getLastNameField().getValue());
    form.doCancel();
  }

  @Test
  public void testDateOfBirthValidation() {
    PersonForm form = new PersonForm();
    form.startModify();

    LocalDate localDateFuture = LocalDate.now().plusDays(5);
    Date date = Date.from(localDateFuture.atStartOfDay(ZoneId.systemDefault()).toInstant());
    form.getDateOfBirthField().setValue(date);
    Assert.assertEquals(IStatus.ERROR, form.getDateOfBirthField().getErrorStatus().getSeverity());

    form.doCancel();
  }
}
