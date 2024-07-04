import axios from "axios"

const AUTH_TOKEN = localStorage.getItem('access_token')

const instance = axios.create({
    baseURL: '/'
})

instance.defaults.headers.common['Authorization'] = AUTH_TOKEN

export default instance
