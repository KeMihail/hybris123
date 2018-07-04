package concerttours.attributehandlers;

import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import concerttours.model.ConcertModel;


public class ConcertDaysUntilAttributeHandler implements DynamicAttributeHandler<Long, ConcertModel>
{

	@Override
	public Long get(final ConcertModel model)
	{
		if (model.getDate() == null)
		{
			return null;
		}

		final ZonedDateTime concertDate = model.getDate().toInstant().atZone(ZoneId.systemDefault());
		final ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.systemDefault());

		if (concertDate.isBefore(currentDate))
		{
			return Long.valueOf(0L);
		}
		final Duration duration = Duration.between(currentDate, concertDate);

		return Long.valueOf(duration.toDays());
	}

	@Override
	public void set(final ConcertModel model, final Long value)
	{

	}

}
