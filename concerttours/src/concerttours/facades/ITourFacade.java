package concerttours.facades;

import concerttours.data.TourData;

public interface ITourFacade
{
	TourData getTour(final String tourId);
}
