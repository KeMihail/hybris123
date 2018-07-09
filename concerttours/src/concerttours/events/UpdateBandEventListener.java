package concerttours.events;

import de.hybris.platform.core.PK;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.tx.AfterSaveEvent;
import de.hybris.platform.tx.AfterSaveListener;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;

import concerttours.model.BandModel;
import concerttours.model.NewsModel;


public class UpdateBandEventListener implements AfterSaveListener
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
	public void afterSave(final Collection<AfterSaveEvent> events)
	{
		for (final AfterSaveEvent event : events)
		{
			final PK pk = event.getPk();

			if (pk.getTypeCode() == 30283 && event.getType() == AfterSaveEvent.UPDATE)
			{

				final BandModel band = modelService.get(pk);
				news = modelService.create(NewsModel.class);
				news.setDate(new Date());
				news.setHeadline("update band");
				news.setContent("update band: " + band.getName());
				modelService.save(news);

			}
		}
	}
}
