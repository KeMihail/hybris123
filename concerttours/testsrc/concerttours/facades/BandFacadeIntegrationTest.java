package concerttours.facades;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.impex.jalo.ImpExException;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import concerttours.data.BandData;
import concerttours.data.TourSummaryData;
import concerttours.model.BandModel;
import concerttours.service.IBandService;



@IntegrationTest
public class BandFacadeIntegrationTest extends ServicelayerTransactionalTest
{
	@Resource
	private IBandService bandServiceImpl;
	@Resource
	private IBandFacades defaultBandFacade;

	@Before
	public void setUp() throws ImpExException
	{
		importCsv("/impex/essentialdata-musictypes.impex", "UTF-8");
		importCsv("/impex/concerttours-bands.impex", "UTF-8");
		importCsv("/impex/concerttour-bandmusictypes.impex", "UTF-8");
		importCsv("/impex/concerttours-yBandTour.impex", "UTF-8");

	}

	@Test(expected = UnknownIdentifierException.class)
	public void testUnknownParametr()
	{
		final BandData facade = defaultBandFacade.getBand("unknown");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullParametr()
	{
		final BandData facade = defaultBandFacade.getBand(null);
	}

	@Test
	public void testGetBandForCode()
	{
		final BandData facade = defaultBandFacade.getBand("B001");
		Assert.assertNotNull(facade);

		Assert.assertEquals("B001", facade.getId());
		Assert.assertEquals("yRock", facade.getName());
		Assert.assertEquals("Occasional tribute rock band", facade.getDescription());
		Assert.assertEquals(Long.valueOf(1000000), facade.getAlbumSold());

		final List<String> ganres = new ArrayList<>();
		ganres.add("pop");
		ganres.add("jazz");
		ganres.add("classical");

		Assert.assertEquals(ganres, facade.getGenres());

		final List<TourSummaryData> tour = new ArrayList<>();

		final BandModel band = bandServiceImpl.getBandForCode("B001");

		Assert.assertEquals(band.getTour().size(), facade.getTours().size());
		Assert.assertEquals(band.getTour().get(0).getCode(), facade.getTours().get(0).getId());
		Assert.assertEquals(band.getTour().get(0).getName(), facade.getTours().get(0).getTourName());
		Assert.assertEquals(Integer.toString(band.getTour().get(0).getVariants().size()),
				facade.getTours().get(0).getNumberOfConcerts());
	}

	@Test
	public void testGetBands()
	{
		final List<BandData> bands = defaultBandFacade.getBands();
		Assert.assertNotNull(bands);
		Assert.assertEquals(7, bands.size());
	}
}
