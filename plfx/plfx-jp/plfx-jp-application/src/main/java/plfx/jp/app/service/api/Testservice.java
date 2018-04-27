package plfx.jp.app.service.api;

import org.springframework.stereotype.Service;

import plfx.api.client.api.v1.gj.request.CreateJPOrderGJCommand;
import plfx.api.client.api.v1.gj.response.CreateJPOrderGJResponse;
import plfx.api.client.common.api.PlfxApiAction;
import plfx.api.client.common.api.PlfxApiService;

//@Service
public class Testservice implements PlfxApiService {

	@PlfxApiAction(PlfxApiAction.GJ_CreateJPOrder)
	public CreateJPOrderGJResponse createJPOrderGJ(CreateJPOrderGJCommand command) {
		return null;
	}

}
