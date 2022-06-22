package mymanuscript;

import base.LoginPortal;
import base.RMCommonMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author wufeng
 * 新建融媒稿件（文稿、图集、音频、视频）
 * @date 2022/6/20 16:36
 */
public class CreateScript extends LoginPortal {

    static WebDriver driver;

    //新建普通文稿
    public static void addArticle() throws InterruptedException {
        driver.findElement(By.cssSelector("i.add.el-icon-plus")).click();//点击新建
        Thread.sleep(200);
        driver.findElement(By.className("addArticle")).click();//点击新建文稿
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@class='required-box']/div/input")).sendKeys("autoTest-普通文稿-" + System.currentTimeMillis());//录入标题
        driver.switchTo().frame("ueditor_0");//切换到编辑器图层
        Thread.sleep(200);
        driver.findElement(By.tagName("p")).sendKeys("这是稿件内容" + System.currentTimeMillis());//录入内容
        driver.switchTo().defaultContent();//切回到默认图层
        Thread.sleep(200);
        driver.findElement(By.xpath("//div[@class='el-form-item is-required']/div/div[2]/button")).click();//点击选择
        Thread.sleep(500);
        boolean hasLibrary = false;
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//table[@class='el-table__body']/tbody/tr"))) {//校验是否有数据
            hasLibrary = true;
            driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr[1]/td[1]/div/label/span/span")).click();//选中第一条数据
            driver.findElement(By.cssSelector("button.el-button.el-button--danger")).click();//点击确定
        } else driver.findElement(By.cssSelector("button.el-button.el-button--default")).click();//无数据则点击取消
        if (hasLibrary) {
            driver.findElement(By.xpath("//header[@class='el-header edit-head headerCss zIndex']/button[1]")).click();//点击保存
            System.out.println("~~~ addArticle()，新建文稿，执行成功 ~~~");
        } else {
            driver.findElement(By.className("el-icon-circle-close")).click();//没有可选择的稿库数据，点击关闭新建文稿
            System.out.println("稿库无数据，无法创建文稿");
        }
        Thread.sleep(3000);
    }

    //新建图集稿件
    public static void addImg() throws InterruptedException {
        driver.findElement(By.cssSelector("i.add.el-icon-plus")).click();//点击新建
        Thread.sleep(200);
        driver.findElement(By.className("addImg")).click();//点击新建图集稿件
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@class='el-input el-input--suffix']/input")).sendKeys("autoTest-图集稿件-" + System.currentTimeMillis());//录入稿件标题
        Thread.sleep(200);
        driver.findElement(By.className("upload-btn2")).click();//点击在线资源库
        RMCommonMethod.getMaterial(driver, 1);//再选资源库选择图片
        driver.findElement(By.cssSelector("button.el-button.saveBtn.el-button--info")).click();//点击保存
        System.out.println("~~~ addImg()，新建图集稿件，执行成功 ~~~");
        Thread.sleep(3000);
    }

    //新建视频稿件
    public static void addVideo() throws InterruptedException {
        driver.findElement(By.cssSelector("i.add.el-icon-plus")).click();//点击新建
        Thread.sleep(200);
        driver.findElement(By.className("addVideo")).click();//点击新建视频稿件
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@class='titBoxInput el-input el-input--suffix']/input")).sendKeys("autoTest-视频稿件-" + System.currentTimeMillis());//录入标题
        Thread.sleep(200);
        driver.findElement(By.className("upload2")).click();//点击在线资源库
        Thread.sleep(500);
        RMCommonMethod.getMaterial(driver, 3);//在线资源库选择视频
        Thread.sleep(2000);
        driver.findElement(By.className("sy-tools_setImg")).click();//设置封面图
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.cropSet-button.ok.save")).click();//点击确定，添加封面
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.el-button.saveBtn.el-button--info")).click();//点击保存
        System.out.println("~~~ addVieo()，新建视频稿件，执行成功 ~~~");
        Thread.sleep(3000);
    }

    //新建音频稿件
    public static void addAudio() throws InterruptedException {
        driver.findElement(By.cssSelector("i.add.el-icon-plus")).click();//点击新建
        Thread.sleep(200);
        driver.findElement(By.className("addAudio")).click();//点击新建音频
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@class='titBoxInput el-input el-input--suffix']/input")).sendKeys("autoTest-音频稿件-" + System.currentTimeMillis());//录入稿件名称
        Thread.sleep(200);
        driver.findElement(By.className("upload2")).click();//点击在线资源库
        Thread.sleep(500);
        RMCommonMethod.getMaterial(driver, 2);//在线资源库选择视频
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.el-button.saveBtn.el-button--info")).click();//点击保存
        System.out.println("~~~ addAudio()，新建音频稿件，执行成功 ~~~");
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
