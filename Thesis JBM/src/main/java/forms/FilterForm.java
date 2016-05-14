package forms;

import org.hibernate.validator.constraints.NotBlank;

public class FilterForm {
	
	private String filter;
	private String search;
	
	
	public FilterForm() {
		super();
	}

	@NotBlank
	public String getFilter() {
		return filter;
	}


	public void setFilter(String filter) {
		this.filter = filter;
	}

	@NotBlank
	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}
	
	
	
	

}
