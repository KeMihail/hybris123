package concerttours.attributehandlers;

import de.hybris.bootstrap.annotations.UnitTest;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import concerttours.model.ConcertModel;


@UnitTest
public class ConcertDaysUntilAttributeHandlerUnitTest
{
	private ConcertModel concert;
	private ConcertDaysUntilAttributeHandler handler;

	@Before
	public void setUp()
	{
		concert = new ConcertModel();
		handler = new ConcertDaysUntilAttributeHandler();
	}

	@Test
	public void testGetFutureDate()
	{
		final Date concertDate = new Date(new Date().getTime() + 49 * 60 * 60 * 1000);
		concert.setDate(concertDate);

		Assert.assertEquals(2L, handler.get(concert).longValue());
	}

	@Test
	public void testGetNullConcertDate()
	{
		Assert.assertNull(handler.get(concert));
	}

	@Test
	public void testGetPastDate()
	{
		final Date concertDate = new Date(new Date().getTime() - 49 * 60 * 60 * 1000);
		concert.setDate(concertDate);
		final ConcertDaysUntilAttributeHandler handler = new ConcertDaysUntilAttributeHandler();

		Assert.assertEquals(0L, handler.get(concert).longValue());
	}
}
