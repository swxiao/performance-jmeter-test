package org.apache.jmeter.performance.task;

import org.apache.jmeter.performance.result.HandlerResult;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * @since 2019年1月16日
 */
public interface Execution {

	void before(JavaSamplerContext context, SampleResult result) throws Exception;

	void after(JavaSamplerContext context, SampleResult result) throws Exception;

	HandlerResult exec(JavaSamplerContext context, SampleResult result) throws Exception;
}
