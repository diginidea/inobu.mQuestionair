package com.inobu.mquestioner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService {
	// Namespace of the Webservice - can be found in WSDL
	// private static String NAMESPACE = "http://tempuri.org/";
	// Webservice URL - WSDL File location
	// private static String URL =
	// "http://sismonip.mefanet.com/webservicetest.asmx?op=GetEnumID";//Make
	// sure you changed IP address
	// SOAP Action URI again Namespace + Web method name
	// private static String SOAP_ACTION = "http://service.programmerguru.com/";

	private static final String SOAP_ACTION = "http://tempuri.org/GetEnumID";
	private static final String OPERATION_NAME = "GetEnumID";// your webservice
																// web method
																// name
	private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
	private static final String SOAP_ADDRESS = "http://sismonip.mefanet.com/webservicetest.asmx";

	public static String invokeLoginWS(String userName, String passWord,
			String webMethName) {
		String loginStatus = "";
		// Create request
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
				OPERATION_NAME);
		// Property which holds input parameters
		PropertyInfo unamePI = new PropertyInfo();
		PropertyInfo passPI = new PropertyInfo();
		// Set Username
		unamePI.setName("username");
		// Set Value
		unamePI.setValue(userName);
		// Set dataType
		unamePI.setType(String.class);
		// Add the property to request object
		request.addProperty(unamePI);
		// Set Password
		passPI.setName("password");
		// Set dataType
		passPI.setValue(passWord);
		// Set dataType
		passPI.setType(String.class);
		// Add the property to request object
		request.addProperty(passPI);
		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);

		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to boolean variable variable
			loginStatus = response.toString();

		} catch (Exception e) {
			// Assign Error Status true in static variable 'errored'
			login.errored = true;
			e.printStackTrace();
		}
		// Return booleam to calling object
		return loginStatus;
	}
}
