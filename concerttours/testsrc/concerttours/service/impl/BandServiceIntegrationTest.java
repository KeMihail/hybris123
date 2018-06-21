package concerttours.service.impl;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.impex.jalo.ImpExException;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import concerttours.model.BandModel;


@IntegrationTest
public class BandServiceIntegrationTest extends ServicelayerTransactionalTest
{

	@Resource
	private ModelService modelService;
	@Resource
	private BandServiceImpl bandServiceImpl;

	private BandModel bandModel;

	private final String code = "code";
	private final String name = "name";
	private final String history = "history";
	private final Long albumSales = 20L;

	@Before
	public void setUp() throws ImpExException
	{
		//importCsv("df.impex", "UTF-8");
		bandModel = modelService.create(BandModel.class);
		bandModel.setCode(code);
		bandModel.setHistory(history);
		bandModel.setAlbumSales(albumSales);
	}

	@Test
	public void testBandService()
	{

		List<BandModel> bands = bandServiceImpl.getAllBands();
		final Integer size = bands.size();
		modelService.save(bandModel);

		bands = bandServiceImpl.getAllBands();

		Assert.assertEquals(size + 1, bands.size());
		Assert.assertTrue(bands.contains(bandModel));

		final BandModel band = bandServiceImpl.getBandForCode(code);
		Assert.assertNotNull(band);
		Assert.assertEquals(code, band.getCode());
	}
}
