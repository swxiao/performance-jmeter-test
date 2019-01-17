package org.apache.jmeter.performance;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.performance.result.HandlerResult;
import org.apache.jmeter.performance.task.Execution;
import org.apache.jmeter.performance.util.ClassUtil;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * @since 2019年1月16日
 */
public class PerformanceClient extends AbstractJavaSamplerClient implements Serializable {

	private static final long serialVersionUID = -8824417562452057732L;

	private SampleResult result;

	private static final Map<String, Execution> taskMap = ClassUtil.getAnnotationedClasses("org.apache.jmeter.performance");

	@Override
	public void setupTest(JavaSamplerContext context) {
		super.setupTest(context);
		result = getSampleResult();
	}

	public SampleResult getSampleResult() {
		SampleResult result = new SampleResult();
		result.setSampleLabel(getLabel());
		return result;
	}

	public String getLabel() {
		return "PerformanceClient" + Thread.currentThread().getId();
	}

	public SampleResult runTest(JavaSamplerContext context) {
		try {
			result.sampleStart();
			String task = context.getParameter("task");
			if (StringUtils.isEmpty(task)) {
				result.setResponseCode("ERROR");
				result.setResponseMessage("param task is null or empty.");
				return result;
			}
			Execution execution = taskMap.get(task);
			HandlerResult handler = execution.exec(context);
			if (handler.isSuccess()) {
				result.setSuccessful(true);
			} else {
				result.setSuccessful(false);
			}
			result.setResponseData(handler.getHandlerResponse(), "UTF-8");
			result.setDataType(SampleResult.TEXT);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccessful(false);
		} finally {
			result.sampleEnd();
		}
		return result;
	}
}
