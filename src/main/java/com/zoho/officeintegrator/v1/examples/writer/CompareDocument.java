package com.zoho.officeintegrator.v1.examples.writer;

import java.util.logging.Level;

import com.zoho.Initializer;
import com.zoho.UserSignature;
import com.zoho.api.authenticator.APIKey;
import com.zoho.api.logger.Logger;
import com.zoho.api.logger.Logger.Levels;
import com.zoho.dc.ZOIEnvironment;
import com.zoho.officeintegrator.v1.CompareDocumentParameters;
import com.zoho.officeintegrator.v1.CompareDocumentResponse;
import com.zoho.officeintegrator.v1.InvaildConfigurationException;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.officeintegrator.v1.WriterResponseHandler;
import com.zoho.util.APIResponse;

public class CompareDocument {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(CompareDocument.class.getName());

	public static void main(String args[]) {
		
		try {
			//Initializing SDK once is enough. Calling here since code sample will be tested standalone. 
	        //You can place SDK initializer code in you application and call once while your application start-up. 
			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			CompareDocumentParameters compareDocumentParameters = new CompareDocumentParameters();
			
			
			compareDocumentParameters.setUrl1("https://demo.office-integrator.com/zdocs/MS_Word_Document_v0.docx");
			compareDocumentParameters.setUrl2("https://demo.office-integrator.com/zdocs/MS_Word_Document_v1.docx");
            
			String file1Name = "MS_Word_Document_v0.docx";
			String file2Name = "MS_Word_Document_v1.docx";
			/*
			String inputFile1Path = "Absolute input file1 path"
			File inputFile1 = new File(inputFile1Path);
			FileInputStream file1InputStream = new FileInputStream(inputFile1);
			StreamWrapper file1StreamWrapper = new StreamWrapper(file1InputStream);

			compareDocumentParameters.setDocument1(file1StreamWrapper);
			
			String inputFile2Path = "Absolute input file2 path"
			File inputFile2 = new File(inputFile2Path);
			FileInputStream file2InputStream = new FileInputStream(inputFile2);
			StreamWrapper file2StreamWrapper = new StreamWrapper(file2InputStream);

			compareDocumentParameters.setDocument1(file2StreamWrapper);
			*/

			compareDocumentParameters.setLang("en");
            compareDocumentParameters.setTitle(file1Name + " vs " + file2Name);
			
			APIResponse<WriterResponseHandler> response = sdkOperations.compareDocument(compareDocumentParameters);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				CompareDocumentResponse documentResponse = (CompareDocumentResponse) response.getObject();

				LOGGER.log(Level.INFO, "Document compare url - {0}", new Object[] { documentResponse.getCompareUrl() }); //No I18N
			} else {
				InvaildConfigurationException invalidConfiguration = (InvaildConfigurationException) response.getObject();
				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "Document configuration error - {0}", new Object[] { errorMessage }); //No I18N
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Exception in comparing documents - ", e); //No I18N
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
