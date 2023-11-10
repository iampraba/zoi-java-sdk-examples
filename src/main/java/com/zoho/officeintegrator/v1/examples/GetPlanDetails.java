//$Id$
package com.zoho.officeintegrator.v1.examples;

import java.util.logging.Level;

import com.zoho.Initializer;
import com.zoho.UserSignature;
import com.zoho.api.authenticator.APIKey;
import com.zoho.api.logger.Logger;
import com.zoho.api.logger.Logger.Levels;
import com.zoho.dc.ZOIEnvironment;
import com.zoho.officeintegrator.v1.InvaildConfigurationException;
import com.zoho.officeintegrator.v1.PlanDetails;
import com.zoho.officeintegrator.v1.ResponseHandler;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.util.APIResponse;

public class GetPlanDetails {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(GetPlanDetails.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();
			
			V1Operations sdkOperations = new V1Operations();
			
			APIResponse<ResponseHandler> response = sdkOperations.getPlanDetails();
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				PlanDetails planDetailsObj = (PlanDetails) response.getObject();

				LOGGER.log(Level.INFO, "Plan Name - {0}", new Object[] { planDetailsObj.getPlanName() }); //No I18N
				LOGGER.log(Level.INFO, "Subscription Period - {0}", new Object[] { planDetailsObj.getSubscriptionPeriod() }); //No I18N
				LOGGER.log(Level.INFO, "Total Usage Limit- {0}", new Object[] { planDetailsObj.getUsageLimit() }); //No I18N
				LOGGER.log(Level.INFO, "Total API Usage - {0}", new Object[] { planDetailsObj.getTotalUsage() }); //No I18N
				LOGGER.log(Level.INFO, "Remaining API Limit - {0}", new Object[] { planDetailsObj.getRemainingUsageLimit() }); //No I18N
			} else {
				InvaildConfigurationException invalidConfiguration = (InvaildConfigurationException) response.getObject();

				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "configuration error in getting plan details. error - {0}", new Object[] { errorMessage }); //No I18N
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Exception in getting plandetails - ", e); //No I18N
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
