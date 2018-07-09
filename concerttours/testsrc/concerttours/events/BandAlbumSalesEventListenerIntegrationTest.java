package concerttours.events;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import concerttours.model.BandModel;
import concerttours.model.NewsModel;
import concerttours.service.IBandService;


@IntegrationTest
public class BandAlbumSalesEventListenerIntegrationTest extends ServicelayerTransactionalTest
{

	@Resource
	private ModelService modelService;

	@Resource
	private IBandService bandServiceImpl;

	@Resource
	private FlexibleSearchService flexibleSearchService;

	private BandModel band;

	private static final String BAND_CODE = "code";
	private static final String BAND_NAME = "name";
	private static final String BAND_HISTORY = "history";
	private static final Long ALBUM_SALES = 1000000L;

	@Before
	public void setUp() throws Exception
	{
		createCoreData();
		createDefaultCatalog();
		band = modelService.create(BandModel.class);
		band.setCode(BAND_CODE);
		band.setName(BAND_NAME);
		band.setHistory(BAND_HISTORY);
	}

	@Test(expected = ModelSavingException.class)
	public void testValidationInterceptor()
	{
		band.setAlbumSales(Long.valueOf(-10L));
		modelService.save(band);
	}

	@Test
	public void testEventSending()
	{
		band.setAlbumSales(ALBUM_SALES);
		modelService.save(band);

		final NewsModel news = findLastNews();
		Assert.assertNotNull(news);
		Assert.assertTrue(news.getContent().contains(BAND_NAME));
	}

	private NewsModel findLastNews()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT {n:").append(NewsModel.PK).append("} ");
		builder.append("FROM {").append(NewsModel._TYPECODE).append(" AS n} ");
		builder.append("ORDER BY ").append("{n:").append(NewsModel.CREATIONTIME).append("} DESC");

		final List<NewsModel> list = flexibleSearchService.<NewsModel> search(builder.toString()).getResult();

		if (list.isEmpty())
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}

	@Test
	public void testEventSendingAsync() throws InterruptedException
	{

		final String code = "code1";
		band.setCode(code);
		band.setAlbumSales(ALBUM_SALES);
		modelService.save(band);

		Thread.sleep(2000L);
		final NewsModel news = findLastNews();
		Assert.assertTrue(news.getContent().contains(BAND_NAME));
	}

}
