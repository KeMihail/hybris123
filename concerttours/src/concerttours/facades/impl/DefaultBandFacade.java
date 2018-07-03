package concerttours.facades.impl;

import de.hybris.platform.core.model.product.ProductModel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;

import concerttours.data.BandData;
import concerttours.data.TourSummaryData;
import concerttours.enums.MusicType;
import concerttours.facades.IBandFacades;
import concerttours.model.BandModel;
import concerttours.service.IBandService;


public class DefaultBandFacade implements IBandFacades
{
	@Resource
	private IBandService bandServiceImpl;

	@Override
	public BandData getBand(final String bandId)
	{
		if (bandId == null)
		{
			throw new IllegalArgumentException("Band id cannot be null");
		}


		final BandModel bandService = bandServiceImpl.getBandForCode(bandId);
		if (bandService == null)
		{
			return null;
		}

		final BandData bandFacade = new BandData();

		final List<String> ganres = new ArrayList<>();
		final List<MusicType> bandGanres = bandService.getMusicType();

		for (final MusicType type : bandGanres)
		{
			ganres.add(type.getCode());
		}

		final List<TourSummaryData> tours = new ArrayList<>();

		if (bandService.getTour() != null)
		{

			for (final ProductModel item : bandService.getTour())
			{
				final TourSummaryData tourSummary = new TourSummaryData();
				tourSummary.setId(item.getCode());
				tourSummary.setTourName(item.getName());
				tourSummary.setNumberOfConcert(Long.valueOf(item.getVariants().size()));

				tours.add(tourSummary);
			}
		}

		bandFacade.setId(bandService.getCode());
		bandFacade.setName(bandService.getName());
		bandFacade.setDescription(bandService.getHistory());
		bandFacade.setAlbumSold(bandService.getAlbumSales());
		bandFacade.setGenres(ganres);
		bandFacade.setTours(tours);

		return bandFacade;
	}

	@Override
	public List<BandData> getBands()
	{
		final List<BandData> bands = new ArrayList<>();
		final List<BandModel> listBands = bandServiceImpl.getAllBands();

		for (final BandModel item : listBands)
		{

			final BandData band = new BandData();
			band.setId(item.getCode());
			band.setName(item.getName());
			band.setDescription(item.getHistory());
			band.setAlbumSold(item.getAlbumSales());

			bands.add(band);
		}
		return bands;
	}

	@Required
	public void setBandServiceImpl(final IBandService bandServiceImpl)
	{
		this.bandServiceImpl = bandServiceImpl;
	}
}
