package com.rayfay.bizcloud.core.commons.clientinfo;

import com.rayfay.bizcloud.core.commons.message.UpdateClientInfoChannelListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shenfu on 2017/7/12.
 */
@Configuration
@RestController
@RequestMapping("/v2")
@EnableBinding(UpdateClientInfoChannelListener.class)
public class ClientInfoController {
	private Logger logger = LoggerFactory.getLogger(ClientInfoController.class);
	private final ClientInfo clientInfo;

	@Autowired
	public ClientInfoController(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	@RequestMapping(value = "/showClientInfo", method = RequestMethod.GET)
	public Object showClientInfo() {
		return clientInfo;
	}

	@StreamListener(UpdateClientInfoChannelListener.UPDATE_CLIENT_INFO_LISTENER)
	public void updateClientInfo(ClientInfo updateClientInfoMessage) {
		if (updateClientInfoMessage.getClientId().equals(clientInfo.getClientId())) {
			logger.info(
					String.format("更新本客户端【%s】的ClientKey为【%s】", clientInfo.getClientId(), clientInfo.getClientKey()));
			clientInfo.setClientKey(updateClientInfoMessage.getClientKey());
			clientInfo.setClientId(updateClientInfoMessage.getClientId());
		}
	}
}