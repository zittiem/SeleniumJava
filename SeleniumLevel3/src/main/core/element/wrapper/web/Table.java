package element.wrapper.web;

import org.javatuples.Pair;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import element.base.web.Element;
import element.setting.FindBy;

public class Table extends Element {
	private static Logger logger = Logger.getLogger(Table.class);

	public Table(By locator) {
		super(locator);
	}
	
	public Table(String locator) {
		super(locator);
	}
	
	public Table(Element parentElement, String locator) {
		super(parentElement, locator);
	}
	
	public Table(String locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public Table(Element parentElement, String locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public Table(Pair<FindBy, String> locator) {
		super(locator);
	}
	
	public Table(Element parentElement, Pair<FindBy, String> locator) {
		super(parentElement, locator);
	}
	
	public Table(Pair<FindBy, String> locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public Table(Element parentElement, Pair<FindBy, String> locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public Table(FindBy by, String value) {
		super(by, value);
	}
	
	public Table(Element parentElement, FindBy by, String value) {
		super(parentElement, by, value);
	}

	public Table(FindBy by, String value, Object... arguments) {
		super(by, value, arguments);
	}
	
	public Table(Element parentElement, FindBy by, String value, Object... arguments) {
		super(parentElement, by, value, arguments);
	}
	
	public Table generateDynamic(Object... arguments)
	{
		super.generateDynamic(arguments);
		return this;
	}
	
	public List<Table> getWrapperTables() {
		return getWrapperElements(Table.class);
	}
}
