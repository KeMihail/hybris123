package concerttours.facades;

import concerttours.data.TourData;

public interface TourFacade
{
	TourData getTour(final String tourId);
}
