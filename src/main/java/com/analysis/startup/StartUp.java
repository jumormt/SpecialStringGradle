package com.analysis.startup;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.analysis.customer.AnalysisRequestConsumer;
import com.softsec.tase.store.StoreManager;

/**
 * 
 * @author tinyyard
 * 
 * each kind of rules can't be null every time when updating the rules.
 * 
 * rule version should be update every time the detect engine is updated. 
 *
 */

public class StartUp {

	private static Logger logger = LogManager.getLogger(StartUp.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		triggerRuleVersionUpdateJob();
		startCustomer();
	}

	private static void triggerRuleVersionUpdateJob() {
		StdSchedulerFactory factory = new StdSchedulerFactory();
		Scheduler quartz = null;
		try {
			quartz = factory.getScheduler();
			quartz.start();
			quartz.triggerJob("RuleVersionUpdateJob", "DefaultGroup");
		} catch (SchedulerException se) {
			logger.error("Failed to startup quartz scheduler : " + se.getMessage(), se);
			System.exit(-1);
		}
	}
	
	private static void startCustomer() {
		StoreManager storeManager = new StoreManager();
		long missionId = storeManager.startStoreCustomer();
		AnalysisRequestConsumer analysisRequestConsumer = new AnalysisRequestConsumer(missionId);
		analysisRequestConsumer.start();
		// storeManager.flushApkInfoQueue();
	}

}
