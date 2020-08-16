package TestClasses;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import TestPages.HerokuAppPage;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testOne {

    HerokuAppPage herokuAppPage;
    WebDriver driver;

    public boolean isFileDownloaded(String downloadPath, String fileName) {
        File dir = new File("C:\\Users\\enchm\\Downloads");
        File[] dirContents = dir.listFiles();

        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    @Before
        public void setup() {
            System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            do {
                driver.get("http://the-internet.herokuapp.com/");
                driver.manage().timeouts().pageLoadTimeout(4, TimeUnit.MINUTES);
            }
            while (driver.getCurrentUrl().isBlank());
            herokuAppPage = new HerokuAppPage(driver);

        }

        @After
        public void shutdown() throws Exception {
        driver.quit();
        }


        @Test
        public void changeCheckBox() {
            herokuAppPage.checkBoxes.click();
            driver.findElement(By.xpath("//*[@class='example']/h3")).isDisplayed();
            WebElement element = driver.findElement(By.xpath("//*[@type='checkbox'][1]"));
            do {
                element.click();
            }
            while (element.isSelected());
        }

        @Test
        public void dynamicElement() throws InterruptedException {
            //Go to the Entry Ad page
            herokuAppPage.entryAd.click();

            WebElement explicitWait = (new WebDriverWait(driver,10))
                    .until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div.modal"))));
            //If Modal Window is displayed - find "Close" button and click it,
            //  then find "Reopen" button and click it, till it appears
            //If Modal Window is NOT displayed - find "Reopen" button and click it, till it appears
            if (driver.findElement(By.cssSelector("div.modal")).isDisplayed())
            {
                driver.findElement(By.cssSelector("div.modal-footer>p")).click();
                do { driver.findElement(By.cssSelector("a#restart-ad")).click();
                } while (driver.findElement(By.cssSelector("div.modal")).isDisplayed());
            }
            else if (!driver.findElement(By.cssSelector("div.modal")).isDisplayed())
            {
                do { driver.findElement(By.cssSelector("a#restart-ad")).click();
                } while (!driver.findElement(By.cssSelector("div.modal")).isDisplayed());
            }

            //Find modal window header
            driver.findElement(By.cssSelector("div.modal"));
            explicitWait = (new WebDriverWait(driver,10))
                    .until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div.modal"))));
            String modalText = driver.findElement(By.cssSelector("div.modal-title>h3")).getText();
            Assert.assertTrue(modalText.contains("THIS IS A MODAL WINDOW"));


            //TO DO add a test which goes to http://the-internet.herokuapp.com/entry_ad URL and closes the pop-up
            //then it clicks `click here.` link and checks `THIS IS A MODAL WINDOW` text is displayed on the page
        }

        @Test
        public void testInvalidLogin() {
            herokuAppPage.loginPage.click();
            WebElement element = driver.findElement(By.cssSelector("input#username"));
            element.sendKeys("someInvalidLogin");
            element = driver.findElement(By.cssSelector("input#password"));
            element.sendKeys("someInvalidPassword");
            driver.findElement(By.cssSelector("button[type='submit']>i")).click();
            element = driver.findElement(By.xpath("//*[@id='flash']"));
            element.isDisplayed();
            Assert.assertTrue(element.getText().contains("invalid"));

            //TO DO add a test which goes to http://the-internet.herokuapp.com/login URL and fills in incorrect details
            //then it clicks `Login` button
            //Here should be a check on error message is displayed on the page
        }

        @Test
        public void testValidLogin() {
            herokuAppPage.loginPage.click();
            WebElement element = driver.findElement(By.cssSelector("input#username"));
            element.sendKeys("tomsmith");
            element = driver.findElement(By.cssSelector("input#password"));
            element.sendKeys("SuperSecretPassword!");
            driver.findElement(By.cssSelector("button[type='submit']>i")).click();
            element = driver.findElement(By.xpath("//*[@id='flash']"));
            Assert.assertTrue(element.getText().contains("logged"));
            Assert.assertTrue(driver.findElement(By.cssSelector("div.example")).isDisplayed());

            //TO DO add a test which goes to http://the-internet.herokuapp.com/login URL and fills in correct details
            //then it clicks `Login` button
            //Here should be a check that user is logged in and a new content is displayed
        }

        @Test
        public void testDynamicLoadingElement() {
            herokuAppPage.dynamicPage.click();
            herokuAppPage.dynamicPage1.click();
            driver.findElement(By.cssSelector("div#start>button")).click();
            WebElement helloW = driver.findElement(By.cssSelector("div[style='']"));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            Assert.assertTrue(helloW.getText().contains("Hello"));


            //TO DO add a test which goes to  http://the-internet.herokuapp.com/dynamic_loading/1 URL and
            //clicks start button
            //Here should be a check that Hello World text is displayed
        }

        @Test
        public void testDownloadFile() {
            driver.get("http://the-internet.herokuapp.com/jqueryui/menu");
            WebElement element = driver.findElement(By.xpath("//*[@id='ui-id-2']"));
            element.click();
            element = driver.findElement(By.xpath("//*[@id='ui-id-4']"));
            element.click();
            element = driver.findElement(By.xpath("//*[@id='ui-id-8']"));
            element.click();
            Assert.assertTrue
                    (isFileDownloaded("C:\\Users\\enchm\\Downloads","menu.xls"));




            //TO DO add a test which goes to http://the-internet.herokuapp.com/jqueryui/menu URL and
            //goes to Enabled-Download-Excel and clicks it
            //verify that the file is downloaded
        }

        @Test
        public void addElements(){
        herokuAppPage.addRemove.click();
        WebElement element = driver.findElement(By.cssSelector("div.example>button"));
        element.click();
        WebElement button = driver.findElement(By.xpath("//div[@id='elements']/button[1]"));
        Assert.assertTrue(button.isEnabled());

        //Checking that element is added after clicking on "Add" button
        }

        @Test
        public void removeElements(){
        herokuAppPage.addRemove.click();
        WebElement element = driver.findElement(By.cssSelector("div.example>button"));
        element.click();
        WebElement button = driver.findElement(By.xpath("//div[@id='elements']/button[1]"));
        button.click();
        WebElement elements = driver.findElement(By.xpath("//div[@id='elements']"));
        List <WebElement> allButtons = elements.findElements(By.tagName("button"));
        Assert.assertTrue(allButtons.isEmpty());
    }
}