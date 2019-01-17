package org.apache.jmeter.performance.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.jmeter.performance.annotation.Task;
import org.apache.jmeter.performance.task.Execution;
import org.reflections.Reflections;

/**
 * @since 2019年1月16日
 */
public class ClassUtil {

	public static Map<String, Execution> getAnnotationedClasses(String packageName) {
		Map<String, Execution> map = new HashMap<String, Execution>();
		try {
			Reflections reflections = new Reflections(packageName);
			Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Task.class);
			for (Class clz : classesList) {
				Task task = ((Class<Execution>) clz).getAnnotation(Task.class);
				map.put(task.value(), (Execution) clz.newInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}