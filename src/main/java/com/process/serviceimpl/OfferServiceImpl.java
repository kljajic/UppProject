package com.process.serviceimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.process.model.Offer;
import com.process.model.PurchaseRequest;
import com.process.repository.OfferRepository;
import com.process.service.EmailService;
import com.process.service.OfferService;
import com.process.service.UserService;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {

	private OfferRepository offerRepository;
	private UserService userService;
	private EmailService emailService;
	private ProcessEngine processEngine;
	
	@Autowired
	public OfferServiceImpl(OfferRepository offerRepository, UserService userService, EmailService emailService, ProcessEngine processEngine) {
		this.offerRepository = offerRepository;
		this.userService = userService;
		this.emailService = emailService;
		this.processEngine = processEngine;
	}
	
	@Override
	public Offer createOffer(Boolean cancelOffer, Double price, Date dueDate, 
							 PurchaseRequest purchaseRequest, String username, String email) {
		Offer offer = new Offer();
		offer.setCancelOffer(cancelOffer);
		offer.setExpenses(price);
		offer.setJobDueDate(dueDate);
		offer.setAccepted(false);
		offer.setInitiator(userService.getUser(username, email));
		offer.setPurchaseRequest(purchaseRequest);
		return offerRepository.save(offer);
	}
	
	@Override
	public Offer getOffer(Long offerId) {
		return offerRepository.getOne(offerId);
	}
	
	@Override
	public void startAuctionProcess() {
		Map<String, Object> registerProcessVariables = new HashMap<String, Object>();
		registerProcessVariables.put("brojac", new Integer(0));
		TaskService taskService = processEngine.getTaskService();/////
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("pera").list();/////
		System.out.println("Pera pre startovanja procesa ima: " + tasks.size() + " taskova");/////
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("auctionProcess", registerProcessVariables);
		processInstance.getProcessVariables().put("processInstanceId", processInstance.getId());
		System.out.println("ID PROCESA: " + processInstance.getId());
		tasks = processEngine.getTaskService().createTaskQuery().taskAssignee("pera").list();/////
		System.out.println("Pera nakon startovanja procesa ima: " + tasks.size() + " taskova");
	}

	@Override
	public List<Offer> formOffersRangListForPurchaseRequest(Long purchaseRequestId) {
		return offerRepository.findOffersByPurchaseRequestId(purchaseRequestId);
	}

	@Override
	public Integer getNumberOfOffersForPurchaseRequest(Long purchaseRequestId) {
		return offerRepository.countOffersByPurchaseRequestIdAndCancelOffer(purchaseRequestId, false);
	}

	@Override
	public Offer acceptOfferAndSendMailToCompany(Long offerId) {
		Offer offer = offerRepository.getOne(offerId);
		offer.setAccepted(true);
		emailService.sentOfferAcceptanceEmail(offer.getInitiator().getEmail());
		return offerRepository.save(offer);
	}

}
