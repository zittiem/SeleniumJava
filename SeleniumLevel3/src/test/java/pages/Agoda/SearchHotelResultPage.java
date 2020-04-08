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

public class SearchHotelResultPage extends GeneralPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			SearchHotelResultPage.class);
	
	// Static Elements
	protected Element eleResultItem = new Element(locator.getLocator("eleResultItem"));
	protected Button btnFilter = new Button(locator.getLocator("btnFilter"));
	protected Button btnNextPage = new Button(locator.getLocator("btnNextPage"));
	protected Button btnDeleteFilter = new Button(locator.getLocator("btnDeleteFilter"));
	protected Element elesLoadingSignal = new Element(locator.getLocator("elesLoadingSignal"));
	protected Element eleFirstLoadingSignal = new Element(locator.getLocator("eleFirstLoadingSignal"));
	protected Button btnDoneMoreFilter = new Button(locator.getLocator("btnDoneMoreFilter"));
	protected Element eleReviewPointPopup = new Button(locator.getLocator("eleReviewPointPopup"));
	protected TextBox txtPriceMin = new TextBox(locator.getLocator("txtPriceMin"));
	protected TextBox txtPriceMax = new TextBox(locator.getLocator("txtPriceMax"));
	protected Element elePriceSliderReset = new Element(locator.getLocator("elePriceSliderReset"));

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
	
    /**
     * Wait for page load completely. Time out is Constants.LONG_TIME.
     */
	public void waitForPageLoad() {
		btnNextPage.waitForDisplayed(Constants.LONG_TIME);
		btnNextPage.moveToElement();
	}
	
    /**
     * Click filter button. 
     * 
     * @param	filter
     * 			Name of filter button. Such as Popular, Price, Rating, Location, More
     * 
     */
	public void clickFilterButton(Filter filter) {
		btnFilter = btnFilter.generateDynamic(filter.getValue());
		scrollToTop();
		btnFilter.click();
	}
	
    /**
     * Filter results per price
     * 
     * @param	min
     * 			Minimum price used to filter results
     * 
     * @param	max
     * 			Maximum price used to filter results
     * 
     */
	public void filterPrice(int min, int max) {
		clickFilterButton(Filter.Price);
		txtPriceMin.waitForDisplayed(Constants.SHORT_TIME);
		txtPriceMin.enter(min);
		waitForItemLoad();
		txtPriceMax.enter(max);
		clickFilterButton(Filter.Price);
		waitForItemLoad();
	}
	
    /**
     * Filter results per star rating
     * 
     * @param	stars
     * 			Array int of star options that user want to filter
     * 
     */
	public void filterStarRating(int... stars) {
		clickFilterButton(Filter.Rating);
		for (int i = 0; i < stars.length; i++) {
			eleStarRatingCkb.generateDynamic(stars[i]).click();
		}
		clickFilterButton(Filter.Rating);
		waitForItemLoad();
	}
	
    /**
     * Filter results per more categories
     * 
     * @param	filerOptions
     * 			List of filter options which should be applied (FilterOptions)
     * 
     */
	public void filterMore(FilterOptions filerOptions) {
		scrollToTop();
		clickFilterButton(Filter.More);
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

    /**
     * Clear a specific filter
     * 
     * @param	filter
     * 			Type of filter. It includesPopular, Price, Rating, Location, More
     * 
     * @param	hide
     * 			
     */
	public void deleteFilter(Filter filter, boolean hide) {
		deleteFilter(filter);
		if (hide) {
			btnFilter.generateDynamic(filter.getValue()).click();
		}
	}

    /**
     * Clear a specific filter
     * 
     * @param	filter
     * 			Type of filter. It includesPopular, Price, Rating, Location, More
     * 
     */
	public void deleteFilter(Filter filter) {
		scrollToTop();
		btnFilter.generateDynamic(filter.getValue()).click();
		btnDeleteFilter.click();
		waitForItemLoad();
		btnDeleteFilter.waitForClickable(Constants.SHORT_TIME);
	}
	
    /**
     * Select a hotel per index
     * 
     * @param	index
     * 			index of hotel (int)
     * 
     */
	public void selectHotel(int index) {
		moveToHotel(index);
		eleResultItem.getWrapperElements().get(index - 1).click();
	}
	
    /**
     * Move to a specific hotel per index
     * 
     * @param	index
     * 			index of hotel (int)
     * 
     */
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
	
    /**
     * Get hotel name per index
     * 
     * @param	index
     * 			index of hotel (int)
     * 
     * @return	Return hotel name (String)
     * 
     */
	public String getHotelName(int index) {
		moveToHotel(index);
		Element eleName = new Element(eleResultItem.getWrapperElements().get(index-1), nameLocator);
		return eleName.getText();
	}
	
    /**
     * Get all detailed review score of a hotel per index
     * 
     * @param	index
     * 			index of hotel (int)
     * 
     * @return	Return Map<Category, Score> of a hotel
     * 
     */
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
	
    /**
     * Choose a specific sort option. 
     * 
     * @param	sortOption
     * 			Any sort option includes BestMatch, CheapestPrice, NearestTo, TopReviewed, SecretDeal
     * 
     */
	public void chooseSortOption(SortOption sortOption) {
		btnSearchOption.generateDynamic(sortOption.getCode()).moveToElement();
		btnSearchOption.click();
		btnNextPage.waitForDisplayed(Constants.SHORT_TIME);
		btnNextPage.moveToElement();
		waitForItemLoad();
	}
	
    /**
     * Wait for the loading signal of the first item disappears. It also means items loaded completely.
     * Timeout is Constants.LONG_TIME
     */
	public void waitForItemLoad() {
		eleFirstLoadingSignal.waitForNotDisplayed(Constants.LONG_TIME);
/*		Boolean firstItemExist = eleFirstLoadingSignal.isDisplayed(Constants.SLEEP_TIME);
		if(firstItemExist) {
			List<Element> loadingItemLst = elesLoadingSignal.getWrapperElements();
			System.out.print("loadingItemLst.size():" + loadingItemLst.size() +"\n");
			if(loadingItemLst.size() != 0) {
				loadingItemLst.get(loadingItemLst.size()-1).waitForNotPresent(Constants.LONG_TIME);
			}
		}*/
	}
	
	// Verify

	 /**
     * Return a Boolean value to indicate whether hotel destination is correct
     *
     * @param	destination
     *			Expected Hotel destination (String)
     *
     * @param	records
     * 			Number of record that need to be verified the hotel destination (int)
     *
     * @return  true|false
     * 			true: the hotel destinations of all records match with the expected destination
     * 			false: destination of any record does not match with the expected destination
     * 
     */
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

	 /**
     * Return a Boolean value to indicate whether hotel star rating is correct
     *
     * @param	stars
     *			Expected Hotel Star (int)
     *
     * @param	records
     * 			Number of record that need to be verified the hotel destination (int)
     *
     * @return  true|false
     * 			true: the hotel star rating of all records are correct
     * 			false: star rating of any record is incorrect
     * 
     */
	public boolean isHotelStarRatingCorrect(int stars, int records) {
		moveToHotel(records);
		Element eleStarRating = null;
		double currentStar = 0;
		List<Element> elements = eleResultItem.getWrapperElements();
		if (elements.size() < records)
			return false;
		for (int i = 0; i < records; i++) {
			eleStarRating = new Element(elements.get(i), starRatingLocator);
			currentStar = Double.parseDouble(eleStarRating.getAttribute("title").split(" ")[0]);
			if (currentStar < stars || currentStar >= stars + 1) {
				Logger.warning("There is a record with invalid stars (" + currentStar + " star).");
				return false;
			}
		}
		return true;
	}

	 /**
     * Return a Boolean value to indicate whether hotel price is correct
     *
     * @param	min
     *			Minimum price used to verify the hotel price (int)
     *
     * @param	max
     *			Maximum price used to verify the hotel price (int)
     *
     * @param	records
     * 			Number of record that need to be verified the hotel destination (int)
     *
     * @return  true|false
     * 			true: the hotel prices of all records are correct
     * 			false: the hotel prices of any record is incorrect
     * 
     */
	public boolean isHotelPriceCorrect(double min, double max, int records) {
		moveToHotel(records);
		Element elePrice = null;
		double currentPrice = 0;
		List<Element> elements = eleResultItem.getWrapperElements();
		if (elements.size() < records) {
			Logger.warning("There is less than " + records + "records. Total records is: " + elements.size());
			return false;
		}
		for (int i = 0; i <= records; i++) {
			elePrice = new Element(elements.get(i), priceLocator);
			currentPrice = Double.parseDouble(elePrice.getText().replace(",", ""));
			if (currentPrice > max || currentPrice < min) {
				Logger.warning("There is a record which price (" + currentPrice + ") is larger than max or less than min.");
				return false;
			}
		}
		return true;
	}

	 /**
     * Return a Boolean value to indicate whether the price slider is reset
     *
     * @return  true|false
     * 			true: the price slider is reset
     * 			false: the price slider is not reset
     * 
     */
	public boolean isPriceSliderReset() {
		return elePriceSliderReset.isDisplayed();
	}

	 /**
     * Return a Boolean value to indicate whether filter(s) is highlighted
     *
     * @param	filters
     *			List of filters that need to be verified
     *
     * @return  true|false
     * 			true: filter(s) is highlighted
     * 			false: filter(s) is not highlighted
     * 
     */
	public boolean isFiltersHighLighted(Filter... filters) {
		for (int i = 0; i < filters.length; i++) {
			if (!btnFilter.generateDynamic(filters[i]).getAttribute("class").contains("Button--select")) {
				return false;
			}
		}
		return true;
	}
	
	 /**
     * Return a Boolean value to indicate whether results are sorted by cheapest price
     *
     * @param	records
     *			Number of records (int)
     *
     * @return  true|false
     * 			true: all records are sorted by cheapest prices
     * 			false: records are not sorted by cheapest prices
     * 
     */
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
