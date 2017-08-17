package test;

import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.Test;

public class SearchByShortLinkTest extends AbstractTest {
	
	@Test(dataProvider = "long_links", groups = "create_delete")
	public void test_createLinksForSearch(String actualLink) {
		mainPage.createShortLink(actualLink);
		Assert.assertTrue(mainPage.isLongLinkPresent(actualLink));
	}
	
	@Test(dependsOnMethods = "test_createLinksForSearch", groups = "search")
	public void test_searchByShortLink() {
		String linkNameForSearch = mainPage.searchByShortLinkName();
		Assert.assertTrue(mainPage.isSameLinkFoundBySearch(linkNameForSearch));		
	}
	
	@Test(dependsOnMethods = "test_searchByShortLink", groups = "search_appear_link")
	public void test_searchByShortLink_OneLinkAppears() {
		Assert.assertEquals(mainPage.checkNumberOflinks(), 1);
	}
	
	@AfterGroups(groups = {"search_appear_link"})
    public void tearDownSearchLinks() {
        mainPage = mainPage.deleteAllLinks();
    }
	
}
