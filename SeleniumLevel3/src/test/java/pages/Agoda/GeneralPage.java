package pages.Agoda;

import java.util.List;
import datatype.Agoda.Enums.Month;
import datatype.Agoda.Enums.TravelFields;
import datatype.Agoda.Enums.TravelTypes;
import datatype.Agoda.RoomBooking;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.wrapper.web.Button;
import element.wrapper.web.DropDown;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import tests.TestBase;
import utils.constant.Constants;
import utils.helper.ResourceHelper;
import utils.helper.DateTimeHelper;

public class GeneralPage extends TestBase {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			GeneralPage.class);
	
	// Static Elements
	protected Button btnLanguageMenu = new Button(locator.getLocator("btnLanguageMenu"));
	protected Element eleLanguagePopup = new Element(locator.getLocator("eleLanguagePopup"));
	protected Button btnShowDestination = new Button(locator.getLocator("btnShowDestination"));
	protected Button btnShowCheckIn = new Button(locator.getLocator("btnShowCheckIn"));
	protected Button btnShowCheckOut = new Button(locator.getLocator("btnShowCheckOut"));
	protected Button btnShowTraveler = new Button(locator.getLocator("btnShowTraveler"));
	protected TextBox txtDestination = new TextBox(locator.getLocator("txtDestination"));
	protected Element eleCheckInCal = new Element(locator.getLocator("eleCheckInCal"));
	protected Element eleCheckOutCal = new Element(locator.getLocator("eleCheckOutCal"));
	protected Button btnShowTravelingOption = new Button(locator.getLocator("btnShowTravelingOption"));
	protected Button btnPreviousMonth = new Button(locator.getLocator("btnPreviousMonth"));
	protected Button btnNextMonth = new Button(locator.getLocator("btnNextMonth"));
	protected DropDown cbxChildAge = new DropDown(locator.getLocator("cbxChildAge"));
	protected Element eleFirstSuggestionItem = new Element(locator.getLocator("eleFirstSuggestionItem"));
	protected Element elesDatePicker = new Element(locator.getLocator("elesDatePicker"));
	protected Element eleDatePicker1 = new Element(locator.getLocator("eleDatePicker1"));
	protected Element eleDatePicker2 = new Element(locator.getLocator("eleDatePicker2"));

	// Dynamic Elements
	protected Button btnLanguage = new Button(locator.getLocator("btnLanguage"));
	protected Button btnTravelingOption = new Button(locator.getLocator("btnTravelingOption"));
	protected Button btnMinus = new Button(locator.getLocator("btnMinus"));
	protected Button btnPlus = new Button(locator.getLocator("btnPlus"));
	protected Element eleDisplayValueLbl = new Element(locator.getLocator("eleDisplayValueLbl"));
	protected Element eleDate = new Element(locator.getLocator("eleDate"));
	
	// Methods
    /**
     * Enter a destination or property into SearchBoxTextEditor
     *
     * @param  keyWord
     *         A destination or property (String)
     *
     */
	protected void enterSearchKeyWord(String keyWord) {
		if (!keyWord.isEmpty()) {
			btnShowDestination.click();
			txtDestination.enter(keyWord);
			eleFirstSuggestionItem.click();
		}
	}

    /**
     * Find and select a year in the calendar
     *
     * @param  year
     *         The specific year that should be selected (int)
     *
     */
	private void selectYear(int year) {
		List<Element> eles = elesDatePicker.getWrapperElements();
		eles.get(0).waitForDisplayed(Constants.SHORT_TIME);
		String currentFirstMonthYear = eles.get(0).getText();
		int firstYear = DateTimeHelper.getYear(currentFirstMonthYear, ResourceHelper.SHARED_DATA.get().month_year_format);
		String currentSecondMonthYear = eles.get(1).getText();
		int secondYear = DateTimeHelper.getYear(currentSecondMonthYear, ResourceHelper.SHARED_DATA.get().month_year_format);
		Button btnClick = null;
		while (year != firstYear && year != secondYear) {
			if (year < firstYear) {
				btnClick = btnNextMonth;
				firstYear = DateTimeHelper.getYear(eleDatePicker1.getText(), ResourceHelper.SHARED_DATA.get().month_year_format);
			} else if (year > secondYear) {
				btnClick = btnPreviousMonth;
				secondYear = DateTimeHelper.getYear(eleDatePicker2.getText(), ResourceHelper.SHARED_DATA.get().month_year_format);
			}
			btnClick.click();
		}
	}

    /**
     * Find and select a month in the calendar
     *
     * @param  month
     *         The specific month that should be selected (int)
     *
     */
	private void selectMonth(int month) {
		List<Element> eles = elesDatePicker.getWrapperElements();
		String currentFirtMonthYear = eles.get(0).getText();
		int firstMonth = Month.getMonth(currentFirtMonthYear.split(" ")[0]);
		String currentSecondMonthYear = eles.get(1).getText();
		int secondMonth = Month.getMonth(currentSecondMonthYear.split(" ")[0]);
		Button btnClick = null;
		while (month != firstMonth && month != secondMonth) {
			if (month < firstMonth) {
				btnClick = btnNextMonth;
				firstMonth = Month.getMonth(eleDatePicker1.getText().split(" ")[0]);
			} else if (month > secondMonth) {
				btnClick = btnPreviousMonth;
				secondMonth = Month.getMonth(eleDatePicker2.getText().split(" ")[0]);
			}
			btnClick.click();
		}
	}

    /**
     * Find and select a date in the calendar
     *
     * @param  type [in|out]
     *         in: select check-in date
     *         out: select check-out date
     * @param  date
     *         The specific date that should be selected (String)
     *
     */
	protected void selectDate(String type, String date) {
		if (!date.isEmpty()) {
			int year = Integer.parseInt(date.split("/")[2]);
			int month = Integer.parseInt(date.split("/")[1]);
			String strDateTime = DateTimeHelper.getDateString(DateTimeHelper.getDate(date, "dd/MM/yyyy"),
					"EEE MMM dd yyyy");
			selectYear(year);
			selectMonth(month);
			eleDate.generateDynamic(strDateTime).click();
			if (type.contentEquals("out")) {
				eleDate.waitForNotDisplayed(10);
			}
		}
	}

    /**
     * Select traveler type
     *
     * @param  type [Solo traveler|Couple/Pair|Family travelers|Group travelers|Business travelers]
     *         traveler type (String)
     *
     */
	protected void selectTravelerType(String type) {
		btnTravelingOption = btnTravelingOption.generateDynamic(TravelTypes.getName(type).getCode());
		btnTravelingOption.click();
	}

    /**
     * Select number of room or adult or children
     *
     * @param  field
     *         travel field such as room or adult or children (TravelFields)
     *
     *@param	number
     *			number of room or adult or children depends on the input field
     *
     */
	protected void selectNumber(TravelFields field, int number) {
		if (number != 0) {
			int currentNumber = Integer.parseInt(eleDisplayValueLbl.generateDynamic(field.getValue()).getText());

			Button btnClick = null;
			if (currentNumber < number) {
				btnClick = btnPlus.generateDynamic(field.getValue());
			} else if (currentNumber > number) {
				btnClick = btnMinus.generateDynamic(field.getValue());
			}
			while (currentNumber != number) {
				btnClick.click();
				currentNumber = Integer.parseInt(eleDisplayValueLbl.generateDynamic(field.getValue()).getText());
			}
		}
	}

    /**
     * Select age for each child
     *
     * @param  age
     *         age of a child (String)
     *
     */
	protected void selectChildAge(String age) {
		if (!age.isEmpty()) {
			if (!cbxChildAge.getSelectedOption().equals(age)) {
				cbxChildAge.selectByValue(age);
			}
		}
	}
	
    /**
     * Enter traveling info to search a suitable hotel
     *
     * @param  travel
     *         travel info (TravellingInfo)
     *
     */
	protected void enterTravelingInfo(RoomBooking travel) {
		enterSearchKeyWord(travel.getDestination());
		selectDate("in", travel.getCheckInDate());
		selectDate("out", travel.getCheckOutDate());
		selectTravelerType(travel.getTravelOption());
		selectNumber(TravelFields.ROOM, travel.getNumberOfRoom());
		selectNumber(TravelFields.ADULT, travel.getNumberOfAdult());
		selectNumber(TravelFields.CHILDREN, travel.getNumberOfChildren());
		if (travel.getChildrenAge().equals("<0") || Integer.parseInt(travel.getChildrenAge()) != 0) {
			selectChildAge(travel.getChildrenAge());
		}
	}
	
	/**
     * Select language
     *
     * @param  language
     *         language name to select (String)
     *
     */
	public void selectLanguage(String language) {
		btnLanguageMenu.click();
		eleLanguagePopup.waitForPresent(Constants.SHORT_TIME);
		btnLanguage.generateDynamic(language).click();
		eleLanguagePopup.waitForNotPresent(Constants.SHORT_TIME);
	}
		
    /**
     * scroll to top of page
     */
	public void scrollToTop() {
		DriverUtils.executeJavaScript("window.scrollTo(0, 0);");
	}
	
	/**
     * scroll to bottom of page
     */
	public void scrollToBottom() {
		DriverUtils.executeJavaScript("window.scrollTo(0, document.body.scrollHeight);");
	}

}
