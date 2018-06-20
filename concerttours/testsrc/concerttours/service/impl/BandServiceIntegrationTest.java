package concerttours.service.impl;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;


@IntegrationTest
public class BandServiceIntegrationTest extends ServicelayerTransactionalTest
{

	@Resource
	private ModelService modelService;
}
