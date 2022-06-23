package mymanuscript;

import base.LoginPortal;
import base.RMCommonMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author wufeng
 * 成品稿，签发、传稿、一键撤稿
 * @date 2022/6/22 15:14
 */
public class FinishedScript extends LoginPortal {

    static WebDriver driver;

    //签发
    public static void push() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='finished-list']/li/div"))) {//校验是否有成品稿数据
            driver.findElement(By.xpath("//ul[@class='finished-list']/li[1]/div[@class='item-content']/div/div[1]/div[1]/button[1]")).click();//点击签发
            Thread.sleep(200);
            boolean push = treeIframe(driver);//签发频道图层操作
            if (push) System.out.println("~~~ push()，稿件签发，执行成功 ~~~");
            else System.out.println("稿件签发失败");
        } else System.out.println("没有可签发的成品稿");
        Thread.sleep(3000);
    }

    //传稿
    public static void feature() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='finished-list']/li/div"))) {//校验是否有成品稿数据
            driver.findElement(By.xpath("//ul[@class='finished-list']/li[1]/div[@class='item-content']/div/div[1]/div[1]/button[4]")).click();//点击传稿
            Thread.sleep(200);
            driver.findElement(By.xpath("//div[@class='el-select']/div/input")).click();
            Thread.sleep(200);
            driver.findElement(By.xpath("//div[@class='el-select']/div/input")).sendKeys(usernick);
            Thread.sleep(200);
            if (RMCommonMethod.isJudgingElement(driver,By.xpath("//ul[@class='el-select-group']/li/span"))){
                driver.findElement(By.xpath("//ul[@class='el-select-group']/li")).click();
                Thread.sleep(100);
                driver.findElement(By.xpath("//textarea[@class='el-textarea__inner']")).sendKeys("这里是传稿说明。这里是传稿说明。这里是传稿说明。这里是传稿说明。这里是传稿说明。这里是传稿说明。");
                driver.findElement(By.xpath("//div[@class='content-list']/div[1]/div/div[@class='el-dialog__footer']/div/button[1]")).click();
                System.out.println("~~~ feature()，传稿，执行成功 ~~~");
            }else System.out.println("没有找到传稿目标-"+usernick);
        } else System.out.println("没有可传稿的成品稿");
        Thread.sleep(3000);
    }

    //一键撤稿
    public static void back() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='finished-list']/li/div"))) {//校验是否有成品稿数据
            driver.findElement(By.xpath("//ul[@class='finished-list']/li[1]/div[@class='item-content']/div/div[1]/div[1]/button[last()]")).click();//点击传稿
            System.out.println("~~~ back()，一键撤稿，执行成功 ~~~");
        }else System.out.println("没有可传稿的成品稿");
        Thread.sleep(3000);
    }

    //签发图层
    private static boolean treeIframe(WebDriver driver) throws InterruptedException {
        boolean success = false;
        driver.switchTo().frame("treeIframe");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("div.el-input.el-input--suffix")).click();//点击
        Thread.sleep(200);
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='el-scrollbar__view el-select-dropdown__list']/li"))) {
            List<WebElement> li = driver.findElements(By.xpath("//ul[@class='el-scrollbar__view el-select-dropdown__list']/li"));
            for (int i = 0; i < li.size(); i++) {
                if (li.get(i).findElement(By.xpath("p")).getText().equals(siteName)) {
                    li.get(i).click();
                    success = true;
                    break;
                }
            }
            Thread.sleep(1000);
            if (success) {
                if (selectChannel(driver))
                    driver.findElement(By.cssSelector("button.el-button.lz-tree-ok.el-button--default")).click();
                else {
                    success = false;
                    driver.findElement(By.cssSelector("button.el-button.lz-tree-cancel.el-button--default")).click();
                }
            } else System.out.println("签发渠道中没有" + siteName);
        } else System.out.println("没有可签发的渠道");
        driver.switchTo().parentFrame();
        Thread.sleep(1000);
        return success;
    }

    //选择测试频道
    private static boolean selectChannel(WebDriver driver) throws InterruptedException {
        boolean selected = false;
        //一级频道
        List<WebElement> li1 = driver.findElements(By.xpath("//div[@class='lz-tree-box']/div[2]/div"));//一级频道列表
        for (int i1 = 0; i1 < li1.size(); i1++) {
            if (!li1.get(i1).findElement(By.xpath("div[1]/span")).getAttribute("class").contains("is-leaf")) {//如果有下一级可展开
                li1.get(i1).findElement(By.xpath("div[1]/span")).click();//点击展开第二级频道
                Thread.sleep(200);
                //二级频道
                List<WebElement> li2 = li1.get(i1).findElements(By.xpath("div[2]/div"));//第二级频道的数据列表
                for (int i2 = 0; i2 < li2.size(); i2++) {
                    if (li2.get(i2).findElement(By.xpath("div/span[2]")).getText().contains(channel1)) {//第二级频道名称是否有测试test
                        li2.get(i2).findElement(By.xpath("div/label/span")).click();//点击勾选
                        selected = true;
                        break;
                    }
                    Thread.sleep(100);
                }
            }
            if (selected) break;
            Thread.sleep(100);
        }
        if (!selected) System.out.println("没有找到测试test频道");
        Thread.sleep(1000);
        return selected;
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {//校验是已登录成功，是否已正常打开页面
                if (!RMCommonMethod.isJudgingElement(driver, By.className("text-p"))) {
                    if (RMCommonMethod.isJudgingElement(driver, By.className("loginBtn"))) driver = login();
                    driver.get(domain + "/reditor/static/index.html#/mymanuscript");
                    Thread.sleep(2000);
                } else break;
            }
            driver.findElement(By.xpath("//ul[@class='tab-header clearfix']/li[2]")).click();//切换到成品稿
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
