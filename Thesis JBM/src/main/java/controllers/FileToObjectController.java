package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utilities.FileToObjectsConverter;

import domain.Exam;
import domain.Exercise;
import domain.User;
import forms.FileForm;

@Controller
@RequestMapping("/converter")
public class FileToObjectController {

	
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		
		FileForm fileform = new FileForm();

		result = createCreateModelAndView(fileform);
		
		return result;
	}
	
	
	@RequestMapping(value = "/select", method = RequestMethod.POST, params = "save")
	public ModelAndView convert(@Valid FileForm fileform, BindingResult binding,
			RedirectAttributes redirect) {
		ModelAndView result;

		if (binding.hasErrors()) {

				result = createCreateModelAndView(fileform, "exam.commit.error");
			
		} else {
			try {
				String [] array = {"controller", fileform.getDirectory()};
				FileToObjectsConverter.main(array);
				redirect.addFlashAttribute("successMessage", "metadata.deleteSuccess");
				result = new ModelAndView("redirect:select.do");
			} catch (Throwable oops) {
				
					result = createCreateModelAndView(fileform, "exam.commit.error");
				

			}

		}

		return result;
	}
	
	
	
	
	protected ModelAndView createCreateModelAndView(FileForm fileform) {
		assert fileform != null;

		ModelAndView result;

		result = createCreateModelAndView(fileform, null);
		return result;
	}
	
	
	
	protected ModelAndView createCreateModelAndView(FileForm fileform, String message) {
		assert fileform != null;
		
		ModelAndView result;
		result = new ModelAndView("converter/select");
		result.addObject("fileform", fileform);
		result.addObject("message", message);

		return result;
	}
	
	
	
}
