package concerttours.facades;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.impex.jalo.ImpExException;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import concerttours.data.TourData;


@IntegrationTest
public class DefaultTourFacadesIntegrationTest extends ServicelayerTransactionalTest
{

	@Resource
	private ProductService productService;
	@Resource
	private ITourFacade defaultTourFacade;

	@Before
	public void setUp() throws ImpExException
	{
		importCsv("/impex/essentialdata-musictypes.impex", "UTF-8");
		importCsv("/impex/concerttours-bands.impex", "UTF-8");
		importCsv("/impex/concerttour-bandmusictypes.impex", "UTF-8");
		importCsv("/impex/concerttours-yBandTour.impex", "UTF-8");
	}

	@Test
	public void testTourFacade()
	{
		final TourData tour = defaultTourFacade.getTour("tour1");
		Assert.assertNotNull(tour);

		Assert.assertEquals("tour1", tour.getId());
		Assert.assertEquals("first tour", tour.getTourName());
		Assert.assertEquals("description", tour.getDescription());
		Assert.assertEquals(5, tour.getConcerts().size());
	}

}
