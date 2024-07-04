import { UserConfigExport, ConfigEnv } from 'vite'
import react from '@vitejs/plugin-react-swc'
import { viteMockServe } from 'vite-plugin-mock'

// https://vitejs.dev/config/
export default ({ command }: ConfigEnv): UserConfigExport => {
  return {
    plugins: [
      react(),
      viteMockServe({
        mockPath: 'mock',
        enable: command === 'serve',
      }),
    ],
  }
}
