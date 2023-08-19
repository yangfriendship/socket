export default defineEventHandler(event => {
    if (!event.node.req.url?.startsWith("/api")) return;

    const target = new URL(event.node.req.url, "http://localhost:8080");

    return proxyRequest(event, target.toString(), {
        headers: {
            host: target.host,
        },
    });
});
