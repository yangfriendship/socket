import { RequestHeaders } from "h3";

const { apiServerUrl, apiEndpoint, socketEndpoint } = useRuntimeConfig().public;
const proxyUrls = [apiEndpoint, socketEndpoint];
export default defineEventHandler(event => {
	const targetUrl = event.node.req.url;
	if (!targetUrl) return;

	const shouldBeProxyRequest = proxyUrls.some(url => targetUrl.startsWith(url));
	if (shouldBeProxyRequest) {
		const target = new URL(targetUrl, apiServerUrl as string);
		const headers: RequestHeaders = {
			host: target.host,
			...(targetUrl.startsWith(socketEndpoint) && {
				upgrade: "websocket",
				Connection: "Upgrade",
			}),
		};
		return proxyRequest(event, target.toString(), {
			headers,
		});
	}
});
