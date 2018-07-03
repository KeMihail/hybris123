package concerttours.facades;

import java.util.List;

import concerttours.data.BandData;


public interface IBandFacades
{
	BandData getBand(final String bandId);

	List<BandData> getBands();
}
