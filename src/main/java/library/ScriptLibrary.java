package library;

import base.LoginPortal;
import base.RMCommonMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author wufeng
 * @date 2022/6/23 11:27
 */
public class ScriptLibrary extends LoginPortal {
    static WebDriver driver;

    //签发稿件
    public static void push() throws InterruptedException {
        WebElement script = search();
        if (script != null) {
            script.findElement(By.xpath("div[@class='item-content']/div/div[@class='item-btns']/div[1]/button[1]")).click();
            Thread.sleep(500);
            boolean push = RMCommonMethod.treeIframe(driver);//签发频道图层操作
            if (push) System.out.println("~~~ push()，稿件签发，执行成功 ~~~");
            else System.out.println("稿件签发失败");
        } else System.out.println("没有autoTest测试数据");
        Thread.sleep(3000);
    }

    //退稿
    public static void returnScript() throws InterruptedException {
        WebElement script = search();
        if (script != null) {
            script.findElement(By.xpath("div[@class='item-content']/div/div[@class='item-btns']/div[1]/button[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.className("el-textarea__inner")).sendKeys("autoTest-这是退稿意见-" + System.currentTimeMillis());
            RMCommonMethod.getButtonDiv(driver).findElement(By.xpath("div/div[@class='el-dialog__footer']/span/button[1]")).click();
            System.out.println("~~~ returnScript()，稿件退回，执行成功 ~~~");
        } else System.out.println("没有autoTest测试数据");
        Thread.sleep(3000);
    }

    //取消终审
    public static void cancel() throws InterruptedException {
        WebElement script = search();
        if (script != null) {
            script.findElement(By.xpath("div[@class='item-content']/div/div[@class='item-btns']/div[1]/button[3]")).click();
            Thread.sleep(500);
            boolean selected = false;
            //先判断常用审核人中是否有自动化测试账号，有则直接使用
            if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='common-list']/li"))) {
                List<WebElement> li = driver.findElements(By.xpath("//ul[@class='common-list']/li"));
                for (int i = 0; i <= li.size(); i++) {
                    if (li.get(i).getText().contains("wf")) {
                        li.get(i).click();
                        selected = true;
                        Thread.sleep(200);
                        break;
                    }
                }
            }
            if (!selected) {
                driver.findElement(By.className("el-select__input")).click();
                driver.findElement(By.className("el-select__input")).sendKeys(usernick);
                Thread.sleep(200);
                if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='el-select-group']/li"))) {
                    driver.findElement(By.xpath("//ul[@class='el-select-group']/li[1]")).click();
                    selected = true;
                }
                driver.findElement(By.xpath("//ul[@class='el-form']/div[1]/label")).click();
                Thread.sleep(200);
            }
            if (selected) {
                RMCommonMethod.getButtonDiv(driver).findElement(By.xpath("div/div[@class='el-dialog__footer']/div/button[1]")).click();
                System.out.println("~~~ cancel()，取消终审，执行成功 ~~~");
            } else {
                RMCommonMethod.getButtonDiv(driver).findElement(By.xpath("div/div[@class='el-dialog__footer']/div/button[2]")).click();
                System.out.println("没找到测试审核人");
            }
        } else System.out.println("没有autoTest测试数据");
        Thread.sleep(3000);
    }

    //取消传稿
    public static void feature() throws InterruptedException {
        WebElement script = search();
        if (script != null) {
            script.findElement(By.xpath("div[@class='item-content']/div/div[@class='item-btns']/div[1]/button[4]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@class='el-select']/div/input")).click();
            Thread.sleep(200);
            driver.findElement(By.xpath("//div[@class='el-select']/div/input")).sendKeys(usernick);
            Thread.sleep(200);
            if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='el-select-group']/li/span"))) {
                driver.findElement(By.xpath("//ul[@class='el-select-group']/li")).click();
                Thread.sleep(100);
                driver.findElement(By.xpath("//textarea[@class='el-textarea__inner']")).sendKeys("这里是传稿说明。这里是传稿说明。这里是传稿说明。这里是传稿说明。这里是传稿说明。这里是传稿说明。");
                RMCommonMethod.getButtonDiv(driver).findElement(By.xpath("div/div[@class='el-dialog__footer']/div/button[1]")).click();
                System.out.println("~~~ feature()，传稿，执行成功 ~~~");
            } else {
                RMCommonMethod.getButtonDiv(driver).findElement(By.xpath("div/div[@class='el-dialog__footer']/div/button[2]")).click();
                System.out.println("未找到接收人");
            }
        } else System.out.println("没有autoTest测试数据");
        Thread.sleep(3000);
    }

    //一键撤稿
    public static void back() throws InterruptedException {
        WebElement script = search();
        if (script != null) {
            script.findElement(By.xpath("div[@class='item-content']/div/div[@class='item-btns']/div[1]/button[last()]")).click();
            Thread.sleep(500);
            System.out.println("~~~ back()，一键撤稿，执行成功 ~~~");
        } else System.out.println("没有autoTest测试数据");
        Thread.sleep(3000);
    }

    //测试数据搜索
    private static WebElement search() throws InterruptedException {
        WebElement script = null;
        driver.findElement(By.cssSelector("input.keyword.float-left")).clear();//清空搜索关键词
        driver.findElement(By.cssSelector("input.keyword.float-left")).sendKeys("autoTest");//录入搜索关键词
        driver.findElement(By.cssSelector("div.btn.float-right")).click();//点击搜索
        Thread.sleep(1000);
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='finished-list']/li/div"))) {//校验是否有搜索结果数据
            script = driver.findElement(By.xpath("//ul[@class='finished-list']/li"));//有搜索结果取第一条数据
        }
        return script;
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!RMCommonMethod.isJudgingElement(driver, By.className("text-p"))) {
                    if (RMCommonMethod.isJudgingElement(driver, By.className("loginBtn"))) driver = login();
                    driver.get(domain + "/reditor/static/index.html#/library");
                    Thread.sleep(2000);
                } else break;
            }
            driver.findElement(By.xpath("//p[@class='float-left']/span[2]")).click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}