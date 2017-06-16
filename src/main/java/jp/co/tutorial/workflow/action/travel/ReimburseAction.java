package jp.co.tutorial.workflow.action.travel;

import org.seasar.struts.annotation.Execute;

public class ReimburseAction {
	
	private static final String INDEX = "/travel/reimburse.jsp";
//	private static final String INDEX = "/leave/apply/index.jsp";
	
	@Execute(validator = false)
	public String index() {
		return INDEX;
	}
}
