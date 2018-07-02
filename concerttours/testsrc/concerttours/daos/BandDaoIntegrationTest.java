package concerttours.daos;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import concerttours.model.BandModel;


@IntegrationTest
public class BandDaoIntegrationTest extends ServicelayerTransactionalTest
{

	private final String code = "code";
	private final String name = "name";
	private final String history = "history";
	private final Long albumSales = 200L;

	@Resource
	private ModelService modelService;
	@Resource
	private IBandDao bandDaoImpl;

	private BandModel bandModel;

	@Before
	public void setUp()
	{
		bandModel = modelService.create(BandModel.class);
		bandModel.setCode(code);
		bandModel.setName(name);
		bandModel.setHistory(history);
		bandModel.setAlbumSales(albumSales);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnknownCode()
	{
		bandDaoImpl.getBandForCode(null);
	}

	@Test
	public void testNullCode()
	{
		final List<BandModel> bands = bandDaoImpl.getBandForCode(code);
		Assert.assertTrue(bands.isEmpty());
	}


	@Test
	public void testBandDaoTest()
	{
		List<BandModel> bands = bandDaoImpl.getAllBands();
		final Integer size = bands.size();

		modelService.save(bandModel);
		bands = bandDaoImpl.getAllBands();
		Assert.assertTrue(!bands.isEmpty());
		Assert.assertEquals(size + 1, bands.size());
		Assert.assertTrue(bands.contains(bandModel));

	}

	@Test
	public void testFindBandByCode()
	{
		modelService.save(bandModel);
		final List<BandModel> band = bandDaoImpl.getBandForCode(code);
		Assert.assertNotNull(band);
		Assert.assertEquals(1, band.size());
		Assert.assertEquals(code, band.get(0).getCode());
		Assert.assertEquals(name, band.get(0).getName());
		Assert.assertEquals(history, band.get(0).getHistory());
		Assert.assertEquals(albumSales, band.get(0).getAlbumSales());
	}
}
