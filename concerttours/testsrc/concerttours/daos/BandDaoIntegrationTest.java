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
	private BandModel bandModel;
	@Resource
	private IBandDao bandDaoImpl;

	@Before
	public void setUp() throws Exception
	{
		bandModel = modelService.create(BandModel.class);
		bandModel.setCode(code);
		bandModel.setName(name);
		bandModel.setHistory(history);
		bandModel.setAlbumSales(albumSales);
		modelService.save(bandModel);
	}

	@Test
	public void testBandDaoTest()
	{
		final List<BandModel> bands = bandDaoImpl.getAllBands();
		Assert.assertTrue(!bands.isEmpty());
		Assert.assertEquals(1, bands.size());
		Assert.assertEquals(code, bands.get(0).getCode());
		Assert.assertEquals(name, bands.get(0).getName());
		Assert.assertEquals(history, bands.get(0).getHistory());
		Assert.assertEquals(albumSales, bands.get(0).getAlbumSales());

	}


}
