package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.MetadataService;
import domain.Metadata;
import domain.Question;


@Controller
@RequestMapping("/metadata")
public class MetadataController {

	
	// Services ----------------------------------------------------------------

	@Autowired
	private MetadataService metadataService;


	// Constructor
	// ---------------------------------------------------------------
	public MetadataController() {
		super();
	}

	// Listing
	// -------------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		String uri = "metadata/list";
		String requestURI = "metadata/list.do";
		Collection<Metadata> metadatas = metadataService.findAll();
		result = createListModelAndView(requestURI, metadatas, uri);

		return result;
	}
	
	// Creation
		// ------------------------------------------------------------------
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {

			ModelAndView result;

			Metadata metadata = metadataService.create();

			result = createCreateModelAndView(metadata);
			return result;
		}
		
		// Edition
		// -------------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int metadataId) {
			ModelAndView result;
			Metadata metadata = metadataService.findOne(metadataId);

			result = createEditModelAndView(metadata);

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Metadata metadata, BindingResult binding,
				RedirectAttributes redirect) {
			ModelAndView result;

			if (binding.hasErrors()) {
				if (metadata.getId() == 0) {
					result = createCreateModelAndView(metadata, "metadata.commit.error");
				} else {
					result = createCreateModelAndView(metadata, "metadata.commit.error");
				}
			} else {
				try {
					metadataService.save(metadata);
					result = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					if (metadata.getId() == 0) {
						redirect.addFlashAttribute("successMessage", "metadata.deleteSuccess");
						result = createCreateModelAndView(metadata, "metadata.commit.error");
					} else {
						result = createCreateModelAndView(metadata, "metadata.commit.error");
					}

				}

			}

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(@ModelAttribute Metadata metadata,
				BindingResult bindingResult, RedirectAttributes redirect) {
			ModelAndView result;

			try {
				redirect.addFlashAttribute("successMessage", "metadata.deleteSuccess");
				metadataService.delete(metadata);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				if (oops.getMessage() == "Error") {
					result = createEditModelAndView(metadata, "metadata.error");
				} else {
					result = createEditModelAndView(metadata, "metadata.commit.error");
				}
			}
			return result;
		}


	
	// Other bussiness method
		
		protected ModelAndView createEditModelAndView(Metadata metadata) {
			assert metadata != null;

			ModelAndView result;

			result = createEditModelAndView(metadata, null);

			return result;
		}

		protected ModelAndView createCreateModelAndView(Metadata metadata) {
			assert metadata != null;

			ModelAndView result;

			result = createCreateModelAndView(metadata, null);
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Metadata metadata,
				String message) {
			assert metadata != null;
			Collection<Question> questions = new ArrayList<Question>();
			ModelAndView result;
			result = new ModelAndView("metadata/edit");
			result.addObject("questions", questions);
			result.addObject("metadata", metadata);
			result.addObject("message", message);

			return result;
		}

		protected ModelAndView createCreateModelAndView(Metadata metadata,
				String message) {
			assert metadata != null;
			Collection<Question> questions = new ArrayList<Question>();
			ModelAndView result;
			result = new ModelAndView("metadata/create");
			result.addObject("questions", questions);
			result.addObject("create", true);
			result.addObject("metadata", metadata);
			result.addObject("message", message);

			return result;
		}

		protected ModelAndView createListModelAndView(String requestURI,
				Collection<Metadata> metadatas, String uri) {
			ModelAndView result;

			result = new ModelAndView(uri);
			result.addObject("metadatas", metadatas);
			result.addObject("requestURI", requestURI);

			return result;
		}
		
	
	
}
