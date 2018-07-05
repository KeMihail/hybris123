package concerttours.events;

import de.hybris.platform.servicelayer.event.events.AfterItemCreationEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;

import concerttours.model.BandModel;
import concerttours.model.NewsModel;


public class NewBandEventListener extends AbstractEventListener<AfterItemCreationEvent>
{
	@Resource
	private ModelService modelService;

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	private NewsModel news;

	@Override
	protected void onEvent(final AfterItemCreationEvent event)
	{
		if (event != null && event.getSource() != null)
		{

			final Object object = modelService.get(event.getSource());
			if (object instanceof BandModel)
			{
				final BandModel band = (BandModel) object;
				news = modelService.create(NewsModel.class);
				news.setDate(new Date());
				news.setHeadline("header: " + band.getName());
				news.setContent("content: create new band" + band.getName());
				modelService.save(news);
			}
		}
	}



}
