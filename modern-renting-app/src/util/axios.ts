import axios from "axios"

const AUTH_TOKEN = localStorage.getItem('access_token')
const apiUrl = import.meta.env.VITE_HOST || "";
const instance = axios.create({
    baseURL: apiUrl
})

instance.defaults.headers.common['Authorization'] = AUTH_TOKEN

export default instance
