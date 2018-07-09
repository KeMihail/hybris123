package concerttours.events;

import de.hybris.platform.servicelayer.event.ClusterAwareEvent;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;


public class BandAlbumSalesEvent extends AbstractEvent implements ClusterAwareEvent
{
	private String code;
	private String bandName;
	private Long albumSales;

	public BandAlbumSalesEvent(final String code, final String bandName, final Long albumSales)
	{
		super();
		this.code = code;
		this.bandName = bandName;
		this.albumSales = albumSales;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(final String code)
	{
		this.code = code;
	}

	public String getBandName()
	{
		return bandName;
	}

	public void setBandName(final String bandName)
	{
		this.bandName = bandName;
	}

	public Long getAlbumSales()
	{
		return albumSales;
	}

	public void setAlbumSales(final Long albumSales)
	{
		this.albumSales = albumSales;
	}

	@Override
	public boolean publish(final int sourseNodeId, final int targetNodeId)
	{
		return (sourseNodeId == targetNodeId);
	}
}
