package com.analysis.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.analysis.customer.AnalysisThread;
import com.analysis.queue.AnalysisRequestQueue;
import com.softsec.tase.store.service.FileStorageService;

public class RuleVersionUpdater implements StatefulJob {

	private static final Logger LOGGER = LogManager.getLogger(RuleVersionUpdater.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		FileStorageService fileStorageService = new FileStorageService();
		long latestRuleVersion = fileStorageService.getLatestRuleVersion();
		if(latestRuleVersion > AnalysisRequestQueue.getInstance().getSensitiveStringVersion()) {
			LOGGER.info("get new rule version [ " + latestRuleVersion + " ] ");
			AnalysisThread.setSpecialStringList(latestRuleVersion, fileStorageService.getLatestSensitiveStringList());
			AnalysisRequestQueue.getInstance().setSensitiveStringVersion(latestRuleVersion);
		}
		
	}

}
