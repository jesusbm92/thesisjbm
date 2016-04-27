package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Metadata;

@Component
@Transactional
public class MetadataToStringConverter implements Converter<Metadata, String> {

	@Override
	public String convert(Metadata metadata) {
		String result;

		if (metadata == null)
			result = null;
		else
			result = String.valueOf(metadata.getId());

		return result;
	}

}
