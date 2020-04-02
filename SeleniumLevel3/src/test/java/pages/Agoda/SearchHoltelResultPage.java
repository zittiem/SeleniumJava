package pages.Agoda;

import java.util.List;

import datatype.Agoda.Enums.Filter;
import element.base.web.Element;
import element.setting.FindBy;
import element.wrapper.web.Button;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class SearchHoltelResultPage {

	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			getClass().getSimpleName());
	// Static Elements
	protected Element eleResultItem = new Element(locator.getLocator("eleResultItem"));
	protected Button btnFilter = new Button(locator.getLocator("btnFilter"));
	protected Button btnNextPage = new Button(locator.getLocator("btnNextPage"));
	protected Button btnDeleteFilter = new Button(locator.getLocator("btnDeleteFilter"));

	// Dynamic Elements
	protected Button btnFilterPopular = btnFilter.generateDynamic("Popular");
	protected Button btnFilterLocation = btnFilter.generateDynamic("LocationFilters");
	protected Button btnFilterMore = btnFilter.generateDynamic("more");

	// filter Price

	// protected Element eleSliderTrack = new Element(btnFilterPriceRange,
	// FindBy.xpath,"//div[contains(@class,'slider-track')]");

	// Methods

	public void waitForPageLoad() {
		btnNextPage.waitForDisplayed(Constants.LONG_TIME);
	}

	public void filterPrice(double min, double max) {
		btnFilter.generateDynamic("PriceFilterRange").click();
		new TextBox(btnFilter.generateDynamic("PriceFilterRange"), FindBy.xpath, "//input[@id='price_box_0']")
				.waitForDisplayed(Constants.SHORT_TIME);
		new TextBox(btnFilter.generateDynamic("PriceFilterRange"), FindBy.xpath, "//input[@id='price_box_0']")
				.enter(min);
		new TextBox(btnFilter.generateDynamic("PriceFilterRange"), FindBy.xpath, "//input[@id='price_box_1']")
				.enter(max);
		btnFilter.generateDynamic("PriceFilterRange").click();
	}

	public void filterStarRating(int... stars) {
		btnFilter.generateDynamic("StarRating").click();
		Element eleRating = null;
		for (int i = 0; i < stars.length; i++) {
			eleRating = new Element(btnFilter.generateDynamic("StarRating"), FindBy.xpath,
					"//following-sibling::div//span[@data-element-value='%d']").generateDynamic(stars[i]);
			eleRating.waitForDisplayed(Constants.SHORT_TIME);
			eleRating.click();
		}
		btnFilter.generateDynamic("StarRating").click();
	}

	public void deleteFilter(Filter filter, boolean hide) {
		deleteFilter(filter);
		if (hide) {
			btnFilter.generateDynamic(filter.getValue()).click();
		}
	}

	public void deleteFilter(Filter filter) {
		btnFilter.generateDynamic(filter.getValue()).click();
		btnDeleteFilter.waitForDisplayed(Constants.SHORT_TIME);
		btnDeleteFilter.click();
	}
	// Verify

	public boolean isDestinationDisplayed(String destination, int records) {
		List<Element> elements = eleResultItem.getWrapperElements();
		if (elements.size() >= records) {
			Element eleDestination = null;
			for (int i = 1; i <= records; i++) {
				eleDestination = new Element(elements.get(i), FindBy.xpath,
						"//span[@data-selenium='area-city-text' and contains(text(),'%s')]", destination);
				if (!eleDestination.isDisplayed()) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isStartRatingDisplayed(int stars, int records) {
		List<Element> elements = eleResultItem.getWrapperElements();
		if (elements.size() >= records) {
			Element eleStarRating = null;
			for (int i = 1; i <= records; i++) {
				eleStarRating = new Element(elements.get(i), FindBy.xpath, "//i[@data-selenium='hotel-star-rating']");
				if (Double.parseDouble(eleStarRating.getAttribute("title").split(" ")[0]) >= stars) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isPriceDisplayed(double min, double max, int records) {
		List<Element> elements = eleResultItem.getWrapperElements();
		if (elements.size() >= records) {
			Element eleStarRating = null;
			double currentPrice = 0;
			for (int i = 1; i <= records; i++) {
				eleStarRating = new Element(elements.get(i), FindBy.xpath, "//i[@data-selenium='hotel-star-rating']");
				currentPrice = Double.parseDouble(eleStarRating.getAttribute("title").split(" ")[0].replace(".", ""));
				if (currentPrice > max || currentPrice < min) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isPriceSliderReset() {
		if (!new Element(btnFilter.generateDynamic("PriceFilterRange"), FindBy.xpath,
				"//div[contains(@class,'slider-track') and contains(@style,'width: 100%')]").isDisplayed()) {
			return false;
		}
		return true;
	}

	public boolean isFiltersHighted(Filter... filters) {
		for (int i = 0; i < filters.length; i++) {
			if (!btnFilter.generateDynamic(filters[i]).getAttribute("class").contains("Button--select")) {
				return false;
			}
		}
		return true;
	}
}
