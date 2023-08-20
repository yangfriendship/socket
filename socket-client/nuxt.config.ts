// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
	runtimeConfig: {
		public: {
			apiServerUrl: process.env.NUXT_API_SERVER_URL,
			apiEndpoint: process.env.NUXT_API_URL_ENDPOINT,
			socketEndpoint: process.env.NUXT_SOCKET_URL_ENDPOINT,
		},
	},
	devtools: { enabled: true },
	css: ["~/assets/css/main.css"],
	postcss: {
		plugins: {
			tailwindcss: {},
			autoprefixer: {},
		},
	},
});
