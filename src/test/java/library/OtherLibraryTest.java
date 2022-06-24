package library;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/6/23 17:26
 */
public class OtherLibraryTest {

    @Test
    public void testUse() throws InterruptedException {
        OtherLibrary.use();
    }
}