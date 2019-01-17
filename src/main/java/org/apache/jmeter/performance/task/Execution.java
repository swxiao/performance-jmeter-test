package org.apache.jmeter.performance.task;

import org.apache.jmeter.performance.result.HandlerResult;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;

/**
 * @since 2019年1月16日
 */
public interface Execution {

	HandlerResult exec(JavaSamplerContext context) throws Exception;
}
