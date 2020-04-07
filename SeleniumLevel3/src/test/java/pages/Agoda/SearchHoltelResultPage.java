package pages.Agoda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.javatuples.Pair;
import org.openqa.selenium.Keys;

import datatype.Agoda.Enums.Filter;
import datatype.Agoda.Enums.ReviewCategory;
import datatype.Agoda.Enums.SortOption;
import datatype.Agoda.FilterOptions;
import element.base.web.Element;
import element.setting.FindBy;
import element.wrapper.web.Button;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.Logger;
import utils.helper.ResourceHelper;

public class SearchHoltelResultPage extends GeneralPage {

	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			SearchHoltelResultPage.class);
	
	// Static Elements
	protected Element eleResultItem = new Element(locator.getLocator("eleResultItem"));
	protected Button btnFilter = new Button(locator.getLocator("btnFilter"));
	protected Button btnNextPage = new Button(locator.getLocator("btnNextPage"));
	protected Button btnDeleteFilter = new Button(locator.getLocator("btnDeleteFilter"));
	protected Element eleLoadingSignal = new Button(locator.getLocator("eleLoadingSignal"));
	protected Button btnDoneMoreFilter = new Button(locator.getLocator("btnDoneMoreFilter"));
	protected Element eleReviewPointPopup = new Button(locator.getLocator("eleReviewPointPopup"));
	protected TextBox txtPriceMin = new TextBox(locator.getLocator("txtPriceMin"));
	protected TextBox txtPriceMax = new TextBox(locator.getLocator("txtPriceMax"));

	// Dynamic Elements
	protected Button btnSearchOption = new Button(locator.getLocator("btnSearchOption"));
	protected Button btnResultPriceItem = new Button(locator.getLocator("btnResultPriceItem"));
	protected Element eleMoreFilterCategory = new Element(locator.getLocator("eleMoreFilterCategory"));
	protected Element eleReviewPoint = new Element(locator.getLocator("eleReviewPoint"));
	protected Element eleStarRatingCkb = new Element(locator.getLocator("eleStarRatingCkb"));
	
	// Special Locators
	protected Pair<FindBy, String> moreFilterLocator = locator.getLocator("eleMoreFilter");
	protected Pair<FindBy, String> starRatingFilterLocator = locator.getLocator("eleStarRatingFilter");
	protected Pair<FindBy, String> priceFilterLocator = locator.getLocator("elePriceFilter");
	protected Pair<FindBy, String> destinationLocator = locator.getLocator("eleDestination");
	protected Pair<FindBy, String> nameLocator = locator.getLocator("eleName");
	protected Pair<FindBy, String> starRatingLocator = locator.getLocator("eleStarRating");
	protected Pair<FindBy, String> priceLocator = locator.getLocator("elePrice");
	protected Pair<FindBy, String> priceSliderLocator = locator.getLocator("elePriceSlider");
	protected Pair<FindBy, String> reviewScoreNumberLocator = locator.getLocator("eleReviewScoreNumber");
	
	// Methods
	public void waitForPageLoad() {
		btnNextPage.waitForDisplayed(Constants.LONG_TIME);
		btnNextPage.moveToElement();
	}
	
	public void clickFilterButton(Filter filter) {
		btnFilter = btnFilter.generateDynamic(filter.getValue());
		scrollToTop();
		btnFilter.click();
	}
	
	public void filterPrice(double min, double max) {
		clickFilterButton(Filter.Price);
		txtPriceMin.waitForDisplayed(Constants.SHORT_TIME);
		txtPriceMin.enter(min);
		txtPriceMax.enter(max);
		clickFilterButton(Filter.Price);
	}

	public void filterPriceDung(double min, double max) {
		scrollToTop();
		btnFilter.generateDynamic(Filter.Price.getValue()).click();
		new TextBox(btnFilter.generateDynamic(Filter.Price.getValue()), priceFilterLocator, 0)
				.waitForDisplayed(Constants.SHORT_TIME);
		new TextBox(btnFilter.generateDynamic(Filter.Price.getValue()), priceFilterLocator, 0)
				.enter(min);
		new TextBox(btnFilter.generateDynamic(Filter.Price.getValue()), priceFilterLocator, 1)
				.enter(max);
		btnFilter.generateDynamic(Filter.Price.getValue()).click();
	}
	
	public void filterStarRating(int... stars) {
		clickFilterButton(Filter.Rating);
		for (int i = 0; i < stars.length; i++) {
			eleStarRatingCkb.generateDynamic(stars[i]).click();
		}
		clickFilterButton(Filter.Rating);
	}

	public void filterStarRatingDung(int... stars) {
		scrollToTop();
		btnFilter.generateDynamic(Filter.Rating.getValue()).click();
		Element eleRating = null;
		for (int i = 0; i < stars.length; i++) {
			eleRating = new Element(btnFilter.generateDynamic(Filter.Rating.getValue()), starRatingFilterLocator).generateDynamic(stars[i]);
			eleRating.click();
		}
		btnFilter.generateDynamic(Filter.Rating.getValue()).click();
	}
	
	public void filterMore(FilterOptions filerOptions) {
		scrollToTop();
		btnFilter.generateDynamic(Filter.More.getValue()).click();
		btnDoneMoreFilter.waitForDisplayed(Constants.SHORT_TIME);
		Element eleFilterOption = null;
		for (String filerCategory : filerOptions.getCategories()) {
			eleFilterOption = new Element(eleMoreFilterCategory.generateDynamic(filerCategory), moreFilterLocator);
			for (Object filterOption : filerOptions.getOptions(filerCategory)) {
				eleFilterOption.generateDynamic(filterOption);
				eleFilterOption.click();
			}
		}
		btnDoneMoreFilter.click();
		btnDoneMoreFilter.waitForNotDisplayed(Constants.SHORT_TIME);
	}

	public void deleteFilter(Filter filter, boolean hide) {
		deleteFilter(filter);
		if (hide) {
			btnFilter.generateDynamic(filter.getValue()).click();
		}
	}

	public void deleteFilter(Filter filter) {
		scrollToTop();
		btnFilter.generateDynamic(filter.getValue()).click();
		btnDeleteFilter.click();
		btnDeleteFilter.waitForNotPresent(Constants.SHORT_TIME);
	}
	
	public void selectHotel(int index) {
		moveToHotel(index);
		eleResultItem.getWrapperElements().get(index - 1).click();
	}
	
	public void moveToHotel(int index) {
		eleResultItem.waitForDisplayed(Constants.SHORT_TIME);
		StopWatch sw = new StopWatch();
		sw.start();
		int size = 0;
		List<Element> elements;
		do
		{
			elements = eleResultItem.getWrapperElements();
			size = elements.size();
			if (size == 0) return;
			if (size >= index)
			{
				elements.get(index - 1).moveToElement();
				break;
			}
			else
			{
				elements.get(size - 1).moveToElement();
			}
		}
		while (sw.getTime(TimeUnit.SECONDS) < Constants.SHORT_TIME);
	}
	
	public String getHotelName(int index) {
		moveToHotel(index);
		Element eleName = new Element(eleResultItem.getWrapperElements().get(index-1), nameLocator);
		return eleName.getText();
	}
	
	public Map<String, String> getHotelScores(int index) {
		Map<String, String> scoreMap = new HashMap<String, String>();
		moveToHotel(index);
		Element eleScoreNumber = new Element(eleResultItem.getWrapperElements().get(index-1), reviewScoreNumberLocator);
		eleScoreNumber.moveToElement();
		eleReviewPointPopup.waitForDisplayed(Constants.SHORT_TIME);
		for (ReviewCategory category : ReviewCategory.values()) {
			scoreMap.put(category.getValue(), eleReviewPoint.generateDynamic(category.getValue()).getText());
		}
		return scoreMap;
	}
	
	// Verify

	public boolean isHotelDestinationCorrect(String destination, int records) {
		moveToHotel(records);
		Element eleDestination = null;
		List<Element> elements = eleResultItem.getWrapperElements();
		if (elements.size() < records)
			return false;
		for (int i = 0; i < records; i++) {
			eleDestination = new Element(elements.get(i), destinationLocator, destination);
			if (!eleDestination.getText().contains(destination)) {
				return false;
			}
		}
		return true;
	}

	public boolean isHotelStarRatingCorrect(int stars, int records) {
		moveToHotel(records);
		Element eleStarRating = null;
		List<Element> elements = eleResultItem.getWrapperElements();
		if (elements.size() < records)
			return false;
		for (int i = 0; i < records; i++) {
			eleStarRating = new Element(elements.get(i), starRatingLocator);
			if (Double.parseDouble(eleStarRating.getAttribute("title").split(" ")[0]) >= stars) {
				return false;
			}
		}
		return true;
	}

	public boolean isHotelPriceCorrect(double min, double max, int records) {
		moveToHotel(records);
		Element elePrice = null;
		double currentPrice = 0;
		List<Element> elements = eleResultItem.getWrapperElements();
		if (elements.size() < records)
			return false;
		for (int i = 0; i <= records; i++) {
			elePrice = new Element(elements.get(i), priceLocator);
			currentPrice = Double.parseDouble(elePrice.getText().replace(",", ""));
			if (currentPrice > max || currentPrice < min) {
				return false;
			}
		}
		return true;
	}

	public boolean isPriceSliderReset() {
		return new Element(btnFilter.generateDynamic("PriceFilterRange"), priceSliderLocator).isDisplayed();
	}

	public boolean isFiltersHighLighted(Filter... filters) {
		for (int i = 0; i < filters.length; i++) {
			if (!btnFilter.generateDynamic(filters[i]).getAttribute("class").contains("Button--select")) {
				return false;
			}
		}
		return true;
	}
	
	public void chooseSortOption(SortOption sortOption) {
		btnSearchOption.generateDynamic(sortOption.getCode()).moveToElement();
		btnSearchOption.click();
		btnNextPage.waitForDisplayed(Constants.SHORT_TIME);
		btnNextPage.moveToElement();
		eleLoadingSignal.waitForDisplayed(Constants.SHORT_TIME);
		eleLoadingSignal.waitForNotDisplayed(Constants.LONG_TIME);
	}
	
	public boolean isResultSortedByCheapestPrice(int records) {
		List<Element> elements = eleResultItem.getWrapperElements();
		
		if (elements.size() >= records) {
			
			for (int i = 1; i <= records; i++) {
				
				String currentResultPrice = btnResultPriceItem.generateDynamic(i).getText().replace(",", "");
				String nextResultPrice = btnResultPriceItem.generateDynamic(i+1).getText().replace(",", "");

				if (Double.parseDouble(currentResultPrice) > Double.parseDouble(nextResultPrice)) {
					return false;
				}
			}
			return true;
		} else {

			Logger.warning("No or less than " + records + " result is available. Total results are " + elements.size());
		}
		return false;
	}
}
