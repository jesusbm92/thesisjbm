package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MetadataRepository;
import domain.Metadata;

@Component
@Transactional
public class StringToMetadataConverter implements Converter<String, Metadata> {

	@Autowired
	MetadataRepository metadataRepository;

	@Override
	public Metadata convert(String text) {
		Metadata result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = metadataRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
