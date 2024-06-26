// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
	devtools: { enabled: false },
	modules: ['@nuxtjs/tailwindcss',
		'@nuxt/eslint',
	],
	// css: [
	//   '~/node_modules/flowbite-vue/dist/index.css'
	// ],
	tailwindcss: {
		cssPath: '~/assets/css/main.css',
	},
	runtimeConfig: {
		// The private keys which are only available within server-side
		proxyUrl: process.env.NUXT_PROXY_URL,
		bucketProxyUrl: process.env.NUXT_BUCKET_PROXY_URL,
		public: {
			// The public keys which are available on both server and client
			someUrl: process.env.PROXY_URL,
		},
	},
	eslint: {
		config: {
			stylistic: {
				indent: 'tab',
				semi: true,
				// ...
			},
		},
	},
});
