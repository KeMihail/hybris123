package concerttours.service;

import java.util.Date;
import java.util.List;

import concerttours.model.NewsModel;


public interface INewsService
{
	List<NewsModel> findNewsForData(final Date date);
}
