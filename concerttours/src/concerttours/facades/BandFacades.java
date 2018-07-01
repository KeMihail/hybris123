package concerttours.facades;

import java.util.List;

import concerttours.data.BandData;


public interface BandFacades
{
	BandData getBand(final String bandId);

	List<BandData> getBands();
}
