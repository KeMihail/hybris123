package concerttours.daos;

import java.util.List;

import concerttours.model.BandModel;


public interface IBandDao
{

	List<BandModel> getBandForCode(final String code);

	List<BandModel> getAllBands();
}
