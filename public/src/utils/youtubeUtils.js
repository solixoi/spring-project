export function getYoutubeEmbedUrl(url) {
    try {
      if (url.includes('youtube.com')) {
        const params = new URLSearchParams(url.split('?')[1]);
        const videoId = params.get('v');
        return `https://www.youtube.com/embed/${videoId}`;
      } else if (url.includes('youtu.be')) {
        const videoId = url.split('youtu.be/')[1].split('?')[0];
        return `https://www.youtube.com/embed/${videoId}`;
      }
    } catch (error) {
      console.error('Ошибка обработки YouTube ссылки:', error);
    }
    return '';
  }