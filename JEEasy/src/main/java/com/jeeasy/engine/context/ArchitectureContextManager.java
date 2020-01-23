package com.jeeasy.engine.context;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.enterprise.inject.Produces;

public class ArchitectureContextManager {
	private static HashMap<Thread, ArchitectureContext> contextsForThreads = new HashMap<>();

	@Produces
	public static ArchitectureContext getForThread() {
		return getForThread(Thread.currentThread());
	}

	public static ArchitectureContext getForThread(Thread t) {
		ArchitectureContext context = contextsForThreads.getOrDefault(t, new ArchitectureContext());

		if (context == null) {
			context = new ArchitectureContext();
		}

		contextsForThreads.put(t, context);

		return context;
	}
	
	public static void setForThread(ArchitectureContext context) {
		contextsForThreads.put(Thread.currentThread(), context);
	}
	
	public static void setForThread(Thread t, ArchitectureContext context) {
		contextsForThreads.put(t, context);
	}

	public static void destroyForThread() {
		destroyForThread(Thread.currentThread());
	}

	public static void destroyForThread(Thread t) {
		if (contextsForThreads.containsKey(t)) {
			contextsForThreads.remove(t);
		}
	}

	public static void destroyForThread(ArchitectureContext context) {
		for (Entry<Thread, ArchitectureContext> entry : contextsForThreads.entrySet()) {
			if (entry.getValue() == context) {
				destroyForThread(entry.getKey());
				return;
			}
		}
	}
}
