package hgtech.jfaccount.listener;

import hgtech.jfaccount.JfFlow;

public interface FlowListener {

	void beforeSaveFlow(JfFlow flow);
	void afterSaveFlow(JfFlow flow);
}
