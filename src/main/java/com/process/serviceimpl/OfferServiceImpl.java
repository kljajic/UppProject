package com.process.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.process.activiti.ListOfferFormType;
import com.process.model.Offer;
import com.process.model.PurchaseRequest;
import com.process.model.User;
import com.process.repository.OfferRepository;
import com.process.service.EmailService;
import com.process.service.OfferService;
import com.process.service.SecurityService;
import com.process.service.UserService;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {

	private OfferRepository offerRepository;
	private UserService userService;
	private EmailService emailService;
	private SecurityService securityService;
	private ProcessEngine processEngine;
	
	@Autowired
	public OfferServiceImpl(OfferRepository offerRepository, UserService userService, EmailService emailService, SecurityService securityService, ProcessEngine processEngine) {
		this.offerRepository = offerRepository;
		this.userService = userService;
		this.emailService = emailService;
		this.securityService = securityService;
		this.processEngine = processEngine;
	}
	
	@Override
	public Offer createOffer(Boolean cancelOffer, Double price, String dueDate, 
							 PurchaseRequest purchaseRequest, String username, String email) {
		Offer offer = new Offer();
		offer.setCancelOffer(cancelOffer);

		if(price != null)
			offer.setExpenses(price);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(dueDate != null)
				offer.setJobDueDate(simpleDateFormat.parse(dueDate));
		} catch (ParseException e) {
			offer.setJobDueDate(new Date());
		}
		offer.setAccepted(false);
		offer.setInitiator(userService.getUser(username, email));
		offer.setPurchaseRequest(purchaseRequest);
		return offerRepository.save(offer);
	}
	
	@Override
	public Offer getOffer(Long offerId) {
		return offerRepository.findOne(offerId);
	}
	
	@Override
	public void startAuctionProcess() {
		User user = securityService.getLoggedUser();
		if(user == null) {
			return;
		}
		Map<String, Object> austionProcessVariables = new HashMap<String, Object>();
		austionProcessVariables.put("brojac", 0);
		TaskService taskService = processEngine.getTaskService();/////
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(user.getUsername()).list();/////
		System.out.println(user.getUsername() + " pre startovanja procesa ima: " + tasks.size() + " taskova");/////
		processEngine.getIdentityService().setUserInfo(user.getUsername(), "email", user.getEmail());
		processEngine.getIdentityService().setAuthenticatedUserId(user.getUsername());
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("auctionProcess", austionProcessVariables);
		processInstance.getProcessVariables().put("processInstanceId", processInstance.getId());
		System.out.println("ID PROCESA: " + processInstance.getId());
		tasks = processEngine.getTaskService().createTaskQuery().taskAssignee(user.getUsername()).list();/////
		System.out.println(user.getUsername() + " nakon startovanja procesa ima: " + tasks.size() + " taskova");
	}

	@Override
	public ListOfferFormType formOffersRangListForPurchaseRequest(Long purchaseRequestId) {
		List<Offer> offers = offerRepository.findOffersByPurchaseRequestIdOrderByExpensesAscJobDueDateAsc(purchaseRequestId);
		ListOfferFormType loft = new ListOfferFormType(offers);
		return loft;
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
