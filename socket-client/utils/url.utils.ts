const removeLeadingSlash = (uri: string) =>
	uri.startsWith("/") ? uri.substring(1) : uri;

export default {
	removeLeadingSlash,
};
