<script setup lang="ts">
import { onMounted } from 'vue';
import { initCarousels } from 'flowbite';

// Interface for the post object
interface Post {
	id: number;
	title: string;
	content: string;
	createdAt: string; // LocalDateTime will be a string in JSON
	upvote: number;
	downvote: number;
	approved: boolean;
	urls: string[];
}

// initialize components based on data attribute selectors
onMounted(() => {
	initCarousels();
});

// Define props with type
const props = defineProps<{
	post: Post;
}>();

const emit = defineEmits(['postModified']);

const handleUpvote = async (postId: number) => {
	console.log('Upvoting postID:', postId);
	try {
		const updatedPost = await $fetch(`/api/post/${postId}/upvote`, {
			method: 'POST',
		});
		console.log(updatedPost);
		if (updatedPost) {
			props.post.upvote = updatedPost.upvote;
			console.log('Post upvoted:', props.post);
			emit('postModified', props.post); // Emit the updated post
		}
	}
	catch (error) {
		console.error('Error upvoting the post:', error);
	}
};

const handleDownvote = async (postId: number) => {
	console.log('Downvoting postID:', postId);
	try {
		const updatedPost = await $fetch(`/api/post/${postId}/downvote`, {
			method: 'POST',
		});
		console.log(updatedPost);
		if (updatedPost) {
			props.post.downvote = updatedPost.downvote;
			console.log('Post downvoted:', props.post);
			emit('postModified', props.post); // Emit the updated post
		}
	}
	catch (error) {
		console.error('Error downvoting the post:', error);
	}
};

const modifyApproval = async (postId: number, approval: boolean) => {
	console.log('Unapproving postID:', postId);
	try {
		const updatedPost = await $fetch(`/api/post/modify/approval`, {
			method: 'POST',
			params: { postId, approved: approval },
		});
		console.log(updatedPost);
		if (updatedPost) {
			props.post.approved = updatedPost.approved;
			console.log('Post unapproved:', props.post);
			emit('postModified', props.post); // Emit the updated post
		}
	}
	catch (error) {
		console.error('Error unapproving the post:', error);
	}
};
</script>

<template>
	<div class="bg-gray-300/50 rounded-xl min-w-[26rem] max-h-[50rem] flex flex-col items-center justify-start p-4">
		<h2 class="text-3xl font-bold">
			{{ post.title }}
		</h2>
		<p> {{ post.content }} </p>
		<div
			v-if="post.urls && post.urls.length > 0"
			id="indicators-carousel"
			class="relative min-w-[12rem]"
			data-carousel="static"
		>
			<!-- Carousel wrapper -->
			<div class="relative aspect-square min-h-[12rem] overflow-hidden rounded-lg md:h-96">
				<div
					v-for="(url, index) in post.urls"
					:key="url"
					class="hidden duration-700 ease-in-out"
					:data-carousel-item="index === 0 ? 'active' : ''"
				>
					<!-- Main image -->
					<img
						:src="url"
						class="absolute h-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2"
						alt="post image"
					>
					<!-- Blurred background image -->
					<img
						:src="url"
						class="absolute top-1/2 left-1/2 w-full block -translate-x-1/2 -translate-y-1/2 object-cover blur-lg -z-10"
						alt="post image blurred"
					>
				</div>
			</div>
			<!-- Slider indicators -->
			<div class="absolute z-30 flex -translate-x-1/2 space-x-3 rtl:space-x-reverse bottom-5 left-1/2">
				<button
					v-for="(url, index) in post.urls"
					:key="`indicator-${url}`"
					type="button"
					class="w-3 h-3 rounded-full"
					:aria-current="index === 0 ? 'true' : 'false'"
					:aria-label="`Slide ${index + 1}`"
					:data-carousel-slide-to="index"
				/>
			</div>
			<!-- Slider controls -->
			<button
				type="button"
				class="absolute top-0 start-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none"
				data-carousel-prev
			>
				<span
					class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-slate-400/60 dark:bg-gray-800/30 group-hover:bg-gray-800/30 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none"
				>
					<svg
						class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180"
						aria-hidden="true"
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 6 10"
					>
						<path
							stroke="currentColor"
							stroke-linecap="round"
							stroke-linejoin="round"
							stroke-width="2"
							d="M5 1 1 5l4 4"
						/>
					</svg>
					<span class="sr-only">Previous</span>
				</span>
			</button>
			<button
				type="button"
				class="absolute top-0 end-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none"
				data-carousel-next
			>
				<span
					class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-slate-400/60 dark:bg-gray-800/30 group-hover:bg-gray-800/30 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none"
				>
					<svg
						class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180"
						aria-hidden="true"
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 6 10"
					>
						<path
							stroke="currentColor"
							stroke-linecap="round"
							stroke-linejoin="round"
							stroke-width="2"
							d="m1 9 4-4-4-4"
						/>
					</svg>
					<span class="sr-only">Next</span>
				</span>
			</button>
		</div>
		<!-- Carousel wrapper end -->
		<div class="flex w-full justify-between p-4">
			<button
				class="bg-green-300 hover:bg-green-500 text-white font-bold py-2 px-2 rounded-full"
				@click="handleUpvote(post.id)"
			>
				Upvotes {{ post.upvote }}
				⬆️
			</button>
			<button
				class="bg-blue-300 hover:bg-blue-500 text-white font-bold py-2 px-2 rounded-full"
				@click="modifyApproval(post.id, false)"
			>
				Unapprove ❌
			</button>
			<button
				class="bg-blue-300 hover:bg-blue-500 text-white font-bold py-2 px-2 rounded-full"
				@click="modifyApproval(post.id, true)"
			>
				Approve ✅
			</button>
			<button
				class="bg-red-300 hover:bg-red-500 text-white font-bold py-2 px-2 rounded-full"
				@click="handleDownvote(post.id)"
			>
				Downvotes {{ post.downvote }}
				⬇️
			</button>
		</div>
	</div>
</template>
