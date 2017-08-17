package test;

import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.LinkKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

public class EditLinkTest extends AbstractTest {
    private String date;

    @DataProvider(name = "edit_short_links")
    public Object[][] editShortLinksCreator() {
        Object[][] data = new Object[links.size()][];
        for (int i = 0; i < links.size(); i++){
            Map<LinkKey, String> map = links.get(i);
            String longLink = map.get(LinkKey.LONG_LINK);
            String shortLink = map.get(LinkKey.SHORT_LINK);
            String newShortLink = map.get(LinkKey.NEW_SHORT_LINK);
            data[i] = new Object[]{longLink, shortLink, newShortLink};
        }
        return data;
    }

    @DataProvider(name = "edit_long_links")
    public Object[][] editLongLinksCreator() {
        Object[][] data = new Object[links.size()][];
        for (int i = 0; i < links.size(); i++){
            Map<LinkKey, String> map = links.get(i);
            String longLink = map.get(LinkKey.LONG_LINK);
            String newLongLink = map.get(LinkKey.NEW_LONG_LINK);
            data[i] = new Object[]{longLink, newLongLink};
        }
        return data;
    }

    @BeforeGroups(groups = {"edit_short_links", "edit_long_links", "edit_date"})
    public void setUpEditLinks(){
        for (Map<LinkKey, String> map : links){
            String longLink = map.get(LinkKey.LONG_LINK);
            String shortLink = map.get(LinkKey.SHORT_LINK);
            mainPage.createShortLink(shortLink, longLink);
        }
    }

    @BeforeGroups(value = "edit_date")
    public void setUpDate(){
        Calendar calendar = new GregorianCalendar();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, currentDay + 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
    }


    @Test(dataProvider = "edit_short_links", groups = "edit_short_links")
    public void editShortLinkNameWithPositiveData(String longLink, String oldShortLink, String newShortLink){
        mainPage.editShortLink(oldShortLink, newShortLink);
        Assert.assertTrue(mainPage.isShortLinkMatchesLongLink(newShortLink, longLink));
    }

    @Test(dataProvider = "edit_long_links", groups = "edit_long_links")
    public void editLongLinkWithPositiveData(String longLink, String newLongLink){
        mainPage.editLongLink(longLink, newLongLink);
        Assert.assertTrue(mainPage.isLongLinkPresent(newLongLink));
    }

    @Test(dataProvider = "short_links", groups = "edit_date")
    public void editExpirationDateWithPositiveData(String longLink, String shortLink){
        mainPage.editExpirationDate(shortLink, date);
        Assert.assertTrue(mainPage.isExpirationDateCorrect(shortLink, date));
    }

    @AfterGroups(groups = {"edit_short_links", "edit_long_links", "edit_date"})
    public void tearDownEditLinks(){
        mainPage = mainPage.deleteAllLinks();
    }
}
