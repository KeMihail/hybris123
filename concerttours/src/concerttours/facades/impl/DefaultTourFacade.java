package concerttours.facades.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;

import concerttours.data.ConcertSummaryData;
import concerttours.data.TourData;
import concerttours.facades.ITourFacade;
import concerttours.model.ConcertModel;


public class DefaultTourFacade implements ITourFacade
{
	@Resource
	private ProductService productService;

	@Override
	public TourData getTour(final String tourId)
	{

		if (tourId == null)
		{
			throw new IllegalArgumentException("Tour id cannot be null");
		}

		final TourData tour = new TourData();
		final ProductModel product = productService.getProductForCode(tourId);

		if (product == null)
		{
			return null;
		}

		tour.setId(product.getCode());
		tour.setDescription(product.getDescription());
		tour.setTourName(product.getName());

		final List<ConcertSummaryData> concerts = new ArrayList<>();

		if (product.getVariants() != null)
		{
			for (final VariantProductModel item : product.getVariants())
			{
				if (item instanceof ConcertModel)
				{
					final ConcertModel concert = (ConcertModel) item;
					final ConcertSummaryData concertSummary = new ConcertSummaryData();
					concertSummary.setDate(new Date());
					concertSummary.setId(concert.getCode());
					concertSummary.setVenue(concert.getVenue());
					concertSummary.setType(concert.getConcertType().getCode());
					concertSummary.setCountDown(concert.getDaysUntil());

					concerts.add(concertSummary);
				}
			}
		}

		tour.setConcerts(concerts);

		return tour;
	}

	@Required
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

}
