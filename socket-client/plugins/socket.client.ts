import SockJS from "sockjs-client/dist/sockjs";
import Stomp from "webstomp-client";
const sendUriPrefix = "/pub";
const subscribeUriPrefix = "/sub";

export default defineNuxtPlugin(() => {
	const { apiServerUrl, socketEndpoint } = useRuntimeConfig().public;

	const socket = new SockJS(apiServerUrl + socketEndpoint);
	const stompClient = Stomp.over(socket);
	stompClient.connect(
		{},
		frame => {
			console.log(frame);
			stompClient.subscribe(subscribeUriPrefix + "/echo", message => {
				console.log("message : ", message);
			});
		},
		error => {
			console.log(error);
		},
	);
	return {
		provide: {
			socket: stompClient,
			sendUriPrefix,
			subscribeUriPrefix,
		},
	};
});
