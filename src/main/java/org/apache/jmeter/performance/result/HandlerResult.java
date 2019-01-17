package org.apache.jmeter.performance.result;

/**
 * @since 2019年1月16日
 */
public class HandlerResult {

	private boolean success;

	private String handlerResponse = "true";

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getHandlerResponse() {
		return handlerResponse;
	}

	public void setHandlerResponse(String handlerResponse) {
		this.handlerResponse = handlerResponse;
	}

	@Override
	public String toString() {
		return "HandlerResult [success=" + success + ", handlerResponse=" + handlerResponse + "]";
	}
}
