package test;

import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.LinkKey;

import java.util.Map;


public class LinkCreationTest extends AbstractTest {

    @DataProvider(name = "oneLink")
    public Object[][] oneLinkCreator() {
        Object[][] data = new Object[1][];
        Map<LinkKey, String> map = links.get(0);
        String longLonk = map.get(LinkKey.LONG_LINK);
        String message = "Invalid short link (only English letters, digits, and '-' allowed)";
        data[0] = new Object[]{message, longLonk};
        return data;
    }

    @Test(dataProvider = "oneLink")
    public void createShortLinkWhereTheShortLinkIsTheSameAsTheLongLink(String hintText, String longLink) {
        mainPage.createShortLink(longLink, longLink);
        Assert.assertTrue(mainPage.isHintForLinks(hintText));
    }


    @Test()
    public void createEmptyLongLinkWithoutSpecifyingShortLinkName() {
        mainPage.createShortLink("");
        Assert.assertTrue(mainPage.isHintForLinks("This field is required"));
    }


    @Test(dataProvider = "long_links", groups = "longlink")
    public void createLongLinkWithoutSpecifyingShortLinkName(String longLink) {
        mainPage.createShortLink(longLink);
        Assert.assertTrue(mainPage.isLongLinkPresent(longLink));
    }

    @DataProvider(name = "shortLinkWithExistingShortLinkName")
    public Object[][] shortLinkWithExistingShortLinkName() {
        Object[][] data = new Object[1][];
        Map<LinkKey, String> map = links.get(0);
        String longLink = map.get(LinkKey.LONG_LINK);
        String shortLink = map.get(LinkKey.SHORT_LINK).concat("-" + System.currentTimeMillis());
        data[0] = new Object[]{shortLink, longLink};
        return data;
    }

    @Test(dataProvider = "shortLinkWithExistingShortLinkName", groups = {"linktest"})
    public void createShortLinkWithExistingShortLinkName(String shortLink, String longLink) {
        mainPage.createShortLink(shortLink, longLink);
        mainPage.isLongLinkPresent(longLink);
        mainPage.createShortLink(shortLink, longLink);
        Assert.assertTrue(mainPage.isServerReturnedError());
    }

    @Test(dataProvider = "short_links", groups = "shortlink")
    public void createLongLinkWithSpecifyingShortLinkName(String longLink, String shortLinkName) {
        mainPage.createShortLink(shortLinkName, longLink);
        Assert.assertTrue(mainPage.isShortLinkMatchesLongLink(shortLinkName, longLink));

    }

    @Test(dataProvider = "long_links", groups = "directlink")
    public void shortLinkDirectsToFullLinkAddress(String longLink) {
        mainPage.selectFilter("Created descending").createShortLink(longLink);
        String url = mainPage.getLinkTabURL();
        Assert.assertEquals(url, longLink);
    }

    @AfterGroups(groups = {"longlink", "shortlink", "directlink", "linktest"})
    public void deleteLinks() {
        mainPage = mainPage.deleteAllLinks();
    }
}
