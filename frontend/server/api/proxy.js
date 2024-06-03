import { defineEventHandler, getQuery, send } from 'h3';
import axios from 'axios';

export default defineEventHandler(async (event) => {
  const { url } = getQuery(event);

  if (!url) {
    return { error: 'URL parameter is required' };
  }

  try {
    const response = await axios.get(url, { responseType: 'arraybuffer' });
    return send(event, response.data, response.headers['content-type']);
  } catch (error) {
    console.error('Error proxying the image:', error);
    return { error: 'Failed to proxy the image' };
  }
});
