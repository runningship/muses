package com.fh.util;

import java.lang.management.ManagementFactory;
import java.util.List;

public class JVMUtil {

	public static boolean isDebug(){
		List<String> args = ManagementFactory.getRuntimeMXBean().getInputArguments();
		boolean isDebug = false;
		for (String arg : args) {
		  if (arg.startsWith("-Xrunjdwp") || arg.startsWith("-agentlib:jdwp")) {
		    isDebug = true;
		    break;
		  }
		}
		return isDebug;
	}
}
