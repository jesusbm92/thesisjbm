package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ExerciseRepository;
import domain.Exercise;

@Component
@Transactional
public class StringToExerciseConverter implements Converter<String, Exercise> {

	@Autowired
	ExerciseRepository exerciseRepository;

	@Override
	public Exercise convert(String text) {
		Exercise result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = exerciseRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
