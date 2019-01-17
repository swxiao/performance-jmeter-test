package org.apache.jmeter.performance.task.gateway;

import org.apache.jmeter.performance.task.Execution;

import com.zitopay.datagram.AbstractDatagram;
import com.zitopay.datagram.utils.DigestUtil;

/**
 * @since 2019年1月16日
 */
public abstract class AbstractGatewayExecution implements Execution {

	protected abstract String getDeviceNo();

	protected abstract String getMerchantNo();

	protected abstract String getHardWareNo();

	protected abstract String getHardWareKey();

	protected String getPaymentKey() {
		String deviceNoMD5 = DigestUtil.md5Hex(getDeviceNo());// 设备编号(SN号)
		String hardwareMD5 = DigestUtil.md5Hex(getHardWareNo());// 设备硬件编号
		String deviceKeyMD5 = DigestUtil.md5Hex(getHardWareKey());// 硬件设备KEY
		final String plainKey = DigestUtil.md5Hex(deviceNoMD5 + hardwareMD5 + deviceKeyMD5);
		return plainKey;
	}

	protected String getBindKey(AbstractDatagram absRd) {
		String plain = absRd.getSerialNo().concat(absRd.getTimestamp());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < plain.length();) {
			sb.append(plain.charAt(i));
			i = i + 2;
		}
		return DigestUtil.md5Hex(sb.toString());
	}

}
