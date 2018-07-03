package concerttours.facades;

import de.hybris.bootstrap.annotations.UnitTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import concerttours.data.BandData;
import concerttours.facades.impl.DefaultBandFacade;
import concerttours.model.BandModel;
import concerttours.service.impl.BandServiceImpl;


@UnitTest
public class BandFacadeUnitTest
{
	private BandServiceImpl bandServiceImpl;
	private DefaultBandFacade bandFacade;

	private static final String BAND_CODE = "code";
	private static final String BAND_NAME = "name";
	private static final String BAND_HISTORY = "history";
	private static final Long ALBUM_SALES = 500L;
	private BandModel band;

	@Before
	public void setUp()
	{
		band = new BandModel();
		band.setCode(BAND_CODE);
		band.setName(BAND_NAME);
		band.setHistory(BAND_HISTORY);
		band.setAlbumSales(ALBUM_SALES);

		bandServiceImpl = Mockito.mock(BandServiceImpl.class);
		bandFacade = new DefaultBandFacade();
		bandFacade.setBandServiceImpl(bandServiceImpl);
	}

	@Test
	public void testGetBands()
	{
		Mockito.when(bandServiceImpl.getAllBands()).thenReturn(Arrays.asList(band));

		final List<BandData> bands = bandFacade.getBands();
		Assert.assertNotNull(bands);
		final BandData bandData = bands.get(0);

		Assert.assertEquals(BAND_CODE, bandData.getId());
		Assert.assertEquals(BAND_NAME, bandData.getName());
		Assert.assertEquals(BAND_HISTORY, bandData.getDescription());
		Assert.assertEquals(ALBUM_SALES, bandData.getAlbumSold());
	}

	public void testGetBand()
	{
		Mockito.when(bandServiceImpl.getBandForCode(BAND_CODE)).thenReturn((BandModel) Collections.singleton(band));

		final BandData bandData = bandFacade.getBand(BAND_CODE);
		Assert.assertNotNull(bandData);
		
		Assert.assertEquals(BAND_CODE, bandData.getId());
		Assert.assertEquals(BAND_NAME, bandData.getName());
		Assert.assertEquals(BAND_HISTORY, bandData.getDescription());
		Assert.assertEquals(ALBUM_SALES, bandData.getAlbumSold());
	}
}
