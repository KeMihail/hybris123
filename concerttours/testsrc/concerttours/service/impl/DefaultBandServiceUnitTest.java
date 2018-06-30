package concerttours.service.impl;

import de.hybris.bootstrap.annotations.UnitTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import concerttours.daos.impl.BandDaoImpl;
import concerttours.model.BandModel;


@UnitTest
public class DefaultBandServiceUnitTest
{

	private BandServiceImpl bandService;
	private BandDaoImpl bandDao;
	private BandModel band;

	private final String code = "code";
	private final String name = "name";
	private final String history = "hystory";
	private final Long albumSales = 100L;


	@Before
	public void setUp()
	{
		bandDao = Mockito.mock(BandDaoImpl.class);
		bandService = new BandServiceImpl();
		bandService.setBandDaoImpl(bandDao);

		band = new BandModel();
		band.setCode(code);
		band.setName(name);
		band.setHistory(history);
		band.setAlbumSales(albumSales);
	}

	@Test
	public void testFindBandForCode()
	{
		Mockito.when(bandDao.getBandForCode(code)).thenReturn(Collections.singletonList(band));
		Mockito.when(bandDao.getAllBands()).thenReturn(Arrays.asList(band));

		final BandModel persistance = bandService.getBandForCode(code);
		Assert.assertNotNull(persistance);
		Assert.assertEquals(band, persistance);

		final List<BandModel> bands = bandDao.getAllBands();
		Assert.assertNotNull(bands);
		Assert.assertEquals(1, bands.size());
		Assert.assertTrue(bands.contains(band));
	}
}
