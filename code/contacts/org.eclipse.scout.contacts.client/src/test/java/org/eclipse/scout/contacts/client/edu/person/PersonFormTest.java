package org.eclipse.scout.contacts.client.edu.person;

import static org.mockito.Mockito.when;

import org.eclipse.scout.contacts.client.person.PersonForm;
import org.eclipse.scout.contacts.client.person.PersonForm.MainBox.GeneralBox.DateOfBirthField;
import org.eclipse.scout.contacts.shared.organization.IOrganizationLookupService;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

/**
 * Tests for
 * <h3>{@link PersonForm}</h3>
 * <p>
 * TODO 6.03 Testing: Add testing annotations
 */
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
    when(personMockService.load(ArgumentMatchers.any(PersonFormData.class))).thenReturn(answer);
    when(personMockService.store(ArgumentMatchers.any(PersonFormData.class))).thenReturn(answer);
  }

  @Test
  public void testStartModify() {
    PersonForm form = new PersonForm();
    form.startModify();

    Assert.assertEquals("Bart", form.getFirstNameField().getValue());
    Assert.assertEquals("Simpson", form.getLastNameField().getValue());
    form.doCancel();
  }

  /**
   * TODO 6.04 Testing: create a test check if the validation on the {@link DateOfBirthField} fails in case of a date in
   * future.
   */
  @Test
  public void testDateOfBirthValidation() {
  }
}
