package org.apache.jmeter.performance.task.gateway.executions;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.performance.annotation.Task;
import org.apache.jmeter.performance.result.HandlerResult;
import org.apache.jmeter.performance.task.gateway.AbstractGatewayExecution;
import org.apache.jmeter.performance.util.HttpUtil;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;

import com.zitopay.datagram.request.RequestDatagram;

/**
 * @since 2019年1月16日
 */
@Task("payment_order")
public class PaymentOrder extends AbstractGatewayExecution {

	JavaSamplerContext context;

	@Override
	protected String getDeviceNo() {
		return context.getParameter("device_no");
	}

	@Override
	protected String getMerchantNo() {
		return context.getParameter("mer_no");
	}

	@Override
	protected String getHardWareNo() {
		return context.getParameter("hardware_no");
	}

	@Override
	protected String getHardWareKey() {
		return context.getParameter("hardware_key");
	}

	public HandlerResult exec(JavaSamplerContext context) throws Exception {
		String url = context.getParameter("url");
		this.context = context;
		HandlerResult handler = new HandlerResult();
		RequestDatagram rd = new RequestDatagram();
		rd.setDeviceNo(getDeviceNo());
		Order order = new Order();
		order.setDevice_no(getDeviceNo());
		order.setMer_no(getMerchantNo());
		order.setMer_order_id(UUID.randomUUID().toString().replaceAll("-", ""));
		order.setOrder_title(order.getMer_order_id());
		order.setBar_code(order.getMer_order_id());
		rd.setData(order);
		String request = rd.encryptDESString(getPaymentKey());
		String response = HttpUtil.sendPost(url, request);
		if (StringUtils.isEmpty(response)) {
			handler.setSuccess(false);
		} else {
			handler.setSuccess(true);
			handler.setHandlerResponse(response);
		}
		return handler;
	}

	private class Order implements Serializable {

		private String device_no;

		private String mer_no;

		private String mer_order_id;

		private String order_amt;

		private String order_title;

		private String bar_code;

		public String getDevice_no() {
			return device_no;
		}

		public void setDevice_no(String device_no) {
			this.device_no = device_no;
		}

		public String getMer_no() {
			return mer_no;
		}

		public void setMer_no(String mer_no) {
			this.mer_no = mer_no;
		}

		public String getMer_order_id() {
			return mer_order_id;
		}

		public void setMer_order_id(String mer_order_id) {
			this.mer_order_id = mer_order_id;
		}

		public String getOrder_amt() {
			return order_amt;
		}

		public void setOrder_amt(String order_amt) {
			this.order_amt = order_amt;
		}

		public String getOrder_title() {
			return order_title;
		}

		public void setOrder_title(String order_title) {
			this.order_title = order_title;
		}

		public String getBar_code() {
			return bar_code;
		}

		public void setBar_code(String bar_code) {
			this.bar_code = bar_code;
		}
	}
}
