import { joinURL } from 'ufo';

export default defineEventHandler(async (event) => {
	// Get the runtimeconfig proxy url
	const bucketProxyUrl = useRuntimeConfig().bucketProxyUrl;
	// check the path
	const path = event.path.replace(/^\/img\//, ''); // /img/posts -> posts
	const target = joinURL(bucketProxyUrl, path);

	// proxy it!
	return proxyRequest(event, target);
});
