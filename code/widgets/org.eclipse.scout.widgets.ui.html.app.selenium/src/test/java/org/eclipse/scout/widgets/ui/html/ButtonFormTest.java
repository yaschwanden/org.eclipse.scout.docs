package org.eclipse.scout.widgets.ui.html;

import org.eclipse.scout.rt.ui.html.selenium.junit.AbstractSeleniumTest;
import org.eclipse.scout.rt.ui.html.selenium.junit.SeleniumAssert;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox.ButtonFieldButton;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox.GetValueField;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

/**
 * Selenium test for ButtonForm.
 */
public class ButtonFormTest extends AbstractSeleniumTest {

  @Before
  public void setUp() {
    WidgetsSeleniumUtil.goToSimpleWidgetsForm(this, "Button");
  }

  @Test
  public void testToggleButton() {
    WebElement toggleButton = waitUntilElementClickable(ButtonFieldButton.class);
    WebElement getValueField = findInputField(GetValueField.class);
    toggleButton.click();
    SeleniumAssert.assertInputFieldValue(this, getValueField, "true");
  }

  @Test
  public void testLabelTextChanges() {
    /* TODO: Selenium Test - write a Selenium test which types 'Scout' into the 'Label' string field,
     * tabs out of it and asserts that the label of the button changes. Note: since this is a client
     * server application we cannot assume that the change happens immediately, it is not a synchronous
     * operation and we may have network latency while the test is executed.
     *
     * A few hints:
     * - Use Dev-Tools from Chrome to find the model class name of the label field
     * - Use the sendKeysDelayed method from SeleniumUtil to simulate human (=slow) typing
     * - Use WebElement#sendKeys(Keys.TAB) to tab out from the label field
     * - Check how the DOM of the button looks like, to write a correct assertion for the button label text
     * - Check the testToggleButton method for how to find the toggle button
     * - Use findElement() on the toggle button to find the SPAN element inside the button field
     * - Use waitUntil(ExpectedConditions.textToBePresentInElement()) to wait for the label text change
     * - Use debugging / breakpoints to halt test execution, if you need time to inspect things in the browser
     *   or your SeleniumUtil.pause(n) to wait for a few seconds (at the end of your test)
     */
  }

}
