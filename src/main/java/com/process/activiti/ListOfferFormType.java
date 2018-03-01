package com.process.activiti;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.form.AbstractFormType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.process.model.Offer;

public class ListOfferFormType extends AbstractFormType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1094664039648036124L;
	
	
	private static String TYPE_NAME = "listOffer";
	private List<Offer> offers;
	
	public ListOfferFormType(List<Offer> offers) {
		this.offers = offers;
	}
	
	@Override
	public String getName() {
		return TYPE_NAME;
	}

	@Override
	public Object convertFormValueToModelValue(String propertyValue) {
		return null;
	}

	@Override
	public String convertModelValueToFormValue(Object modelValue) {
		
		if(modelValue instanceof ListOfferFormType) {
			List<Offer> off = ((ListOfferFormType) modelValue).getOffers();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			String id = "\"id\" : ";
			String initiator = "\"initiator\" : ";
			String expenses = "\"dueDate\" : ";
			String dueDate = "\"expenses\" : ";
			String json = "[";
			for(Offer of : off) {
				json = json + "{";
				json = json + id + "\"" + of.getId() + "\",";
				json = json + initiator + "\"" + of.getInitiator().getUsername() + "\",";
				json = json + dueDate + "\"" + of.getExpenses() + "\",";
				json = json + expenses + "\"" + sdf.format(of.getJobDueDate()) + "\"";
				json = json + "},";
			};
			json = json.substring(0, json.length() - 1);
			json = json + "]";
			return json;
		}
		return null;
	}
	
	protected void validateValue(String value) {
	    if(value != null) {
	      if(offers != null && offers.stream().filter(offer -> {
	    	  return String.valueOf(offer.getId()).equals(value); 
	      }).toArray().length == 0) {
	        throw new ActivitiIllegalArgumentException("Invalid value for offer list form property: " + value);
	      }
	    }
    }
	
	@Override
	public Object getInformation(String key) {
		if (key.equals("values")) {
		      return offers;
	    }
	    return null;
	}
	
	@JsonProperty
	public List<Offer> getOffers() {
		return offers;
	}
	@JsonProperty
	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}
}
