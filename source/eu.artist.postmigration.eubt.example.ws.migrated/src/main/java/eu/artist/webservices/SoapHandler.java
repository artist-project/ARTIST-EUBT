/*******************************************************************************
 * Copyright (c) 2014 Vienna University of Technology.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Patrick Neubauer (Vienna University of Technology) - initial API and implementation
 *
 * Initially developed in the context of ARTIST EU project www.artist-project.eu
 *******************************************************************************/
package eu.artist.webservices;

import java.util.Iterator;

import javax.xml.bind.JAXB;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SAAJResult;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;

import eu.artist.webservices.jaxws.CreatePetCategory;
import eu.artist.webservices.jaxws.CreateUser;
import eu.artist.webservices.jaxws.GetPetCategory;
import eu.artist.webservices.jaxws.ObtainUser;
import eu.artist.webservices.jaxws.UpdateUserAge;

public class SoapHandler {
	private static final String NAMESPACE_URI = "http://webservices.artist.eu/";
	private static final QName CREATE_USER_QNAME = new QName(NAMESPACE_URI, "createUser");
	private static final QName GET_USER_QNAME = new QName(NAMESPACE_URI, "obtainUser");
	private static final QName CREATE_PET_CATEGORY_QNAME = new QName(NAMESPACE_URI, "createPetCategory");
	private static final QName GET_PET_CATEGORY_QNAME = new QName(NAMESPACE_URI, "getPetCategory");
	private static final QName UPDATE_USER_AGE_QNAME = new QName(NAMESPACE_URI, "updateUserAge");
	private MessageFactory messageFactory;
	private CreateUserAdapter userGreeterAdapter;
	private CreatePetCategoryAdapter petCategoryAdapter;
	private UpdateUserAdapter updateUserAdapter;
	
	public SoapHandler() throws SOAPException {
		messageFactory = MessageFactory.newInstance();
		userGreeterAdapter = new CreateUserAdapter();
		petCategoryAdapter = new CreatePetCategoryAdapter();
		updateUserAdapter = new UpdateUserAdapter();
	}// constructor
	
	@SuppressWarnings("rawtypes")
	public SOAPMessage handleSOAPRequest(SOAPMessage request) throws SOAPException {
		SOAPBody soapBody = request.getSOAPBody();
		Iterator iterator = soapBody.getChildElements();
		Object responsePojo = null;
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof SOAPElement) {
				SOAPElement soapElement = (SOAPElement) next;
				QName qname = soapElement.getElementQName();
				
				if (CREATE_USER_QNAME.equals(qname)) {
					responsePojo = handleCreateUser(soapElement);
					break;
				}
				if (GET_USER_QNAME.equals(qname)) {
					responsePojo = handleObtainUser(soapElement);
					break;
				}
				if (CREATE_PET_CATEGORY_QNAME.equals(qname)) {
					responsePojo = handleCreatePetCategory(soapElement);
					break;
				}
				if (GET_PET_CATEGORY_QNAME.equals(qname)) {
					responsePojo = handleGetPetCategory(soapElement);
					break;
				}
				if (UPDATE_USER_AGE_QNAME.equals(qname)) {
					responsePojo = handleUpdateUserAge(soapElement);
					break;
				}
			}
		}// while
		
		SOAPMessage soapResponse = messageFactory.createMessage();
		soapBody = soapResponse.getSOAPBody();
		if (responsePojo != null) {
			JAXB.marshal(responsePojo, new SAAJResult(soapBody));			
		} else {
			SOAPFault fault = soapBody.addFault();
			fault.setFaultString("Unrecognized SOAP request.");
		}
		return soapResponse;				
	}// handleSOAPRequest

	private Object handleObtainUser(SOAPElement soapElement) {
		ObtainUser obtainUser = JAXB.unmarshal(new DOMSource(soapElement), ObtainUser.class);
		return userGreeterAdapter.obtainUser(obtainUser);
	}// handleObtainUser

	private Object handleCreateUser(SOAPElement soapElement) {
		CreateUser createUser = JAXB.unmarshal(new DOMSource(soapElement), CreateUser.class);
		return userGreeterAdapter.createUser(createUser);
	}// handleCreateUser
	
	private Object handleUpdateUserAge(SOAPElement soapElement) {
		UpdateUserAge updateUserAge = JAXB.unmarshal(new DOMSource(soapElement), UpdateUserAge.class);
		return updateUserAdapter.updateUserAge(updateUserAge);
	}// handleUpdateUserAge
	
	private Object handleGetPetCategory(SOAPElement soapElement) {
		GetPetCategory getPetCategory = JAXB.unmarshal(new DOMSource(soapElement), GetPetCategory.class);
		return petCategoryAdapter.getPetCategory(getPetCategory);
	}// handleGetPetCategory

	private Object handleCreatePetCategory(SOAPElement soapElement) {
		CreatePetCategory createPetCategory = JAXB.unmarshal(new DOMSource(soapElement), CreatePetCategory.class);
		return petCategoryAdapter.createPetCategory(createPetCategory);
	}// handleCreatePetCategory
	
}// SoapHandler
