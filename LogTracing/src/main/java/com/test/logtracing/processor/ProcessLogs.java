package com.test.logtracing.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.test.logtracing.constant.Constants;
import com.test.logtracing.pojo.Log;
import com.test.logtracing.repo.JDBCUtils;

public class ProcessLogs {
	
	@Autowired
	JDBCUtils conUtil;
	
	private static final Logger logger = LogManager.getLogger(ProcessLogs.class);
	File file;
	
	public ProcessLogs(File file) {
		this.file = file;
	}
	
	public void processFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    Map<String, Log> dataMap = new HashMap<>();
		    while ((line = br.readLine()) != null) {
		    	logger.info("parsing json : {}", line);
		    	Gson gson = new Gson();
		    	Log logLine = gson.fromJson(line, Log.class);
		    	if(!dataMap.containsKey(logLine.getId())) {
		    		dataMap.put(logLine.getId(),logLine);
		    		continue;
		    	} 
		    	else {
		    		Log entryFromMap = dataMap.get(logLine.getId());
		    		Log finishInfo = logLine;
		    		Log startInfo = entryFromMap;
		    		if(entryFromMap.getState().equals(Constants.FINISHED_STATE)) {
		    			finishInfo = entryFromMap;
		    			startInfo = logLine;
		    		}

		    		finishInfo.raiseAlertIfTookLonger(startInfo);
		    		startInfo.setAlert(finishInfo.getAlert());
		    		logger.info(startInfo);
		    		logger.info(finishInfo);
		    		
		    		conUtil.commitLogToFile(startInfo);
		    		conUtil.commitLogToFile(finishInfo);
		    		
		    		
		    	}
		    	
		    }
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
