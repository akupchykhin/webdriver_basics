import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.util.concurrent.TimeUnit;

public class YandexTest {

    private WebDriver driver;

        @BeforeMethod(description = "Start browser")
    private void BrowserStart() {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().maximize();
    }
        @BeforeMethod
        public void login(){
            driver.get("https://mail.yandex.by/");
            driver.findElement(By.xpath(".//*[@id='nb-1']/span/input")).sendKeys("yalogintest");
            driver.findElement(By.xpath(".//*[@id='nb-2']/span/input")).sendKeys("yalogintest123");
            driver.findElement(By.cssSelector(".nb-button._nb-action-button.nb-group-start")).click();
    }
        @AfterMethod
        public void driverClose(){
            driver.close();
    }

    public void createNewEmail(){
            driver.findElement(By.cssSelector(".mail-ComposeButton-Text")).click();
            driver.findElement(By.cssSelector(".js-compose-field.mail-Bubbles")).sendKeys("test2.asd@yandex.ru");
            driver.findElement(By.cssSelector(".mail-Compose-Field-Input-Controller.js-compose-field.js-editor-tabfocus-prev")).sendKeys("test email");
            driver.findElement(By.xpath(".//*[@id='cke_1_contents']/div")).sendKeys("test text here");
            String pressBoth = Keys.chord(Keys.CONTROL, "s");
            driver.findElement(By.xpath(".//*[@id='cke_1_contents']/div")).sendKeys(pressBoth);
            WebDriverWait wait1 = new WebDriverWait(driver, 12);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-key='view=compose-autosave-status']")));
            WebElement web = driver.findElement(By.xpath("//span[@data-key='view=compose-autosave-status']"));
            boolean a = web.isDisplayed();
            Assert.assertEquals(true, a);
    }

    public void saveEmailAsDraft(){
            driver.findElement(By.xpath(".//*[@id='cke_1_contents']/div")).click();
            String pressBoth = Keys.chord(Keys.CONTROL, "s");
            driver.findElement(By.xpath(".//*[@id='cke_1_contents']/div")).sendKeys(pressBoth);
            WebDriverWait wait1 = new WebDriverWait(driver, 12);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-key='view=compose-autosave-status']")));
            WebElement web = driver.findElement(By.xpath("//span[@data-key='view=compose-autosave-status']"));
            boolean a = web.isDisplayed();
            Assert.assertEquals(true, a);
    }
    public void goToDraft(){
        driver.get("https://mail.yandex.by/?ncrnd=3827&uid=512863941&login=yalogintest#draft");
    }

    public void sendTheEmail(){
        driver.findElement(By.xpath("//div[@role='textbox']")).click();
        String pressBoth1 = Keys.chord(Keys.CONTROL, Keys.ENTER);
        driver.findElement(By.xpath("//div[@role='textbox']")).sendKeys(pressBoth1);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(" js-mail-Notification-Content")));
        WebElement web = driver.findElement(By.className(" js-mail-Notification-Content"));
        boolean a = web.isDisplayed();
        Assert.assertEquals(true, a);
    }
    public void goToSent(){
        driver.get("https://mail.yandex.by/?uid=512863941&login=yalogintest#sent");
    }
    public void logoff(){
        driver.findElement(By.xpath("//div[@class='mail-User-Name']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-metric='Меню сервисов:Выход']"))).click();
    }
//    Tests============================================================================================================
        @Test(description = "Login page")
    public void yandexLoginPage() {
            WebElement web = driver.findElement(By.cssSelector(".mail-ComposeButton-Text"));
            boolean a = web.isDisplayed();
            Assert.assertEquals(true, a);
        }

        @Test(description = "Create a new mail")
    public void newMailCreationTest() {
            createNewEmail();
            WebElement web = driver.findElement(By.xpath("//span[@data-key='view=compose-autosave-status']"));
            boolean a = web.isDisplayed();
            Assert.assertEquals(true, a);
    }
        
        @Test(description = "Save the mail as a draft")
    public void saveEmailAsDraftTest() {
            createNewEmail();
            WebDriverWait wait1 = new WebDriverWait(driver, 12);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-key='view=compose-autosave-status']")));
            WebElement web = driver.findElement(By.xpath("//span[@data-key='view=compose-autosave-status']"));
            boolean a = web.isDisplayed();
            Assert.assertEquals(true, a);
        }
        
        @Test(description = "Verify, that the mail presents in ‘Drafts’ folder")
    public void emailInDraftVerifyTest() {
            createNewEmail();
            goToDraft();
            driver.findElement(By.cssSelector(".mail-MessageSnippet-Item.mail-MessageSnippet-Item_body.js-message-snippet-body")).click();
            String email = driver.findElement(By.className("mail-Bubble-Block_text")).getText();
            Assert.assertEquals(email, "test2.asd@yandex.ru");
        }

        @Test(description = " Send the mail")
    public void sendTheEmailTest() {
            createNewEmail();
            sendTheEmail();
            WebElement web = driver.findElement(By.className(" js-mail-Notification-Content"));
            boolean a = web.isDisplayed();
            Assert.assertEquals(true, a);
        }
        @Test(description = "Verify, that the mail disappeared from ‘Drafts’ folder.")
    public void verifyDraftIsEmptyTest(){
            createNewEmail();
            sendTheEmail();
            goToDraft();
            driver.findElements(By.cssSelector(".mail-MessageSnippet-Item.mail-MessageSnippet-Item_body.js-message-snippet-body")).size();
        }

        @Test(description = "Verify, that the mail is in ‘Sent’ folder.")
    public void emailIsInSentTest() {
            createNewEmail();
            sendTheEmail();
            goToSent();
            driver.findElement(By.cssSelector(".mail-MessageSnippet-Item.mail-MessageSnippet-Item_body.js-message-snippet-body")).click();
            WebElement web = driver.findElement(By.cssSelector(".mail-MessageSnippet-Item.mail-MessageSnippet-Item_body.js-message-snippet-body"));
            boolean c = web.isDisplayed();
            Assert.assertEquals(true, c);
        }

        @Test(description = "Log off")
    public void logOffTest(){
            goToSent();
            logoff();
    }
}