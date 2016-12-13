package org.eclipse.scout.widgets.ui.html;

import org.eclipse.scout.rt.ui.html.selenium.junit.AbstractSeleniumTest;
import org.eclipse.scout.rt.ui.html.selenium.junit.SeleniumAssert;
import org.eclipse.scout.rt.ui.html.selenium.util.SeleniumUtil;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox.ButtonFieldButton;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox.GetValueField;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox.LabelField;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
  public void testButtonLabelTextChanges() {
    WebElement labelInput = waitUntilInputFieldClickable(LabelField.class);
    SeleniumUtil.sendKeysDelayed(labelInput, "Scout");
    labelInput.sendKeys(Keys.TAB);
    WebElement toggleButton = findElement(ButtonFieldButton.class);
    WebElement spanInButton = toggleButton.findElement(By.tagName("span"));
    waitUntil(ExpectedConditions.textToBePresentInElement(spanInButton, "Scout"));
  }

}
