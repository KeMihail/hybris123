package concerttours.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;

import concerttours.daos.INewsDao;
import concerttours.model.NewsModel;
import concerttours.service.INewsService;


public class NewsServiceImpl implements INewsService
{
	@Resource
	private INewsDao newsDaoImpl;

	@Override
	public List<NewsModel> findNewsForData(final Date date)
	{
		final List<NewsModel> news = newsDaoImpl.findNewsForData(date);

		if (date == null)
		{
			throw new IllegalArgumentException("wrong date !");
		}

		if (news.isEmpty())
		{
			return null;
		}

		return news;
	}

	@Required
	public void setNewsDaoImpl(final INewsDao newsDaoImpl)
	{
		this.newsDaoImpl = newsDaoImpl;
	}

}
