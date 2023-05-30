package com.zoho.officeintegrator.v1.examples.show;

import java.util.logging.Level;

import com.zoho.Initializer;
import com.zoho.UserSignature;
import com.zoho.api.authenticator.APIKey;
import com.zoho.api.logger.Logger;
import com.zoho.api.logger.Logger.Levels;
import com.zoho.dc.ZOIEnvironment;
import com.zoho.officeintegrator.v1.CreateDocumentResponse;
import com.zoho.officeintegrator.v1.CreatePresentationParameters;
import com.zoho.officeintegrator.v1.DocumentInfo;
import com.zoho.officeintegrator.v1.InvaildConfigurationException;
import com.zoho.officeintegrator.v1.ShowResponseHandler;
import com.zoho.officeintegrator.v1.UserInfo;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.util.APIResponse;

public class CoEditPresentation {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(CoEditPresentation.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();
			
			V1Operations sdkOperations = new V1Operations();
			CreatePresentationParameters parameters = new CreatePresentationParameters();
			
			parameters.setUrl("https://demo.office-integrator.com/samples/show/Zoho_Show.pptx");
			
			UserInfo user1Info = new UserInfo();
			
			user1Info.setUserId("100");
			user1Info.setDisplayName("Praba");

			parameters.setUserInfo(user1Info);
			
			DocumentInfo documentInfo = new DocumentInfo();

			documentInfo.setDocumentId("1000");
			documentInfo.setDocumentName("Collaboration Testing Presentation");
			
			parameters.setDocumentInfo(documentInfo);
			
			APIResponse<ShowResponseHandler> response = sdkOperations.createPresentation(parameters);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				CreateDocumentResponse showResponse = (CreateDocumentResponse) response.getObject();

				LOGGER.log(Level.INFO, "Presentation document id - {0}", new Object[] { showResponse.getDocumentId() }); //No I18N
				LOGGER.log(Level.INFO, "Presentation session 1 id - {0}", new Object[] { showResponse.getSessionId() }); //No I18N
				LOGGER.log(Level.INFO, "Presentation session 1 url - {0}", new Object[] { showResponse.getDocumentUrl() }); //No I18N
				
				UserInfo user2Info = new UserInfo();
				
				user2Info.setUserId("200");
				user2Info.setDisplayName("Karan");

				parameters.setUserInfo(user2Info);
				
				response = sdkOperations.createPresentation(parameters);
				responseStatusCode = response.getStatusCode();
				
				if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
					showResponse = (CreateDocumentResponse) response.getObject();

					LOGGER.log(Level.INFO, "Presentation document id - {0}", new Object[] { showResponse.getDocumentId() }); //No I18N
					LOGGER.log(Level.INFO, "Presentation session 2 id - {0}", new Object[] { showResponse.getSessionId() }); //No I18N
					LOGGER.log(Level.INFO, "Presentation session 2 url - {0}", new Object[] { showResponse.getDocumentUrl() }); //No I18N
					
					LOGGER.log(Level.INFO, "Use above to session url to test the collaboration");
				}
			} else {
				InvaildConfigurationException invalidConfiguration = (InvaildConfigurationException) response.getObject();

				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "Presentation configuration error - {0}", new Object[] { errorMessage }); //No I18N
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Exception in creating presentation session url - ", e); //No I18N
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
			LOGGER.log(Level.INFO, "Exception in creating presentation session url - ", e); //No I18N
		}
		return status;
	}
}
