package concerttours.setup;

import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.FileBasedImpExResource;
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource;

import java.io.File;
import java.io.InputStream;

import javax.annotation.Resource;


@SystemSetup(extension = "concerttours")
public class ConcerttoursCustomSetup
{

	@Resource
	private ImportService importService;

	public ImportService getImportService()
	{
		return importService;
	}

	public void setImportService(final ImportService importService)
	{
		this.importService = importService;
	}

	@SystemSetup(type = SystemSetup.Type.ESSENTIAL)
	public void addMyProjectData()
	{
		impexImport("/impex/concerttours-bands.impex");
		impexImport("/impex/concerttours-yBandTour.impex");
		impexImport("/impex/concerttour-bandmusictypes.impex");
	}

	protected Boolean impexImport(final String name)
	{
		try
		{

			final InputStream resourceAsStream = getClass().getResourceAsStream(name);
			final ImportConfig config = new ImportConfig();
			config.setScript(new StreamBasedImpExResource(resourceAsStream, "UTF-8"));
			config.setLegacyMode(Boolean.FALSE);

			final ImportResult importResult = getImportService().importData(config);

			if (importResult.isError())
			{
				return false;
			}
		}
		catch (final Exception e)
		{
			return false;
		}
		return true;
	}

	protected Boolean defaultImpexExport(final String name)
	{

		try
		{
			final File file = new File("C:\\hybris\\bin\\custom\\concerttours\\resources\\impex", name);
			final FileBasedImpExResource impExResource = new FileBasedImpExResource(file, "UTF-8");
			final ImportConfig config = new ImportConfig();
			config.setScript(impExResource);
			config.setLegacyMode(Boolean.FALSE);

			final ImportResult importResult = getImportService().importData(config);

			if (importResult.isError())
			{
				return false;
			}
		}
		catch (final Exception e)
		{
			return false;
		}
		return true;
	}
}
