package concerttours.facades.impl;

import de.hybris.platform.core.model.product.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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


		final BandModel bandModel = bandServiceImpl.getBandForCode(bandId);
		if (bandModel == null)
		{
			return null;
		}

		final BandData bandFacade = new BandData();

		final List<String> genres = new ArrayList<>();

		if (bandModel.getMusicType() != null)
		{

			for (final MusicType type : bandModel.getMusicType())
			{
				genres.add(type.getCode());
			}
		}

		final List<TourSummaryData> tours = new ArrayList<>();


		if (bandModel.getTour() != null)
		{

			for (final ProductModel item : bandModel.getTour())
			{
				final TourSummaryData tourSummary = new TourSummaryData();
				tourSummary.setId(item.getCode());
				tourSummary.setTourName(item.getName(Locale.ENGLISH));
				tourSummary.setNumberOfConcerts(Integer.toString(item.getVariants().size()));

				tours.add(tourSummary);
			}
		}

		bandFacade.setId(bandModel.getCode());
		bandFacade.setName(bandModel.getName());
		bandFacade.setDescription(bandModel.getHistory());
		bandFacade.setAlbumSold(bandModel.getAlbumSales());
		bandFacade.setGenres(genres);
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
