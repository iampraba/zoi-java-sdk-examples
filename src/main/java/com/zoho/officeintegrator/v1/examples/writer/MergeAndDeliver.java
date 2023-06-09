package com.zoho.officeintegrator.v1.examples.writer;

import java.util.logging.Level;

import com.zoho.Initializer;
import com.zoho.UserSignature;
import com.zoho.api.authenticator.APIKey;
import com.zoho.api.logger.Logger;
import com.zoho.api.logger.Logger.Levels;
import com.zoho.dc.ZOIEnvironment;
import com.zoho.officeintegrator.v1.InvaildConfigurationException;
import com.zoho.officeintegrator.v1.MailMergeWebhookSettings;
import com.zoho.officeintegrator.v1.MergeAndDeliverViaWebhookParameters;
import com.zoho.officeintegrator.v1.MergeAndDeliverViaWebhookSuccessResponse;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.officeintegrator.v1.WriterResponseHandler;
import com.zoho.util.APIResponse;

public class MergeAndDeliver {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(MergeAndDeliver.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			MergeAndDeliverViaWebhookParameters parameters = new MergeAndDeliverViaWebhookParameters();

            parameters.setFileUrl("https://demo.office-integrator.com/zdocs/OfferLetter.zdoc");
            parameters.setMergeDataJsonUrl("https://demo.office-integrator.com/data/candidates.json");

            // parameters.setFileContent(streamWrapper);

            parameters.setOutputFormat("zdoc");
            parameters.setMergeTo("separatedoc");

            parameters.setPassword("***");

            MailMergeWebhookSettings webhookSettings = new MailMergeWebhookSettings();

            webhookSettings.setInvokeUrl("https://officeintegrator.zoho.com/v1/api/webhook/savecallback/601e12157a25e63fc4dfd4e6e00cc3da2406df2b9a1d84a903c6cfccf92c8286");
            webhookSettings.setInvokePeriod("oncomplete");

            parameters.setWebhook(webhookSettings);
            
			APIResponse<WriterResponseHandler> response = sdkOperations.mergeAndDeliverViaWebhook(parameters);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				MergeAndDeliverViaWebhookSuccessResponse mergeResponse = (MergeAndDeliverViaWebhookSuccessResponse) response.getObject();

				LOGGER.log(Level.INFO, "Total Records Count - {0}", new Object[] { mergeResponse.getRecords() });
				LOGGER.log(Level.INFO, "Total Report URL - {0}", new Object[] { mergeResponse.getMergeReportDataUrl() });
			} else {
				InvaildConfigurationException invalidConfiguration = (InvaildConfigurationException) response.getObject();

				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "Document configuration error - {0}", new Object[] { errorMessage }); //No I18N
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Exception in creating document session url - ", e); //No I18N
		}
		
		
	}
	
	public static boolean initializeSdk() {
		boolean status = false;

		try {
			APIKey apikey = new APIKey("2ae438cf864488657cc9754a27daa480");
	        UserSignature user = new UserSignature("john@zylker.com"); //No I18N
	        Logger logger = new Logger.Builder()
						        .level(Levels.INFO)
						        //.filePath("<file absolute path where logs would be written>") //No I18N
						        .build();
	        ZOIEnvironment.setProductionUrl("https://api.office-integrator.com/");

			new Initializer.Builder()
				.user(user)
				.environment(ZOIEnvironment.PRODUCTION)
				.token(apikey)
				.logger(logger)
				.initialize();
			
			status = true;
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Exception in creating document session url - ", e); //No I18N
		}
		return status;
	}
}
