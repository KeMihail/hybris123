package concerttours.interceptors;

import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import concerttours.events.BandAlbumSalesEvent;
import concerttours.model.BandModel;


public class BandAlbumSalesInterceptor implements ValidateInterceptor<BandModel>, PrepareInterceptor<BandModel>
{
	@Autowired
	private EventService eventService;

	private static final Long BIG_SALES = 50000L;
	private static final Long NEGATIV_SALES = 0L;

	@Override
	public void onValidate(final BandModel model, final InterceptorContext ctx) throws InterceptorException
	{
		if (model.getAlbumSales() < NEGATIV_SALES)
		{
			throw new InterceptorException("Sales can not be less than zero");
		}
	}

	@Override
	public void onPrepare(final BandModel model, final InterceptorContext ctx) throws InterceptorException
	{
		if (model.getAlbumSales() > BIG_SALES)
		{
			eventService.publishEvent(new BandAlbumSalesEvent(model.getCode(), model.getName(), model.getAlbumSales()));
		}
	}

	@Required
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

}
