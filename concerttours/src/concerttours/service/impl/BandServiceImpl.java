package concerttours.service.impl;

import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;

import concerttours.daos.impl.BandDaoImpl;
import concerttours.model.BandModel;
import concerttours.service.IBandService;


public class BandServiceImpl implements IBandService
{

	@Resource
	private BandDaoImpl bandDaoImpl;

	@Required
	public void setBandDaoImpl(final BandDaoImpl bandDaoImpl)
	{
		this.bandDaoImpl = bandDaoImpl;
	}

	@Override
	public List<BandModel> getAllBands()
	{
		return bandDaoImpl.getAllBands();
	}

	@Override
	public BandModel getBandForCode(final String code)
	{

		final List<BandModel> band = bandDaoImpl.getBandForCode(code);

		if (band.isEmpty())
		{
			throw new UnknownIdentifierException("Band with code: " + code + " does not exist");
		}

		if (band.size() > 1)
		{
			throw new AmbiguousIdentifierException("code: " + code + " not unique");
		}

		return bandDaoImpl.getBandForCode(code).get(0);
	}

}
