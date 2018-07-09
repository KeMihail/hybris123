package concerttours.daos.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;

import concerttours.daos.INewsDao;
import concerttours.model.NewsModel;


public class NewsDaoImpl implements INewsDao
{
	@Resource
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<NewsModel> findNewsForData(final Date date)
	{
		final String stringQuery = "Select {n:" + NewsModel.PK + "} from {" + NewsModel._TYPECODE + " AS n} where {"
				+ NewsModel.DATE + "} = ?date";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(stringQuery);
		query.addQueryParameter("code", date);
		return flexibleSearchService.<NewsModel> search(query).getResult();
	}

	@Required
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

}
