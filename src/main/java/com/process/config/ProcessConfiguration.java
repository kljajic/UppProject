package com.process.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.process.activiti.ListOfferFormType;

@Component
public class ProcessConfiguration {

	private String  registrationProcesUri;
	private String  auctionProcesUri;
	private String  groupsUri;
	private String  usersUri;
	private ProcessEngine processEngine;
	
	@Autowired
	public ProcessConfiguration() throws IOException {
		registrationProcesUri = new ClassPathResource("diagrams/RegisterUserProcess.bpmn").getPath();
		auctionProcesUri = new ClassPathResource("diagrams/AuctionProcess.bpmn").getPath();
		groupsUri = new ClassPathResource("properties/groups.yml").getFile().getAbsolutePath();
		usersUri = new ClassPathResource("properties/users.yml").getFile().getAbsolutePath();
		this.processEngine = processEngineConfiguration().buildProcessEngine();
		prepareProcessEngine();
	}
	
	public void prepareProcessEngine() {
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		System.out.println("POCETAK INICIJALIZACIJE PROCESS ENGINE-A => Ukupan broj deployment-a: " + repositoryService.createDeploymentQuery().count());
		for (Deployment d : repositoryService.createDeploymentQuery().list()) {
			repositoryService.deleteDeployment(d.getId(), true);
		}
		repositoryService.createDeployment().addClasspathResource(this.registrationProcesUri).deploy();
		repositoryService.createDeployment().addClasspathResource(this.auctionProcesUri).deploy();
		System.out.println("KRAJ INICIJALIZACIJE PROCESS ENGINE-A => Ukupan broj deployment-a: " + repositoryService.createDeploymentQuery().count());
	}

	@Bean
	public IdentityService identityService() {
		IdentityService identityService = this.processEngine.getIdentityService();
		System.out.println("POCETAK INICIJALIZACIJE IDENTITY SERVICE-A => Broj grupa: " + identityService.createGroupQuery().count());
		System.out.println("POCETAK INICIJALIZACIJE IDENTITY SERVICE-A => Broj korisnika: " + identityService.createUserQuery().count());
		System.out.println("POCETAK INICIJALIZACIJE IDENTITY SERVICE-A => Broj korisnika u grupi bankar:  " + identityService.createUserQuery().memberOfGroup("bankar").count());
		if (identityService.createGroupQuery().count() == 0) {
			initGroupsYml(identityService);
		}
		if (identityService.createUserQuery().count() == 0) {
			initUsersYml(identityService);
		}
		System.out.println("KRAJ INICIJALIZACIJE IDENTITY SERVICE-A => Broj grupa: " + identityService.createGroupQuery().count());
		System.out.println("KRAJ INICIJALIZACIJE IDENTITY SERVICE-A => Broj korisnika: " + identityService.createUserQuery().count());
		System.out.println("KRAJ INICIJALIZACIJE IDENTITY SERVICE-A => Broj korisnika u grupi bankar:  " + identityService.createUserQuery().memberOfGroup("bankar").count());
		return identityService;
	}

	@SuppressWarnings("rawtypes")
	private void initGroupsYml(IdentityService identityService) {
		YamlReader yamlReader = null;
		Map map;
		Group newGroup;
		try {
			yamlReader = new YamlReader(new FileReader(this.groupsUri));
			while (true) {
				map = (Map) yamlReader.read();
				if (map == null)
					break;
				newGroup = identityService.newGroup((String) map.get("id"));
				newGroup.setName((String) map.get("name"));
				newGroup.setType((String) map.get("type"));
				identityService.saveGroup(newGroup);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (YamlException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initUsersYml(IdentityService identityService) {
		YamlReader yamlReader = null;
		Map map;
		User newUser;
		try {
			yamlReader = new YamlReader(new FileReader(this.usersUri));
			while (true) {
				map = (Map) yamlReader.read();
				if (map == null)
					break;
				newUser = identityService.newUser((String) map.get("id"));
				newUser.setFirstName((String) map.get("firstName"));
				newUser.setLastName((String) map.get("lastName"));
				newUser.setEmail((String) map.get("email"));
				newUser.setPassword((String) map.get("password"));
				identityService.saveUser(newUser);

				for (HashMap recordMap : (List<HashMap>) map.get("groups"))
					identityService.createMembership(newUser.getId(), (String) recordMap.get("id"));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (YamlException e) {
			e.printStackTrace();
		}
	   }
	
	   @Bean
	   public BasicDataSource dataSource(){
	      BasicDataSource dataSource = new BasicDataSource();
	      dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	      dataSource.setUrl("jdbc:mysql://localhost:3306/process?useSSL=false&createDatabaseIfNotExist=true");
	      dataSource.setUsername("root");
	      dataSource.setPassword("root");
	      dataSource.setDefaultAutoCommit(true);
	      return dataSource;
	   }
	   
	   @Bean
	   @DependsOn(value = { "dataSource" })
	   public DataSourceTransactionManager transactionManager(){
	      DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
	      dataSourceTransactionManager.setDataSource(dataSource());
	      return dataSourceTransactionManager;
	   }
	   
	   @Bean
	   @DependsOn(value = { "dataSource" })
	   public SpringProcessEngineConfiguration processEngineConfiguration(){
	      SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
	      configuration.setDataSource(dataSource());
	      configuration.setDatabaseType("mysql");
	      configuration.setTransactionManager(transactionManager());
	      configuration.setDatabaseSchemaUpdate("true");
	      configuration.setJobExecutorActivate(true);
	      configuration.setCustomFormTypes(Arrays.asList(new ListOfferFormType(null)));
	      return configuration;
	   }
}
