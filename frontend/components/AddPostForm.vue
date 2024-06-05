<template>
  <!-- FORM CONTAINER-->
  <div class="flex w-full flex-col items-center rounded-xl bg-red-300/50 py-4">
    <form @submit.prevent="handleSubmit" class="grid grid-cols-2 gap-4 p-4">
      <label for="title">Title</label>
      <input type="text" id="title" v-model="formData.title" maxlength="30"
        placeholder="Enter title (max 30 characters)" required />

      <label for="content">Content</label>
      <textarea id="content" v-model="formData.content" maxlength="80" placeholder="Enter content (max 80 characters)"
        required></textarea>

      <input type="file" ref="fileInput" @change="handleFileUpload" multiple accept="image/*" />
    </form>
    <!-- IMAGE PREVIEW AREA -->
    <div class="grid grid-cols-3 gap-4 p-4" v-if="images.length">
      <div v-for="(image, index) in images" :key="index" class="relative aspect-square min-h-32 overflow-hidden">
        <img :src="image.url" class="absolute left-1/2 top-1/2 h-full -translate-x-1/2 -translate-y-1/2"
          :alt="'Image ' + (index + 1)" />
      </div>
    </div>
    <!-- IMAGE PREVIEW AREA -->
    <button @click="handleSubmit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">Submit</button>
  </div>
  <!-- FORM CONTAINER-->

</template>

<script setup>
import { defineEmits } from 'vue';
import { ref } from 'vue';
const emit = defineEmits(['formSubmitted']);
// Form Logic
const formData = ref({
  title: '',
  content: ''
});

const fileInput = ref(null);

const handleSubmit = async () => {
  const data = new FormData();
  data.append('title', formData.value.title);
  data.append('content', formData.value.content);
  images.value.forEach((image) => {
    data.append('files', image.file);
  });
  try {
    const response = await $fetch('/api/post/new', {
      method: 'POST',
      body: data,
    });
    console.log('Form submitted with response:', response);
    emit('formSubmitted');
    // Clear the form after successful submission
    formData.value = {
      title: '',
      content: ''
    };
    images.value = [];
    // Clear the file input field
    fileInput.value.value = '';
  } catch (error) {
    console.error('Error submitting form:', error);
  }
};

// Image Logic
const images = ref([]);

const handleFileUpload = (event) => {
  const files = event.target.files;
  images.value = [];
  for (let i = 0; i < files.length; i++) {
    const reader = new FileReader();
    reader.onload = (e) => {
      images.value.push({ url: e.target.result, file: files[i] });
    };
    reader.readAsDataURL(files[i]);
  }
};
</script>