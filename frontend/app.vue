<template>
  <div class="flex flex-col min-h-screen bg-slate-500">
    <main class="mx-auto my-5 flex w-full max-w-3xl grow flex-col items-center justify-start rounded-2xl bg-gray-100/95 p-4 px-2 shadow-2xl sm:px-6 lg:px-8">
      <h1 class="text-5xl font-bold">
        {{ $config.public.someUrl || 'No Value'}}
      </h1>
      <h1 class="text-5xl font-bold">Add Post</h1>
      <section class="min-h-[200px] w-full bg-slate-600"></section>
      <h1 class="text-5xl font-bold">Posts</h1>
      <section class="w-full grow flex-col items-center justify-start bg-slate-200 rounded-xl">
        <div v-for="post in posts" :key="post.id" class="max-h-[440px] border-2 border-black rounded-xl p-4">
          <h2 class="text-3xl font-bold"> {{ post.title }} </h2>
          <p> {{ post.content }} </p>
          <div v-for="url in post.urls" :key="url">
            <a :href="url">{{ url }}</a>
            <img :src="url">
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useFetch } from '#app'

const posts = ref([])

const fetchPostUrls = async (postId) => {
  try {
    const { data } = await useFetch(`/api/post/${postId}/images/urls`, {
      method: 'GET'
    })
    return data.value || []
  } catch (error) {
    console.error(`Failed to fetch URLs for post ${postId}`, error)
    return []
  }
}

const fetchPosts = async () => {
  try {
    const { data } = await useFetch('/api/posts/get', {
      method: 'GET',
      params: { desc: true, numberOfPosts: 3 }
    })
    const fetchedPosts = data.value || []
    const postsWithUrls = await Promise.all(fetchedPosts.map(async (post) => {
      const urls = await fetchPostUrls(post.id)
      return { ...post, urls }
    }))
    posts.value = postsWithUrls
  } catch (error) {
    console.error('Failed to fetch posts', error)
  }
}

onMounted(fetchPosts)
</script>
