package mymanuscript;

import base.LoginPortal;
import base.RMCommonMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


/**
 * @author wufeng
 * 流转稿管理，提交、编辑、删除、撤回、提交并终审
 * @date 2022/6/21 14:36
 */
public class ScriptManage extends LoginPortal {

    static WebDriver driver;

    //稿件编辑
    public static void editScript() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='content-list']/li"))) {//校验是否有数据
            List<WebElement> scripts = driver.findElements(By.xpath("//ul[@class='content-list']/li"));//获取数据列表
            boolean edit = false;
            for (int i = 0; i < scripts.size(); i++) {//循环找初稿状态
                if (scripts.get(i).findElement(By.xpath("div[@class='item-content']/ul/li/div[2]/span")).getText().equals("初稿")) {//校验是都是初稿
                    scripts.get(i).findElement(By.xpath("div/div/div/i[@class='el-icon-edit-outline']")).click();//点击编辑
                    Thread.sleep(1000);
                    if (!RMCommonMethod.isJudgingElement(driver, By.className("piclist")))//校验是否不是图集稿件
                        driver.findElement(By.xpath("//div[@class='detailBox']/div/textarea")).sendKeys("——编辑更新（" + System.currentTimeMillis() + ")");//编辑修改内容
                    else {
                        driver.findElement(By.xpath("//ul[@class='piclist']/ul/li[1]/div[@class='float-left rft-info']/form/div[3]/div/div/textarea")).sendKeys("——编辑更新（" + System.currentTimeMillis() + ")");//图集稿件编辑修改图片简介
                    }
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector("button.el-button.saveBtn.el-button--info")).click();//点击保存
                    edit = true;
                    break;
                }
            }
            if (edit) System.out.println("~~~ addAudio()，新建音频稿件，执行成功 ~~~");
            else System.out.println("没有初稿可编辑");
        } else System.out.println("流转稿没有稿件数据");
        Thread.sleep(3000);
    }

    //稿件删除
    public static void delScript() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='content-list']/li"))) {//校验是否有数据
            List<WebElement> scripts = driver.findElements(By.xpath("//ul[@class='content-list']/li"));//获取数据列表
            boolean del = false;
            for (int i = 0; i < scripts.size(); i++) {
                if (scripts.get(i).findElement(By.xpath("div[@class='item-content']/ul/li/div[2]/span")).getText().equals("初稿")) {//校验是否是初稿
                    scripts.get(i).findElement(By.xpath("div/div/div/i[@class='el-icon-delete']")).click();//点击删除
                    Thread.sleep(1000);
                    driver.findElement(By.xpath("//div[@class='el-dialog__wrapper'][2]/div/div[@class='el-dialog__footer']/span/button[2]")).click();//确定删除
                    del = true;
                    break;
                }
            }
            if (del) System.out.println("~~~ delScript()，删除稿件，执行成功 ~~~");
            else System.out.println("没有初稿可删除");
        } else System.out.println("流转稿没有稿件数据");
        Thread.sleep(3000);
    }

    //稿件提交
    public static void submitScript() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='content-list']/li"))) {//校验是否有数据
            List<WebElement> scripts = driver.findElements(By.xpath("//ul[@class='content-list']/li"));//获取数据列表
            boolean submit = false;
            for (int i = 0; i < scripts.size(); i++) {
                if (scripts.get(i).findElement(By.xpath("div[@class='item-content']/ul/li/div[2]/span")).getText().equals("初稿")) {//校验是否是初稿
                    submit = true;
                    scripts.get(i).click();//点击选择该数据
                    driver.findElement(By.cssSelector("button.el-button.el-button--danger")).click();//点击提交
                    Thread.sleep(200);

                    if (!RMCommonMethod.isJudgingElement(driver, By.className("el-select__tags-text"))) {//校验转审人中是否没有数据
                        driver.findElement(By.className("el-select__input")).click();//没数据点击选择转审人
                        driver.findElement(By.className("el-select__input")).sendKeys("吴枫");//搜索转审人
                        Thread.sleep(500);
                        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='el-select-group']/li"))) {//校验是否有搜索结果
                            driver.findElement(By.xpath("//ul[@class='el-select-group']/li")).click();//选择第一条搜索结果数据
                            Thread.sleep(500);
                            driver.findElement(By.xpath("//form[@class='el-form']/div[1]/label")).click();//点击其他位置关闭审核人列表
                            Thread.sleep(500);
                        } else {
                            driver.findElement(By.xpath("//div[@class='saveAndSub']/div[@class='dialog-footer']/button[2]")).click();//点击取消
                            submit = false;
                        }
                    }
                    if (submit)
                        driver.findElement(By.xpath("//div[@class='saveAndSub']/div[@class='dialog-footer']/button[1]")).click();//点击确定，确定提交
                    break;
                }
            }
            if (submit) System.out.println("~~~ submitScript()，提交稿件，执行成功 ~~~");
            else System.out.println("没有初稿可提交");
        } else System.out.println("流转稿没有稿件数据");
        Thread.sleep(3000);
    }

    //稿件撤回
    public static void recallScript() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='content-list']/li"))) {//校验是否有数据
            List<WebElement> scripts = driver.findElements(By.xpath("//ul[@class='content-list']/li"));//获取数据列表
            boolean recall = false;
            for (int i = 0; i < scripts.size(); i++) {
                if (scripts.get(i).findElement(By.xpath("div[@class='item-content']/ul/li/div[2]/span")).getText().equals("待审核")) {//校验是否是待审核状态
                    scripts.get(i).findElement(By.xpath("div/div/div/i[@class='el-icon-recall']")).click();//点击撤回
                    Thread.sleep(1000);
                    driver.findElement(By.xpath("//div[@class='el-dialog__wrapper'][3]/div/div[@class='el-dialog__footer']/span/button[2]")).click();//确定撤回
                    recall = true;
                    break;
                }
            }
            if (recall) System.out.println("~~~ recallScript()，撤回稿件，执行成功 ~~~");
            else System.out.println("没有待审核的稿件可撤回");
        } else System.out.println("流转稿没有稿件数据");
        Thread.sleep(3000);
    }

    //稿件提交并终审
    public static void SubmitAndVerify() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='content-list']/li"))) {//校验是否有数据
            List<WebElement> scripts = driver.findElements(By.xpath("//ul[@class='content-list']/li"));//获取数据列表
            boolean submit = false;
            for (int i = 0; i < scripts.size(); i++) {
                if (scripts.get(i).findElement(By.xpath("div[@class='item-content']/ul/li/div[2]/span")).getText().equals("初稿")) {//校验是否是初稿
                    submit = true;
                    scripts.get(i).click();//点击选择该数据
                    driver.findElement(By.xpath("//div[@class='toolbar-left']/button[2]")).click();//点击提交并终审
                    break;
                }
            }
            if (submit) System.out.println("~~~ submitAndVerify()，提交并终审稿件，执行成功 ~~~");
            else System.out.println("没有初稿可提交并终审");
        } else System.out.println("流转稿没有稿件数据");
        Thread.sleep(3000);
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!RMCommonMethod.isJudgingElement(driver, By.className("text-p"))) {
                    if (RMCommonMethod.isJudgingElement(driver, By.className("loginBtn"))) driver = login();
                    driver.get(domain + "/reditor/static/index.html#/mymanuscript");
                    Thread.sleep(2000);
                } else break;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
