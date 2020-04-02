package pages.Agoda;

import java.util.List;

import datatype.Agoda.Enums.Month;
import datatype.Agoda.Enums.TravelFields;
import datatype.Agoda.Enums.TravelTypes;
import datatype.Agoda.TravellingInfo;
import element.base.web.Element;
import element.setting.FindBy;
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
			getClass().getSimpleName());

	// Static Elements
	protected Button btnShowFields = new Button(locator.getLocator("btnShowFields"));
	protected TextBox txtDestination = new TextBox(locator.getLocator("txtDestination"));
	protected Element calCheckIn = new Element(locator.getLocator("calCheckIn"));
	protected Element calCheckOut = new Element(locator.getLocator("calCheckOut"));
	protected Button btnTravelingOption = new Button(locator.getLocator("btnTravelingOption"));
	protected Button btnMinus = new Button(locator.getLocator("btnMinus"));
	protected Button btnPlus = new Button(locator.getLocator("btnPlus"));
	protected Element lblDisplayValue = new Element(locator.getLocator("lblDisplayValue"));
	protected Button btnSearch = new Button(locator.getLocator("btnSearch"));
	protected Button btnPreviousMonth = new Button(locator.getLocator("btnPreviousMonth"));
	protected Button btnNextMonth = new Button(locator.getLocator("btnNextMonth"));
	protected DropDown cbxChildAge = new DropDown(locator.getLocator("cbxChildAge"));

	// Dynamic Elements

	protected Button btnShowDestination = btnShowFields.generateDynamic("search-box");
	protected Button btnShowCheckIn = btnShowFields.generateDynamic("check-in");
	protected Button btnShowCheckOut = btnShowFields.generateDynamic("check-out");
	protected Button btnShowTraveler = btnShowFields.generateDynamic("travelers");

	// Methods
	protected void enterDestination(String city) {
		if (!city.isEmpty()) {
			btnShowDestination.click();
			txtDestination.enter(city);
			Element eleSearchPopup = new Element(FindBy.xpath, "//li[@data-element-place-type=1]");
			eleSearchPopup.click();
		}
	}

	private void findYear(int year) {
		List<Element> eles = new Element(FindBy.xpath, "//div[@class='DayPicker-Caption']").getWrapperElements();
		eles.get(0).waitForDisplayed(Constants.SHORT_TIME);
		String currentFirtMonthYear = eles.get(0).getText();
		int firstYear = Integer.parseInt(currentFirtMonthYear.split(" ")[1]);
		String currentSecondMonthYear = eles.get(1).getText();
		int secondYear = Integer.parseInt(currentSecondMonthYear.split(" ")[1]);
		Button btnClick = null;
		while (year != firstYear && year != secondYear) {
			if (year < firstYear) {
				btnClick = btnNextMonth;
				firstYear = Integer.parseInt(
						new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][1]").getText().split(" ")[1]);
			} else if (year > secondYear) {
				btnClick = btnPreviousMonth;
				secondYear = Integer.parseInt(
						new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][1]").getText().split(" ")[1]);
			}
			btnClick.click();
		}
	}

	private void findMonth(int month) {
		List<Element> eles = new Element(FindBy.xpath, "//div[@class='DayPicker-Caption']").getWrapperElements();
		String currentFirtMonthYear = eles.get(0).getText();
		int firstMonth = Month.getMonth(currentFirtMonthYear.split(" ")[0]);
		String currentSecondMonthYear = eles.get(1).getText();
		int secondMonth = Month.getMonth(currentSecondMonthYear.split(" ")[0]);
		Button btnClick = null;
		while (month != firstMonth && month != secondMonth) {
			if (month < firstMonth) {
				btnClick = btnNextMonth;
				firstMonth = Month.getMonth(
						new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][1]").getText().split(" ")[0]);
			} else if (month > secondMonth) {
				btnClick = btnPreviousMonth;
				secondMonth = Month.getMonth(
						new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][2]").getText().split(" ")[0]);
			}
			btnClick.click();
		}
	}

	protected void selectDate(String type, String date) {
		if (!date.isEmpty()) {
			int year = Integer.parseInt(date.split("/")[2]);
			int month = Integer.parseInt(date.split("/")[1]);
			String strDateTime = DateTimeHelper.getDateString(DateTimeHelper.getDate(date, "dd/MM/yyyy"),
					"EEE MMM dd yyyy");
			findYear(year);
			findMonth(month);
			Element eleDate = new Element(FindBy.xpath, String.format("//div[@aria-label='%s']", strDateTime));
			eleDate.click();
			if (type.contentEquals("out")) {
				eleDate.waitForNotDisplayed(10);
			}
		}
	}

	protected void selectTravelerType(String type) {
		Element eleTraveler = btnTravelingOption.generateDynamic(TravelTypes.getName(type).getCode());
		eleTraveler.click();
	}

	protected void selectNumber(TravelFields field, int number) {
		if (number != 0) {
			int currentNumber = Integer.parseInt(lblDisplayValue.generateDynamic(field.getValue()).getText());

			Button btnClick = null;
			if (currentNumber < number) {
				btnClick = btnPlus.generateDynamic(field.getValue());
			} else if (currentNumber > number) {
				btnClick = btnMinus.generateDynamic(field.getValue());
			}
			while (currentNumber != number) {
				btnClick.click();
				currentNumber = Integer.parseInt(lblDisplayValue.generateDynamic(field.getValue()).getText());
			}
		}
	}

	protected void selectChildAge(String age) {
		if (!age.isEmpty()) {
			if (!cbxChildAge.getSelectedOption().equals(age)) {
				cbxChildAge.selectByValue(age);
			}
		}
	}

	protected void enterTravelingInfo(TravellingInfo travel) {
		enterDestination(travel.getDestination());
		selectDate("in", travel.getCheckInDate());
		selectDate("out", travel.getCheckOutDate());
		selectTravelerType(travel.getTravelOption());
		selectNumber(TravelFields.ROOM, travel.getNumberOfRoom());
		selectNumber(TravelFields.ADULT, travel.getNumberOfChildren());
		selectNumber(TravelFields.CHILDREN, travel.getNumberOfChildren());
		if (travel.getChildrenAge().equals("<0") || Integer.parseInt(travel.getChildrenAge()) != 0) {
			selectChildAge(travel.getChildrenAge());
		}
	}

	public void searchHoltel(TravellingInfo travel) {
		enterTravelingInfo(travel);
		btnSearch.click();
	}
}
