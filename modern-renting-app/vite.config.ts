import { UserConfigExport, ConfigEnv, HttpProxy, ProxyOptions } from "vite";
import react from "@vitejs/plugin-react-swc";
import { viteMockServe } from "vite-plugin-mock";
import { defineConfig, loadEnv } from 'vite';
import { resolve } from 'path'


// https://vitejs.dev/config/
export default ({ command, mode }: ConfigEnv): UserConfigExport => {
  const env = loadEnv(mode, process.cwd());
  const PROXY_TARGET = env.VITE_PROXY_TARGET || '0.0.0.0';
  const PROXY_OAUTH_PORT = env.VITE_PROXY_OAUTH_PORT || '8080';
  const PROXY_USER_PORT = env.VITE_PROXY_USER_PORT || '8080';
  const PROXY_VEHICLE_PORT = env.VITE_PROXY_VEHICLE_PORT || '8080';
  const PROXY_RENTAL_PORT = env.VITE_PROXY_RENTAL_PORT || '8080';
  const logConf: ((proxy: HttpProxy.Server, options: ProxyOptions) => void) = (proxy, options) => {
    proxy.on("proxyReq", (proxyReq, req, _res) => {
      console.log(
        "Sending Request to the Target:",
        options.target + proxyReq.path
      );
    });
  };
  return {
    root: '.',
    // envPrefix: 'REACT_',
    // envDir: './env',
    server: {
      proxy: {
        "/oauth/api": {
          target: PROXY_TARGET + ':' + PROXY_OAUTH_PORT,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/oauth\/api/, ""),
          configure: logConf
        },
        "/users/api": {
          target: PROXY_TARGET + ':' + PROXY_USER_PORT,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/users\/api/, ""),
          configure: logConf
        },
        "/vehicles/api": {
          target: PROXY_TARGET + ':' + PROXY_VEHICLE_PORT,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/vehicles\/api/, ""),
          configure: logConf
        },
        "/rentals/api": {
          target: PROXY_TARGET + ':' + PROXY_RENTAL_PORT,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/rentals\/api/, ""),
          configure: logConf
        },
      },
    },
    resolve: {
      alias: {
        '@': resolve(__dirname, 'src')
      }
    },
    plugins: [
      react(),
      // viteMockServe({
      //   mockPath: 'mock',
      //   enable: command === 'serve',
      // }),
    ],
  };
};
