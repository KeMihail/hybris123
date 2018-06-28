package concerttours.service.impl;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.impex.jalo.ImpExException;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.Collection;
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

	private final String code = "B001";
	private final String name = "name";
	private final String history = "history";
	private final Long albumSales = 20L;

	@Before
	public void setUp() throws ImpExException
	{
		bandModel = modelService.create(BandModel.class);
		bandModel.setCode(code);
		bandModel.setHistory(history);
		bandModel.setName(name);
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
		Assert.assertEquals(name, band.getName());
		Assert.assertEquals(history, band.getHistory());
		Assert.assertEquals(albumSales, band.getAlbumSales());
	}

	@Test
	public void testConcert() throws ImpExException
	{
		modelService.save(bandModel);
		importCsv("/impex/concerttours-yBandTour.impex", "UTF-8");

		final BandModel band = bandServiceImpl.getBandForCode("B001");
		Assert.assertNotNull(band);
		final List<ProductModel> result = band.getTour();

		final List<ProductModel> tours = band.getTour();
		Assert.assertEquals(tours.size(), 1);

		final Object[] objects = new Object[5];


		final Collection<VariantProductModel> concerts = ((ProductModel) tours.toArray(objects)[0]).getVariants();
		Assert.assertEquals(5, concerts.size());

	}

}
