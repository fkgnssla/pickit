import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import mkcert from 'vite-plugin-mkcert';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), mkcert()],
  server: {
    proxy: {
      '/api': {
        target: 'https://j11a309.p.ssafy.io',
        changeOrigin: true,
        secure: false, // HTTPS 요청이 아닌 경우 설정
        rewrite: (path) => path.replace(/^\/api/, ''), // /api를 제거하고 실제 경로로 전달
      },
    },
    https: true,  // HTTPS를 활성화
    // port: 5173,   // 원하는 포트 설정
  },
});
