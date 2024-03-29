package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.StatisticRepository;
import domain.Statistic;

@Component
@Transactional
public class StringToStatisticConverter implements Converter<String, Statistic> {

	@Autowired
	StatisticRepository statisticRepository;

	@Override
	public Statistic convert(String text) {
		Statistic result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = statisticRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
