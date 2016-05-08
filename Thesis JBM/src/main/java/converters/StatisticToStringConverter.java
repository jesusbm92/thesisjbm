package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Statistic;

@Component
@Transactional
public class StatisticToStringConverter implements Converter<Statistic, String> {

	@Override
	public String convert(Statistic statistic) {
		String result;

		if (statistic == null)
			result = null;
		else
			result = String.valueOf(statistic.getId());

		return result;
	}

}
