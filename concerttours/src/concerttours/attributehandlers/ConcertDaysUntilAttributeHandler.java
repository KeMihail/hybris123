package concerttours.attributehandlers;

import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

import concerttours.model.ConcertModel;


@Component
public class ConcertDaysUntilAttributeHandler extends AbstractDynamicAttributeHandler<Long, ConcertModel>
{

	@Override
	public Long get(final ConcertModel model)
	{

		final ZonedDateTime concertDate = model.getDate().toInstant().atZone(ZoneId.systemDefault());
		final ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.systemDefault());

		if (concertDate.isBefore(currentDate))
		{
			return Long.valueOf(0L);
		}
		final Duration duration = Duration.between(currentDate, concertDate);

		return Long.valueOf(duration.toDays());
	}
}
