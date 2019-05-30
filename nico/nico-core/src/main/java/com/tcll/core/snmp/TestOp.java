package com.tcll.core.snmp;

import java.io.IOException;
import java.net.InetAddress;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 * 需要服务器安装snmp工具开放udp161端口 设置public共同体
 * @author only3c
 */
public class TestOp {

	public static void main(String[] args) {
		try {

			InetAddress ad = InetAddress.getByName("192.168.252.128");
			boolean state = ad.isReachable(2000);// 测试是否可以达到该地址 2秒超时
			System.out.println("是否连接：" + state);

			Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
			CommunityTarget target = new CommunityTarget();// agent对象
			target.setCommunity(new OctetString("public"));// 设置共同体名,没发现设置RWCommnity的方
															// 法,大概只能设一个.
			target.setVersion(SnmpConstants.version2c);// 设置版本
			target.setAddress(new UdpAddress("192.168.252.128/161"));// 设置IP地址和端口号,这里竟然用'/'来分
			// 隔,当初确实没有料到,JDOC的说明等于没有.
			target.setRetries(1); // 设置重试次数
			target.setTimeout(5000); // 设置超时

			snmp.listen(); // 监听
			PDU request = new PDU(); // new request PDU包
			// set pud type and set oid
			request.setType(PDU.GET); // 设置PDU类型,
			request.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.1.0"))); // OID添加
			request.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.2.0")));
			System.out.println("REQUEST UDP:" + request);// 请求包内内容输出,
			PDU response = null;// 定义response包
			ResponseEvent responseEvent = snmp.send(request, target); // 发出request PDU
			// 接收response PDU
			response = responseEvent.getResponse();

			// response PDU包解析
			if (response != null) {
				if (response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {
					System.out.println("no error.");
					String pause = responseEvent.getResponse().getVariableBindings().toString();
//					String getvalue = pause.substring(pause.indexOf("= ") + 2, pause.indexOf(']'));
					String oid = pause.substring(pause.indexOf("VBS[") + 2, pause.indexOf("=") - 1);
					System.out.println(oid + "::");
					System.out.println(response);

				} else {
					System.out.println("get error:" + response.getErrorStatusText());
				}

			} else {
				System.out.println("get response error");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
