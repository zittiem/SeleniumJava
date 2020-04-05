package datatype.Agoda;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class FilterOptions {
	@SuppressWarnings("rawtypes")
	private Map<String, ArrayList> options = null;

	@SuppressWarnings("rawtypes")
	public FilterOptions(Map<String, ArrayList> options)
	{
		this.options = options;
	}
	
	/**
	 * @param options the options to set
	 */
	@SuppressWarnings("rawtypes")
	public void setOptions(Map<String, ArrayList> options) {
		this.options = options;
	}
	
	public Set<String> getCategories() {
		return options.keySet();
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList getOptions(String category) {
		return options.get(category);
	}
}
