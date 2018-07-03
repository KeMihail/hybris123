package concerttours.service;

import java.util.List;

import concerttours.model.BandModel;


public interface IBandService
{

	List<BandModel> getAllBands();

	BandModel getBandForCode(final String code);
}
