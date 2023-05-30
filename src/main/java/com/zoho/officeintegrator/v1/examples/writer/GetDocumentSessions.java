package com.zoho.officeintegrator.v1.examples.writer;

import java.util.List;
import java.util.logging.Level;

import com.zoho.Initializer;
import com.zoho.UserSignature;
import com.zoho.api.authenticator.APIKey;
import com.zoho.api.logger.Logger;
import com.zoho.api.logger.Logger.Levels;
import com.zoho.dc.ZOIEnvironment;
import com.zoho.officeintegrator.v1.AllSessionsResponse;
import com.zoho.officeintegrator.v1.CreateDocumentParameters;
import com.zoho.officeintegrator.v1.CreateDocumentResponse;
import com.zoho.officeintegrator.v1.InvaildConfigurationException;
import com.zoho.officeintegrator.v1.SessionMeta;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.officeintegrator.v1.WriterResponseHandler;
import com.zoho.util.APIResponse;

public class GetDocumentSessions {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(GetDocumentSessions.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			CreateDocumentParameters createDocumentParams = new CreateDocumentParameters();
			
			APIResponse<WriterResponseHandler> response = sdkOperations.createDocument(createDocumentParams);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				CreateDocumentResponse responseObj = (CreateDocumentResponse) response.getObject();
				String documentId = responseObj.getDocumentId();
				
				LOGGER.log(Level.INFO, "Document - {0} has been created to demonstrate the get document sessions details api.", new Object[] { documentId }); //No I18N
				
				response = sdkOperations.getAllSessions(documentId);
				
				responseStatusCode = response.getStatusCode();
				
				if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
					AllSessionsResponse allSessionsMeta = (AllSessionsResponse) response.getObject();

					LOGGER.log(Level.INFO, "Document id - {0}", new Object[] { allSessionsMeta.getDocumentId() }); //No I18N
					LOGGER.log(Level.INFO, "Document Name - {0}", new Object[] { allSessionsMeta.getDocumentName() }); //No I18N
					LOGGER.log(Level.INFO, "Document Type - {0}", new Object[] { allSessionsMeta.getDocumentType() }); //No I18N
					LOGGER.log(Level.INFO, "Document Expires on- {0}", new Object[] { allSessionsMeta.getExpiresOn() }); //No I18N
					LOGGER.log(Level.INFO, "Document Created on- {0}", new Object[] { allSessionsMeta.getCreatedTime() }); //No I18N
					LOGGER.log(Level.INFO, "Active sessions count - {0}", new Object[] { allSessionsMeta.getActiveSessionsCount() }); //No I18N
					LOGGER.log(Level.INFO, "Collaborators count - {0}", new Object[] { allSessionsMeta.getCollaboratorsCount() }); //No I18N
					List<SessionMeta> sessions = allSessionsMeta.getSessions();
					
					for (SessionMeta sessionMeta : sessions) {
						LOGGER.log(Level.INFO, "Session status- {0}", new Object[] { sessionMeta.getStatus() }); //No I18N
						LOGGER.log(Level.INFO, "Session User ID - {0}", new Object[] { sessionMeta.getUserInfo().getUserId() }); //No I18N
						LOGGER.log(Level.INFO, "Session User Display Name - {0}", new Object[] { sessionMeta.getUserInfo().getDisplayName() }); //No I18N
						LOGGER.log(Level.INFO, "Session Expires on - {0}", new Object[] { sessionMeta.getInfo().getExpiresOn() }); //No I18N
					}
				} else {
					InvaildConfigurationException invalidConfiguration = (InvaildConfigurationException) response.getObject();

					String errorMessage = invalidConfiguration.getMessage();
					
					/*Long errorCode = invalidConfiguration.getCode();
					String errorKeyName = invalidConfiguration.getKeyName();
					String errorParameterName = invalidConfiguration.getParameterName();*/
					
					LOGGER.log(Level.INFO, "Failed to get the document details for document id - {0} - Error message - {1}", new Object[] { documentId, errorMessage }); //No I18N
				}
				
			} else {
				InvaildConfigurationException invalidConfiguration = (InvaildConfigurationException) response.getObject();

				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "Document configuration error - {0}", new Object[] { errorMessage }); //No I18N
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Exception in creating document session url - ", e); //No I18N
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
