package concerttours.events;

import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;

import concerttours.model.NewsModel;


public class BandAlbumSalesEventListener extends AbstractEventListener<BandAlbumSalesEvent>
{

	@Resource
	private ModelService modelService;

	private NewsModel news;

	@Override
	protected void onEvent(final BandAlbumSalesEvent event)
	{
		if (event != null)
		{
			news = modelService.create(NewsModel.class);
			news.setDate(new Date());
			news.setHeadline("good sales from " + event.getBandName());
			news.setContent("sales from band: " + event.getBandName() + " = " + event.getAlbumSales());
			modelService.save(news);
		}
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

}
