package controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MetadataService;
import domain.Metadata;
import domain.Exercise;


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

	
	// Other bussiness method
//		protected ModelAndView createEditModelAndView(Metadata metadata,
//				String message) {
//			assert metadata != null;
//			ModelAndView result;
//			result = new ModelAndView("exercise/administrator/edit");
//			result.addObject("exercise", exercise);
//			result.addObject("message", message);
//			result.addObject("create", false);
//			result.addObject("languages", Language.values());
//			result.addObject("map", map);
//
//			return result;
//		}

//		protected ModelAndView createCreateModelAndView(Metadata metadata,
//				String message) {
//			assert exercise != null;
//			Map<String, Integer> map = muscleService.findAllIdName();
//			ModelAndView result;
//			result = new ModelAndView("exercise/administrator/create");
//			result.addObject("exercise", exercise);
//			result.addObject("map", map);
//			result.addObject("message", message);
//			result.addObject("languages", Language.values());
//			result.addObject("create", true);
//
//			return result;
//		}

		protected ModelAndView createListModelAndView(String requestURI,
				Collection<Metadata> metadatas, String uri) {
			ModelAndView result;

			result = new ModelAndView(uri);
			result.addObject("metadatas", metadatas);
			result.addObject("requestURI", requestURI);

			return result;
		}
		
		protected ModelAndView createXMLModelAndView(String requestURI,
				Metadata metadata, String uri) {
			ModelAndView result;

			result = new ModelAndView(uri);
			result.addObject("metadata", metadata);
			result.addObject("requestURI", requestURI);

			return result;
		}
	
	
}
