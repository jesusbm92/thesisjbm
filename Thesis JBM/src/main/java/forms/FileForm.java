package forms;

import java.io.File;

import org.hibernate.validator.constraints.NotBlank;

public class FileForm {
	
	private String directory;
	
	

	public FileForm() {
		super();
		// TODO Auto-generated constructor stub
	}



	@NotBlank
	public String getDirectory() {
		return directory;
	}



	public void setDirectory(String directory) {
		this.directory = directory;
	}

	
	
	


}
