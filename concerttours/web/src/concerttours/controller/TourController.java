package concerttours.controller;

import de.hybris.platform.catalog.CatalogVersionService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import concerttours.data.TourData;
import concerttours.facades.ITourFacade;


@Controller
public class TourController
{

	private ITourFacade tourFacade;
	private CatalogVersionService catalogVersionService;

	private static final String CATALOG_ID = "Default";
	private static final String VERSION_NAME = "Staged";

	@RequestMapping(value = "/tours/{tourId}")
	public String tourDetails(@PathVariable final String id, final Model model) throws UnsupportedEncodingException
	{
		catalogVersionService.setSessionCatalogVersion(CATALOG_ID, VERSION_NAME);
		final String tourId = URLDecoder.decode(id, "UTF-8");
		final TourData tour = tourFacade.getTour(tourId);
		model.addAttribute("tour", tour);

		return "TourDetails";
	}

	@Autowired
	public void setTourFacade(final ITourFacade tourFacade)
	{
		this.tourFacade = tourFacade;
	}

	@Autowired
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}
}
