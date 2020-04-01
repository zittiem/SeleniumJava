package pages.Agoda;

import datatype.Agoda.Enums.Month;
import datatype.Agoda.Enums.TravelFields;
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

public class GeneralPage extends TestBase{
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.getResource("appName"),
			getClass().getSimpleName());
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

	protected Button btnShowDestination = new Button(btnShowFields, FindBy.xpath, "search-box");
	protected Button btnShowCheckIn = new Button(btnShowFields, FindBy.xpath, "check-in");
	protected Button btnShowCheckOut = new Button(btnShowFields, FindBy.xpath, "check-out");
	protected Button btnShowTraveler = new Button(btnShowFields, FindBy.xpath, "travelers");

	protected Button btnDay = null;

	// Methods

	protected void enterDestination(String city) {
		if (!city.isEmpty()) {
			if (!txtDestination.isDisplayed()) {
				btnShowDestination.click();
			}
			txtDestination.enter(city);
		}
	}

	private void findYear(int year) {
		String currentFirtMonthYear = new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][1]").getText();
		int firstYear = Integer.parseInt(currentFirtMonthYear.split(" ")[2]);
		String currentSecondMonthYear = new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][2]").getText();
		int secondYear = Integer.parseInt(currentSecondMonthYear.split(" ")[2]);
		Button btnClick = null;
		while (year != firstYear && year != secondYear) {
			if (year < firstYear) {
				btnClick = btnNextMonth;
				firstYear = Integer.parseInt(
						new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][1]").getText().split(" ")[2]);
			} else if (year > secondYear) {
				btnClick = btnPreviousMonth;
				secondYear = Integer.parseInt(
						new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][1]").getText().split(" ")[2]);
			}
			btnClick.click();
		}
	}

	private void findMonth(int month) {
		String currentFirtMonthYear = new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][1]").getText();
		int firstMonth = Month.getMonthFromFullMonth(currentFirtMonthYear.split(" ")[1]);
		String currentSecondMonthYear = new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][2]").getText();
		int secondMonth = Month.getMonthFromFullMonth(currentSecondMonthYear.split(" ")[1]);
		Button btnClick = null;
		while (month != firstMonth && month != secondMonth) {
			if (month < firstMonth) {
				btnClick = btnNextMonth;
				firstMonth = Month.getMonthFromFullMonth(
						new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][1]").getText().split(" ")[1]);
			} else if (month > secondMonth) {
				btnClick = btnPreviousMonth;
				secondMonth = Month.getMonthFromFullMonth(
						new Element(FindBy.xpath, "//div[@class='DayPicker-Caption'][2]").getText().split(" ")[1]);
			}
			btnClick.click();
		}
	}

	protected void selectDate(String type, String date) {
		// Handle input date as "".
		if (!date.isEmpty()) {
			int year = Integer.parseInt(date.split("/")[3]);
			int month = Integer.parseInt(date.split("/")[2]);
			String strDateTime = DateTimeHelper.getDateString(
					DateTimeHelper.getDate(date, ResourceHelper.getResource("simple_date_format")),
					ResourceHelper.getResource("datetime_format"));
			System.out.println(year);
			System.out.println(month);
			System.out.println(strDateTime);
			// Handle show select date field

			if (type.equals("in")) {
				if (!calCheckIn.isDisplayed()) {
					btnShowCheckIn.click();
				}
			} else if (type.equals("out")) {
				if (!calCheckOut.isDisplayed()) {
					btnShowCheckOut.click();
				}
			}
			findYear(year);
			findMonth(month);
			new Element(FindBy.xpath, String.format("//div[@aria-label='%s']", strDateTime)).click();
		}

	}

	protected void selectTravelerType(String type) {
		Element eleTraveler = btnTravelingOption.generateDynamic(type);
		if (!eleTraveler.isDisplayed()) {
			btnShowTraveler.click();
		}
		eleTraveler.click();
	}

	protected void selectNumber(TravelFields field, int number) {
		if (number != 0) {
			int currentNumber = Integer.parseInt(lblDisplayValue.generateDynamic(field.getValue()).getText());
			Button btnClick = null;
			if (currentNumber < number) {
				btnClick = btnMinus.generateDynamic(field.getValue());
			} else if (currentNumber > number) {
				btnClick = btnPlus.generateDynamic(field.getValue());
			}
			while (currentNumber != number) {
				btnClick.click();
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
		selectChildAge(travel.getChildrenAge());
	}

	public void searchHoltel(TravellingInfo travel) {
		enterTravelingInfo(travel);
		btnSearch.click();
	}

	// Enum Helper

}
