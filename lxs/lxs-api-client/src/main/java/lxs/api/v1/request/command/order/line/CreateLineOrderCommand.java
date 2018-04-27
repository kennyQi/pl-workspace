package lxs.api.v1.request.command.order.line;


import lxs.api.base.ApiPayload;
import lxs.api.v1.dto.order.line.LineOrderDTO;

@SuppressWarnings("serial")
public class CreateLineOrderCommand extends ApiPayload {

	private LineOrderDTO lineOrderDTO;

	public LineOrderDTO getLineOrderDTO() {
		return lineOrderDTO;
	}

	public void setLineOrderDTO(LineOrderDTO lineOrderDTO) {
		this.lineOrderDTO = lineOrderDTO;
	}

}
