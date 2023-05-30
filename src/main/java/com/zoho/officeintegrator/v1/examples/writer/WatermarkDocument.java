package com.zoho.officeintegrator.v1.examples.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.apache.commons.io.IOUtils;

import com.zoho.Initializer;
import com.zoho.UserSignature;
import com.zoho.api.authenticator.APIKey;
import com.zoho.api.logger.Logger;
import com.zoho.api.logger.Logger.Levels;
import com.zoho.dc.ZOIEnvironment;
import com.zoho.officeintegrator.v1.FileBodyWrapper;
import com.zoho.officeintegrator.v1.InvaildConfigurationException;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.officeintegrator.v1.WatermarkParameters;
import com.zoho.officeintegrator.v1.WatermarkSettings;
import com.zoho.officeintegrator.v1.WriterResponseHandler;
import com.zoho.util.APIResponse;

public class WatermarkDocument {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(WatermarkDocument.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			WatermarkParameters waterMarkParams = new WatermarkParameters();
			
			waterMarkParams.setUrl("https://demo.office-integrator.com/zdocs/MS_Word_Document_v0.docx");
			
			WatermarkSettings waterMarkSettings = new WatermarkSettings();
			
			waterMarkSettings.setType("text");
			waterMarkSettings.setFontSize(36);
			waterMarkSettings.setOpacity(70.00);
			waterMarkSettings.setFontName("Arial");
			waterMarkSettings.setFontColor("#000000");
			waterMarkSettings.setOrientation("horizontal");
			waterMarkSettings.setText("Sample Water Mark Text");
			
			waterMarkParams.setWatermarkSettings(waterMarkSettings);
			
			APIResponse<WriterResponseHandler> response = sdkOperations.createWatermarkDocument(waterMarkParams);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				FileBodyWrapper fileBodyWrapper = (FileBodyWrapper) response.getObject();
				String outputFilePath = System.getProperty("user.dir") + File.separator + "WaterMarkedDocument.docx";
				InputStream inputStream = fileBodyWrapper.getFile().getStream();
				OutputStream outputStream = new FileOutputStream(new File(outputFilePath));
				
				IOUtils.copy(inputStream, outputStream);
				LOGGER.log(Level.INFO, "Water marked document saved in output file path - {0}", new Object[] { outputFilePath }); //No I18N
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
//	        ZOIEnvironment.setProductionUrl("https://122a4a0a4b36d2e30488e6700fbb3ca6.m.pipedream.net/");
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
