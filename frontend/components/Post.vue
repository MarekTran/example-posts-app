<script setup>
import { onMounted } from 'vue'
import { initCarousels, } from 'flowbite'

// initialize components based on data attribute selectors
onMounted(() => {
  initCarousels();
})

const props = defineProps(['post'])
const getProxiedUrl = (url) => {
  return `/api/proxy?url=${encodeURIComponent(url)}`;
}
</script>

<template>
  <div class="bg-gray-300/50 rounded-xl max-h-[50rem] flex flex-col items-center justify-start p-4">
    <h2 class="text-3xl font-bold"> {{ post.title }} </h2>
    <p> {{ post.content }} </p>
    <div id="indicators-carousel" class="relative min-w-[12rem]" data-carousel="static">
      <!-- Carousel wrapper -->
      <div class="relative aspect-square min-h-[12rem] max-h-[48rem] overflow-hidden rounded-lg md:h-96">
        <div v-for="(url, index) in post.urls" :key="url" class="hidden duration-700 ease-in-out"
          :data-carousel-item="index === 0 ? 'active' : ''">
          <img :src="getProxiedUrl(url)" class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2"
            alt="post image" />
        </div>
      </div>
      <!-- Slider indicators -->
      <div class="absolute z-30 flex -translate-x-1/2 space-x-3 rtl:space-x-reverse bottom-5 left-1/2">
        <button v-for="(url, index) in post.urls" :key="`indicator-${url}`" type="button" class="w-3 h-3 rounded-full"
          :aria-current="index === 0 ? 'true' : 'false'" :aria-label="`Slide ${index + 1}`"
          :data-carousel-slide-to="index">
        </button>
      </div>
      <!-- Slider controls -->
      <button type="button"
        class="absolute top-0 start-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none"
        data-carousel-prev>
        <span
          class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-slate-400/60 dark:bg-gray-800/30 group-hover:bg-gray-800/30 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
          <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M5 1 1 5l4 4" />
          </svg>
          <span class="sr-only">Previous</span>
        </span>
      </button>
      <button type="button"
        class="absolute top-0 end-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none"
        data-carousel-next>
        <span
          class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-slate-400/60 dark:bg-gray-800/30 group-hover:bg-gray-800/30 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
          <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="m1 9 4-4-4-4" />
          </svg>
          <span class="sr-only">Next</span>
        </span>
      </button>
    </div>
  </div>
</template>