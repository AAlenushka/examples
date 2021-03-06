package tipsandtricks.htmlelements.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import tipsandtricks.htmlelements.web.elems.SearchArrow;
import tipsandtricks.htmlelements.web.elems.SearchResults;


/**
 * @author Artem Eroshenko eroshenkoam
 *         5/6/13, 5:15 PM
 */
public class SearchPage {

    private WebDriver driver;

    //    @FindBy(className = "b-serp-list")
    private SearchResults searchResults;

    private SearchArrow searchArrow;


    public SearchPage(WebDriver driver) {
        PageFactory.initElements(new HtmlElementDecorator(driver), this);
        this.driver = driver;
    }

    public SearchPage searchFor(String request) {
        this.searchArrow.search(request);
        return this;
    }

    public SearchResults getSearchResults() {
        return this.searchResults;
    }
}
