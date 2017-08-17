package test;

import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.Test;

public class DeleteNotAllLinksTest extends AbstractTest {

	@Test(dataProvider = "long_links", groups = "create_delete")
	public void test_createLinksForDelete(String actualLink) {
		mainPage.createShortLink(actualLink);
		Assert.assertTrue(mainPage.isLongLinkPresent(actualLink));
	}

	@Test(dependsOnMethods = "test_createLinksForDelete", groups = "delete_not_all")
	public void test_deleteNotAllLinks() {
		mainPage.deleteNotAllLinks();		
		Assert.assertEquals(mainPage.checkNumberOflinks(), 1);
	}
	
	@AfterGroups(groups = {"delete_not_all"})
    public void tearDownDeleteLinks() {
        mainPage = mainPage.deleteAllLinks();
    }

}
