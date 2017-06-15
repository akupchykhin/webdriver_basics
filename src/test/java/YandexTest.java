import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import java.util.concurrent.TimeUnit;

public class YandexTest {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("https://mail.yandex.by/");
        driver.findElement(By.xpath(".//*[@id='nb-1']/span/input")).sendKeys("yalogintest");
        driver.findElement(By.xpath(".//*[@id='nb-2']/span/input")).sendKeys("yalogintest123");
        driver.findElement(By.cssSelector(".nb-button._nb-action-button.nb-group-start")).click();
        WebElement web = driver.findElement(By.cssSelector(".mail-ComposeButton-Text"));
        boolean a = web.isDisplayed();
        Assert.assertEquals(true, a);
        driver.findElement(By.cssSelector(".mail-ComposeButton-Text")).click();
        driver.findElement(By.cssSelector(".js-compose-field.mail-Bubbles")).sendKeys("test2.asd@yandex.ru");
        driver.findElement(By.cssSelector(".mail-Compose-Field-Input-Controller.js-compose-field.js-editor-tabfocus-prev")).sendKeys("test email");
        driver.findElement(By.xpath(".//*[@id='cke_1_contents']/div")).sendKeys("test text here");
        driver.findElement(By.xpath(".//*[@id='cke_1_contents']/div")).click();

        String pressBoth = Keys.chord(Keys.CONTROL, "s");
        driver.findElement(By.xpath(".//*[@id='cke_1_contents']/div")).sendKeys(pressBoth);

        WebDriverWait wait1 = new WebDriverWait(driver, 11);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-key='view=compose-autosave-status']")));
        driver.get("https://mail.yandex.by/?ncrnd=3827&uid=512863941&login=yalogintest#draft");
        driver.findElement(By.cssSelector(".mail-MessageSnippet-Item.mail-MessageSnippet-Item_body.js-message-snippet-body")).click();
        String email = driver.findElement(By.className("mail-Bubble-Block_text")).getText();
        Assert.assertEquals(email, "test2.asd@yandex.ru");

//        String subj = driver.findElement(By.cssSelector(".mail-Compose-Field-Input-Controller.js-compose-field.js-editor-tabfocus-prev")).getText();
//        Assert.assertEquals(subj, "test email");
//        String body = driver.findElement(By.className("cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders")).getText();
//        Assert.assertEquals(email, "test text here");
          //не смог подобрать локаторы для проверки верности subject и самого тела письма :(

        driver.findElement(By.xpath("//div[@role='textbox']")).click();
        String pressBoth1 = Keys.chord(Keys.CONTROL, Keys.ENTER);
        driver.findElement(By.xpath("//div[@role='textbox']")).sendKeys(pressBoth1);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.className(" js-mail-Notification-Content")));

        driver.findElements(By.cssSelector(".mail-MessageSnippet-Item.mail-MessageSnippet-Item_body.js-message-snippet-body")).size();
        driver.get("https://mail.yandex.by/?uid=512863941&login=yalogintest#sent");
        driver.findElement(By.cssSelector(".mail-MessageSnippet-Item.mail-MessageSnippet-Item_body.js-message-snippet-body")).click();
        driver.findElement(By.xpath("//div[@class='mail-User-Name']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-metric='Меню сервисов:Выход']"))).click();
        driver.close();
        System.out.println("Done");
    }
}