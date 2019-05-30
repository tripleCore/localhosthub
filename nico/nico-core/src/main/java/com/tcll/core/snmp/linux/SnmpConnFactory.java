package com.tcll.core.snmp.linux;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import com.tcll.core.snmp.SnmpDao;
import com.tcll.core.snmp.SnmpModel;
import com.tcll.core.snmp.SnmpService;


public class SnmpConnFactory {

	private static final Logger logger = Logger.getLogger(SnmpService.class);
	SnmpDao snmpDao = new SnmpDao();

	public SnmpDao getInstanceSnmpDao() {
		return snmpDao;
	}

	/**
	 * 获取CPU使用率
	 *
	 * @param snmpModel
	 * @return 正常返回CPU当前使用率，否则返回-1
	 */
	public Integer getCpuUtilization(SnmpModel snmpModel) {
		List<String> result = getInstanceSnmpDao().walkByTable(".1.3.6.1.2.1.25.3.3.1.2", snmpModel);
		if (result == null || result.size() == 0) {
			return -1;
		}
		double sum = 0;
		for (String s : result) {
			sum += Double.parseDouble(s);
		}
		return (int) (sum / result.size());
	}

	/**
	 * 获取Memory占用率
	 *
	 * @param snmpModel
	 * @return 正常返回当前内存使用率，否则返回-1
	 * @throws IOException
	 */
	public Integer getMemoryUtilization(SnmpModel snmpModel) {

		// 使用
		try {
			List<String> usedresultList = getInstanceSnmpDao().walkByTable(".1.3.6.1.2.1.25.2.3.1.6", snmpModel);
			// 总
			List<String> allresultList = getInstanceSnmpDao().walkByTable(".1.3.6.1.2.1.25.2.3.1.5", snmpModel);

			if (usedresultList != null && usedresultList.size() > 0 && allresultList != null
					&& allresultList.size() > 0) {

				double used = 0;
				// 最后一个才是使用的内存（单位是数目 ） 因系统而不同 本机有5项
				// System.out.println(usedresultList.size());
				// for(String s:usedresultList){
				// System.out.println(s);
				// }
				String usedStr = usedresultList.get(usedresultList.size() - 1);
				used = Double.parseDouble(usedStr);
				double all = 0;
				String allStr = allresultList.get(allresultList.size() - 1);
				all = Double.parseDouble(allStr);
				return (int) ((used / all) * 100);
			}
		} catch (Exception e) {
			logger.error("获取Memory占用率:" + e.getMessage());
		}
		return -1;
	}

	/**
	 * 测网络通不通 类似 ping ip
	 *
	 * @param snmpModel
	 * @return
	 * @throws IOException
	 */
	public boolean isEthernetConnection(SnmpModel snmpModel) throws IOException {

		InetAddress ad = InetAddress.getByName(snmpModel.getHostIp());
		boolean state = ad.isReachable(2000);// 测试是否可以达到该地址 2秒超时
		return state;
	}

	public static void main(String[] args) {
		SnmpService snmpService = new SnmpService();
		SnmpModel snmpModel = new SnmpModel();
		// snmpModel.setIp("127.0.0.1");
		snmpModel.setCommunityName("public");
		snmpModel.setHostIp("192.168.252.128");
		snmpModel.setPort(161);
		snmpModel.setVersion(1);
		try {
			// 是否连接
			System.out.println("是否连接：" + snmpService.isEthernetConnection(snmpModel));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("cpu利用率：" + snmpService.getCpuUtilization(snmpModel));
		System.out.println("内存使用率：" + snmpService.getMemoryUtilization(snmpModel));
	}

	/**
	 * 获取指定OID对应的table值
	 * 
	 * @param oid
	 * @param snmpModel
	 * @return
	 */
	public List<String> walkByTable(String oid, SnmpModel snmpModel) {
		// initSnmp(snmpModel);

		Snmp snmp = null;
		CommunityTarget target;
		List<String> result = new ArrayList<String>();

		try {
			DefaultUdpTransportMapping dm = new DefaultUdpTransportMapping();
			// dm.setSocketTimeout(5000);
			snmp = new Snmp(dm);
			snmp.listen();
			target = new CommunityTarget();
			target.setCommunity(new OctetString(snmpModel.getCommunityName()));
			target.setVersion(snmpModel.getVersion());
			target.setAddress(new UdpAddress(snmpModel.getHostIp() + "/" + snmpModel.getPort()));
			target.setTimeout(1000);
			target.setRetries(1);

			TableUtils tutils = new TableUtils(snmp, new DefaultPDUFactory(PDU.GETBULK));
			OID[] columns = new OID[1];
			columns[0] = new VariableBinding(new OID(oid)).getOid();
			List<TableEvent> list = (List<TableEvent>) tutils.getTable(target, columns, null, null);
			for (TableEvent e : list) {
				VariableBinding[] vb = e.getColumns();
				if (null == vb)
					continue;
				result.add(vb[0].getVariable().toString());
				// System.out.println(vb[0].getVariable().toString());
			}
			snmp.close();
		} catch (IOException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				if (snmp != null) {
					snmp.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return result;

	}
}
