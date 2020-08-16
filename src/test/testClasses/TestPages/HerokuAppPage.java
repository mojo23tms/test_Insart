package TestPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
public class HerokuAppPage {

    private WebDriver driver;



    public HerokuAppPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;

    }

    @FindBy(linkText = "Entry Ad")
    public WebElement entryAd;

    @FindBy(linkText = "Checkboxes")
    public WebElement checkBoxes;

    @FindBy(linkText = "Form Authentication")
    public WebElement loginPage;

    @FindBy(linkText = "Dynamic Loading")
    public WebElement dynamicPage;

    @FindBy(css = "a[href='/dynamic_loading/1']")
    public WebElement dynamicPage1;

    @FindBy(linkText = "Add/Remove Elements")
    public WebElement addRemove;



}
