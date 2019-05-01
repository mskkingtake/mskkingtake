package telnetAGCF;

public class TestAgcf {

	public static void main(String[] args) {
		String region = "471";
		String telphone = "3380000";
		String eid = "10.0.0.2";
		String tid = "5";
		String operateFlag = "0";
		
		
		operateAgcfModAsbr(region, telphone, eid, tid, operateFlag);
	}
	
	public static String operateAgcfModAsbr(String region, String telphone, String eid, String tid, String operateFlag) {
		// 日志信息
		System.out.println("---------网元操作的查询和操作---------");
		// 临时变量
		String returnMsg = "";
		
		// 调用接口
		try {
			
			
			// 获取执行命令-查询媒体网关号码
			StringBuffer inputParam = new StringBuffer();
			inputParam.append("ModAsbr_AGCF: PUI=\"sip:+86" + region + telphone + "@nm.ctcims.cn" + "\", ");
			inputParam.append("EID=\"" + eid + ":2944\", ");
			inputParam.append("TID=\"" + tid + "\", ");
			inputParam.append("MGN=\"NMAGCF\";");
			String queryGatewayNumCmd = inputParam.toString();
			
			StringBuffer inputSel = new StringBuffer();			
			inputSel.append("LST ASBR: TEN=EID, ");
			inputSel.append("EID=\"" + eid + ":2944\", ");
			inputSel.append("TID=\"" + tid + "\";");
			String querysel = inputSel.toString();
			
			if("1".equals(operateFlag)) {
				// 操作网元-执行命令
				returnMsg = operate(region, querysel);
				System.out.println("---------SPG移机结果查询-返回结果---------:");
				System.out.println(returnMsg);
				System.out.println("---------SPG移机结果查询-返回结果---------:");
			} else {
				// 操作网元-执行命令
				returnMsg = operate(region, queryGatewayNumCmd);
				System.out.println("---------SPG移机-返回结果---------:");
				System.out.println(returnMsg);
				System.out.println("---------SPG移机-返回结果---------:");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return returnMsg;
	}

	/**
	 * 网元设备操作-执行命令
	 * @param region
	 * @param queryCommand
	 * @return
	 */
	private static String operate(String region, String queryCommand) {
		// 获取对应网元名称
		String netUnit = ImsServicesConstantForNM.getNetUnit(region);
		// 获取登录TELNET命令
		String loginTelnetCmd = "LGI: OP=\"" + ImsServicesConstantForNM.USER + "\", PWD=\"" + ImsServicesConstantForNM.PASSWORD + "\";";
		// 获取登出TELNET命令
		String logoutTelnetCmd = "LGO: OP=\"" + ImsServicesConstantForNM.USER + "\";";
		// 获取登录网元命令
		String loginCmd = "REG NE: NAME=\"" + netUnit + "\";";
		// 获取登出网元命令
		String logoutCmd = "UNREG NE: NAME=\"" + netUnit + "\";";
		
		// 定义基本信息
		TelnetUtil telnet = null;
		TelnetOperatorUtil oper = null;
		String returnMsg = "";
		
		try {
			System.out.println("开始连接TELNET...");
//			telnet = new TelnetUtil(ImsServicesConstantForNM.IP, ImsServicesConstantForNM.PORT);
//			oper = new TelnetOperatorUtil(telnet);
			System.out.println("连接TELNET成功...");
		} catch (Exception e) {
			System.out.println("连接TELNET失败...");
			return "";
		}
		
		try {
			// 登录TELNET
			System.out.println("登录TELNET：命令---(" + loginTelnetCmd + ")");
//			oper.execute(loginTelnetCmd, ImsServicesConstantForNM.PROMPT);
			
			// 登录网元
			System.out.println("登录网元：命令---(" + loginCmd + ")");
//			oper.execute(loginCmd, ImsServicesConstantForNM.PROMPT);
			
			// 执行命令
			System.out.println("执行命令：命令---(" + queryCommand + ")");
//			returnMsg = oper.execute(queryCommand, ImsServicesConstantForNM.PROMPT);
			
			// 登出网元
			System.out.println("登出网元：命令---(" + logoutCmd + ")");
//			oper.execute(logoutCmd, ImsServicesConstantForNM.PROMPT);
			
			// 登出TELNET
			System.out.println("登出TELNET：命令---(" + logoutTelnetCmd + ")");
//			oper.execute(logoutTelnetCmd, ImsServicesConstantForNM.PROMPT);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			oper.close();
		}
		
		// 返回数据
		return returnMsg;
	}
}
