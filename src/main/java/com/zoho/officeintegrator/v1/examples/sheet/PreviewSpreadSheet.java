package com.zoho.officeintegrator.v1.examples.sheet;

import java.util.logging.Level;

import com.zoho.Initializer;
import com.zoho.UserSignature;
import com.zoho.api.authenticator.APIKey;
import com.zoho.api.logger.Logger;
import com.zoho.api.logger.Logger.Levels;
import com.zoho.dc.ZOIEnvironment;
import com.zoho.officeintegrator.v1.InvaildConfigurationException;
import com.zoho.officeintegrator.v1.SheetPreviewParameters;
import com.zoho.officeintegrator.v1.SheetPreviewResponse;
import com.zoho.officeintegrator.v1.SheetResponseHandler;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.util.APIResponse;

public class PreviewSpreadSheet {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(PreviewSpreadSheet.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			SheetPreviewParameters previewParams = new SheetPreviewParameters();
			
			previewParams.setUrl("https://demo.office-integrator.com/samples/sheet/Contact_List.xlsx");
			
			APIResponse<SheetResponseHandler> response = sdkOperations.createSheetPreview(previewParams);
			int responseStatusCode = response.getStatusCode();

			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				SheetPreviewResponse previewResponse = (SheetPreviewResponse) response.getObject();

				String previewUrl = previewResponse.getPreviewUrl();
				
				LOGGER.log(Level.INFO, "Document preview url - {0}", new Object[] { previewUrl }); //No I18N
			} else {
				InvaildConfigurationException invalidConfiguration = (InvaildConfigurationException) response.getObject();

				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "Document configuration error - {0}", new Object[] { errorMessage }); //No I18N
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Exception in creating preview document session url - ", e); //No I18N
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
