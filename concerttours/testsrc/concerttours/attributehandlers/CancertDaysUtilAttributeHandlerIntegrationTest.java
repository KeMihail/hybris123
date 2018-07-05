package concerttours.attributehandlers;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import concerttours.model.ConcertModel;


@IntegrationTest
public class CancertDaysUtilAttributeHandlerIntegrationTest extends ServicelayerTransactionalTest
{
	@Resource
	private ModelService modelService;

	private ConcertModel concert;

	@Before
	public void setUp()
	{
		concert = modelService.create(ConcertModel.class);
	}

	@Test
	public void testGetFutureDate()
	{
		final Date concertDate = new Date(new Date().getTime() + 49 * 60 * 60 * 1000);
		concert.setDate(concertDate);

		Assert.assertEquals(2L, concert.getDaysUntil().longValue());
	}

	@Test
	public void testGetNullConcertDate()
	{
		Assert.assertNull(concert.getDaysUntil());
	}

	@Test
	public void testGetPastDate()
	{
		final Date concertDate = new Date(new Date().getTime() - 49 * 60 * 60 * 1000);
		concert.setDate(concertDate);

		Assert.assertEquals(0L, concert.getDaysUntil().longValue());
	}
}
