package com.zoho.officeintegrator.v1.examples.sheet;

import java.util.logging.Level;

import com.zoho.Initializer;
import com.zoho.UserSignature;
import com.zoho.api.authenticator.APIKey;
import com.zoho.api.logger.Logger;
import com.zoho.api.logger.Logger.Levels;
import com.zoho.dc.ZOIEnvironment;
import com.zoho.officeintegrator.v1.CreateSheetParameters;
import com.zoho.officeintegrator.v1.CreateSheetResponse;
import com.zoho.officeintegrator.v1.InvaildConfigurationException;
import com.zoho.officeintegrator.v1.SheetResponseHandler;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.util.APIResponse;

public class CreateSpreadsheet {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(CreateSpreadsheet.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			CreateSheetParameters createSpreadsheetParams = new CreateSheetParameters();

			APIResponse<SheetResponseHandler> response = sdkOperations.createSheet(createSpreadsheetParams);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				CreateSheetResponse sheetResponse = (CreateSheetResponse) response.getObject();
				
				LOGGER.log(Level.INFO, "Sheet document id - {0}", new Object[] { sheetResponse.getDocumentId() }); //No I18N
				LOGGER.log(Level.INFO, "Sheet document session id - {0}", new Object[] { sheetResponse.getSessionId() }); //No I18N
				LOGGER.log(Level.INFO, "Sheet document session url - {0}", new Object[] { sheetResponse.getDocumentUrl() }); //No I18N
			} else {
				InvaildConfigurationException invalidConfiguration = (InvaildConfigurationException) response.getObject();

				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "Sheet configuration error - {0}", new Object[] { errorMessage }); //No I18N
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Exception in creating sheet session url - ", e); //No I18N
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
			LOGGER.log(Level.INFO, "Exception in creating sheet session url - ", e); //No I18N
		}
		return status;
	}
}
