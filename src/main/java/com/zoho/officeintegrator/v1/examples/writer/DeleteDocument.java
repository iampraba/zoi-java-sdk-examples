package com.zoho.officeintegrator.v1.examples.writer;

import java.util.logging.Level;

import com.zoho.Initializer;
import com.zoho.UserSignature;
import com.zoho.api.authenticator.APIKey;
import com.zoho.api.logger.Logger;
import com.zoho.api.logger.Logger.Levels;
import com.zoho.dc.ZOIEnvironment;
import com.zoho.officeintegrator.v1.CreateDocumentParameters;
import com.zoho.officeintegrator.v1.CreateDocumentResponse;
import com.zoho.officeintegrator.v1.DocumentDeleteSuccessResponse;
import com.zoho.officeintegrator.v1.InvaildConfigurationException;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.officeintegrator.v1.WriterResponseHandler;
import com.zoho.util.APIResponse;

public class DeleteDocument {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(DeleteDocument.class.getName());

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
				
				LOGGER.log(Level.INFO, "Document ID - {0} created to demontrate the document delete api.", new Object[] { documentId }); //No I18N
				
				response = sdkOperations.deleteDocument(documentId);
				
				if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
					DocumentDeleteSuccessResponse deleteResponseObj = (DocumentDeleteSuccessResponse) response.getObject();
					
					LOGGER.log(Level.INFO, "Document delete status - {0}", new Object[] { deleteResponseObj.getDocumentDeleted() }); //No I18N
				} else {
					InvaildConfigurationException invalidConfiguration = (InvaildConfigurationException) response.getObject();

					String errorMessage = invalidConfiguration.getMessage();
					
					/*Long errorCode = invalidConfiguration.getCode();
					String errorKeyName = invalidConfiguration.getKeyName();
					String errorParameterName = invalidConfiguration.getParameterName();*/
					
					LOGGER.log(Level.INFO, "Unable to delete the document - {0} error message - {1}", new Object[] { documentId, errorMessage }); //No I18N
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
