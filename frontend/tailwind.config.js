/* @type {import('tailwindcss').Config} */
module.exports = {

	plugins: [
		require('flowbite/plugin'),
	],
	// Perhaps not needed. Only for interactive?
	content: [
		'./node_modules/flowbite/**/*.js',
		'./node_modules/flowbite-vue/**/*.{js,jsx,ts,tsx}',
		'./components/**/*.{js,vue,ts}',
		'./layouts/**/*.vue',
		'./pages/**/*.vue',
		'./plugins/**/*.{js,ts}',
		'./nuxt.config.{js,ts}',
	],
	theme: {
		extend: {},
	},
};
