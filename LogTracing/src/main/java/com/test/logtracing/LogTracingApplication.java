package com.test.logtracing;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.test.logtracing.processor.ProcessLogs;

@SpringBootApplication
public class LogTracingApplication {
	
	private static final Logger log = LogManager.getLogger(LogTracingApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(LogTracingApplication.class, args);
		if(args.length < 1) {
			log.error("File Path not specified");
		} 
		else {
		    LogTracingApplication.start(args[0]);
		}
	}
	
	public static void start(String path) {
		File f = new File(path);
		if(!f.exists()) {
			log.error("File not found : {}",f.getPath());
		}
		ProcessLogs proc = new ProcessLogs(f);
		proc.processFile();
	}

}
