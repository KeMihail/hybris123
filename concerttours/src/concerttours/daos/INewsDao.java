package concerttours.daos;

import java.util.Date;
import java.util.List;

import concerttours.model.NewsModel;


public interface INewsDao
{
	List<NewsModel> getNewsOfTheDay(final Date date);
}
