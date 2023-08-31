package hello.typeconvert.converter;

import org.springframework.core.convert.converter.Converter;

import hello.typeconvert.type.IpPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IpPortToStringConverter implements Converter<IpPort, String> {

	@Override
	public String convert(IpPort source) {
		log.info("convert source = {}", source);
		// IpPort 객체를 문자로
		return source.getIp() + ":" + source.getPort();
	}


}
