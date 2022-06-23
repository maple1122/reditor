package manuscript;

import base.LoginPortal;
import base.RMCommonMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author wufeng
 * @date 2022/6/23 9:41
 */
public class Audit extends LoginPortal {

    static WebDriver driver;

    //终审
    public static void finalAudit() throws InterruptedException {
        WebElement testScript = getTestData(driver);
        if (testScript != null) {
            testScript.findElement(By.xpath("div[1]/div/i")).click();//选中该数据
            driver.findElement(By.cssSelector("button.el-button.el-button--danger")).click();//点击审核
            Thread.sleep(500);
            driver.findElement(By.xpath("//form[@class='el-form']/div[1]/div/div/label[1]/span[1]/span")).click();//点击终审
            driver.findElement(By.xpath("//form[@class='el-form']/div[last()]/div/div/textarea")).sendKeys("autoTest-终审通过-" + System.currentTimeMillis());
            Thread.sleep(200);
            driver.findElement(By.cssSelector("button.el-button.el-button--primary")).click();
            System.out.println("~~~ finalAudit()，稿件终审，执行成功 ~~~");
        } else System.out.println("没有待审核状态的数据");
        Thread.sleep(3000);
    }

    //转审
    public static void transAudit() throws InterruptedException {
        WebElement testScript = getTestData(driver);
        if (testScript != null) {
            testScript.findElement(By.xpath("div[1]/div/i")).click();//选中该数据
            driver.findElement(By.cssSelector("button.el-button.el-button--danger")).click();//点击审核
            Thread.sleep(500);
            if (!RMCommonMethod.isJudgingElement(driver, By.xpath("//div[@class='el-select__tags']/span/span"))) {
                driver.findElement(By.xpath("//div[@class='el-select__tags']/span")).click();
                driver.findElement(By.xpath("//div[@class='el-select__tags']/input")).sendKeys(usernick);
                Thread.sleep(200);
                driver.findElement(By.xpath("//form[@class='el-form']/div[1]/label")).click();
                Thread.sleep(200);
            }
            driver.findElement(By.xpath("//form[@class='el-form']/div[last()]/div/div/textarea")).sendKeys("autoTest-转审-" + System.currentTimeMillis());
            driver.findElement(By.cssSelector("button.el-button.el-button--primary")).click();
            System.out.println("~~~ transAudit()，稿件转审，执行成功 ~~~");
        } else System.out.println("没有待审核状态的数据");
        Thread.sleep(3000);
    }

    //退回
    public static void returnScript() throws InterruptedException {
        WebElement testScript = getTestData(driver);
        if (testScript != null) {
            testScript.findElement(By.xpath("div[1]/div/i")).click();//选中该数据
            driver.findElement(By.cssSelector("button.el-button.el-button--danger")).click();//点击审核
            Thread.sleep(500);
            driver.findElement(By.xpath("//form[@class='el-form']/div[1]/div/div/label[3]/span[1]/span")).click();//点击退回
            driver.findElement(By.xpath("//form[@class='el-form']/div[last()]/div/div/textarea")).sendKeys("autoTest-稿件退回-" + System.currentTimeMillis());
            Thread.sleep(200);
            driver.findElement(By.cssSelector("button.el-button.el-button--primary")).click();
            System.out.println("~~~ returnScript()，稿件退回，执行成功 ~~~");
        } else System.out.println("没有待审核状态的数据");
        Thread.sleep(3000);
    }

    //校验是否有数据
    private static WebElement getTestData(WebDriver driver) {
        WebElement testData = null;

        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='content-list']/li"))) {
            testData = driver.findElement(By.xpath("//ul[@class='content-list']/li[1]"));
        }

        return testData;
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {//校验是已登录成功，是否已正常打开页面
                if (!RMCommonMethod.isJudgingElement(driver, By.className("text-p"))) {
                    if (RMCommonMethod.isJudgingElement(driver, By.className("loginBtn"))) driver = login();
                    driver.get(domain + "/reditor/static/index.html#/manuscript");
                    Thread.sleep(2000);
                } else break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
