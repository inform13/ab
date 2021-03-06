package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void initContactCreation() {
    wd.findElement(By.linkText("add new")).click();
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirst_name());
    type(By.name("lastname"), contactData.getSecond_name());
    type(By.name("email"), contactData.getEmail());
    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    }
      else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
}

  public void submitContactCreation() {
    wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
  }

  public void returnToContactPage() {
      wd.findElement(By.linkText("home page")).click();
  }

  public void initContactModification() {
    click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void selectContact() {
    click(By.name("selected[]"));
    //if (!wd.findElement(By.id("22")).isSelected()) {
     // wd.findElement(By.id("22")).click();

  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("Selected()"));
  }

  public void  createContact (ContactData contact, boolean b) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    returnToContactPage();
  }

  public boolean isThereAGroup() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("td.center"));
    for (WebElement element : elements) {
      String name = element.getText();
      ContactData contact = new ContactData (name, null, null, null);
      contacts.add(contact);
    }
    return contacts;
  }
}
