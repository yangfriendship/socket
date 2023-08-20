import { RequestHeaders } from "h3";

const { apiServerUrl, apiEndpoint } = useRuntimeConfig().public;
export default defineEventHandler(event => {
	const targetUrl = event.node.req.url;
	if (!targetUrl) return;

	if (targetUrl.startsWith(apiEndpoint)) {
		const target = new URL(targetUrl, apiServerUrl as string);
		const headers: RequestHeaders = {
			host: target.host,
		};
		return proxyRequest(event, target.toString(), {
			headers,
		});
	}
});
