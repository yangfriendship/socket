const { apiServerUrl, apiEndpoint } = useRuntimeConfig().public;
export default defineEventHandler(event => {
	const targetUrl = event.node.req.url;
	if (!targetUrl) return;

	if (targetUrl.startsWith(apiEndpoint)) {
		const target = new URL(targetUrl, apiServerUrl as string);
		return proxyRequest(event, target.toString(), {
			headers: {
				host: target.host,
				origin: target.origin,
			},
		});
	}
});
