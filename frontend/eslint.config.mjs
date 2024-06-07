import withNuxt from './.nuxt/eslint.config.mjs';

export default withNuxt(
	{
		files: ['server/**/*.ts', 'server/**/*.vue', 'pages/**/*.ts', 'pages/**/*.vue', 'components/**/*.ts', 'components/**/*.vue'],
		rules: {
			'no-console': 'off', // example rule, allow console.log in specified directories
			// add more custom rules here
		},
		// {
		// more configs here
		// },
	},
);
