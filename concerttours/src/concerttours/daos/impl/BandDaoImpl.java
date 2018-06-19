package concerttours.daos.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;

import concerttours.daos.IBandDao;
import concerttours.model.BandModel;


public class BandDaoImpl implements IBandDao
{
	@Resource
	private FlexibleSearchService flexibleSearchService;

	@Required
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	@Override
	public List<BandModel> getBandForCode(final String code)
	{
		final String stringQuery = "Select {b:" + BandModel.PK + "} from {" + BandModel._TYPECODE + " AS b} where {b:"
				+ BandModel.CODE + "} = ?" + BandModel.CODE;
		final FlexibleSearchQuery query = new FlexibleSearchQuery(stringQuery);
		query.addQueryParameter(BandModel.CODE, code);

		return flexibleSearchService.<BandModel> search(query).getResult();
	}

	@Override
	public List<BandModel> getAllBands()
	{
		final String stringQuery = "Select {b:" + BandModel.PK + "} from {" + BandModel._TYPECODE + " AS b}";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(stringQuery);

		return flexibleSearchService.<BandModel> search(query).getResult();
	}

}
