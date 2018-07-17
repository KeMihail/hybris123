package concerttours.daos.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import concerttours.daos.INewsDao;
import concerttours.model.NewsModel;


public class NewsDaoImpl implements INewsDao
{
	@Autowired
	private FlexibleSearchService flexibleSearchService;

	private static final String SQL_DATE_FORMAT = "yyyy-MM-dd";

	@Override
	public List<NewsModel> getNewsOfTheDay(final Date date)
	{

		if (date == null)
		{
			return Collections.emptyList();
		}

		final String theDay = new SimpleDateFormat(SQL_DATE_FORMAT).format(date);
		final String theNextDay = new SimpleDateFormat(SQL_DATE_FORMAT).format(oneDayAfter(date));

		final String stringQuery = "Select {n:" + NewsModel.PK + "} from {" + NewsModel._TYPECODE + " AS n} where {"
				+ NewsModel.DATE + "} >= DATE" + theDay + " AND " + NewsModel.DATE + " <= DATE" + theNextDay;

		final FlexibleSearchQuery query = new FlexibleSearchQuery(stringQuery);
		return flexibleSearchService.<NewsModel> search(query).getResult();
	}

	private Date oneDayAfter(final Date date)
	{
		final Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		instance.add(Calendar.DATE, 1);
		return instance.getTime();
	}
}
