<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useFetch } from '#app'

const posts = ref([])

const { data: cats } = await useAsyncData('cats', () => $fetch('https://api.thecatapi.com/v1/images/search',
  {
    method: 'GET',
    params: {
      limit: 4
    }
  }
)) 
const fetchPostUrls = async (postId) => {
  try {
    const urls = await $fetch(`/api/post/${postId}/images/urls`, {
      method: 'GET'
    });
    return urls || [];
  } catch (error) {
    console.error(`Failed to fetch URLs for post ${postId}`, error);
    return [];
  }
};

const fetchPosts = async () => {
  try {
    const fetchedPosts = await $fetch('/api/posts/get', {
      method: 'GET',
      params: { desc: true, numberOfPosts: 20 }
    });
    
    const postsWithUrls = await Promise.all(fetchedPosts.map(async (post) => {
      const urls = (await fetchPostUrls(post.id)).map(getProxiedUrl);
      return { ...post, urls };
    }));
    
    posts.value = postsWithUrls as never[];
  } catch (error) {
    console.error('Failed to fetch posts', error);
  }
};

const getProxiedUrl = (url: string) => {
  return `/api/proxy?url=${encodeURIComponent(url)}`;
}

const handlePostModified = (updatedPost) => {
  console.log('Post modified, updating post data', updatedPost);
  const index = posts.value.findIndex(post => post.id === updatedPost.id);
  if (index !== -1) {
    posts.value[index] = updatedPost;
  }
}

// initialize components based on data attribute selectors
onMounted(() => {
  fetchPosts();
})
</script>


<template>
  <div class="flex flex-col min-h-screen bg-slate-500">
    <main class="mx-auto my-5 flex w-full max-w-3xl grow flex-col items-center justify-start rounded-2xl bg-gray-100/95 p-4 px-2 shadow-2xl sm:px-6 lg:px-8 space-y-4">
      <h1 class="text-5xl font-bold">
        {{ $config.public.someUrl || 'Placeholder'}}
      </h1>
      <Carousel :cats="cats"/>
      <h1 class="text-5xl font-bold">Add Post</h1>
      <section class="min-h-[200px] w-full">
      <AddPostForm @formSubmitted="fetchPosts()" />
      </section>
      <h1 class="text-5xl font-bold">Posts</h1>
      <section class="w-full flex flex-col items-center justify-start bg-slate-200 rounded-xl space-y-4">
        <Post v-for="post in posts" :key="post.id" :post="post" @postModified="handlePostModified"/>
      </section>
    </main>
  </div>
</template>

