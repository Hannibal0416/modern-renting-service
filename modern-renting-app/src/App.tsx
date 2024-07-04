import { Routes, Route } from 'react-router-dom'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'

import './App.css'

import Home from './pages/Home'
import Profile from './pages/Profile'
import Collection from './pages/Collection'
import Search from './pages/Search'
import Detail from './pages/Detail'

import HeaderBar from './components/HeaderBar'
import FooterBar from './components/FooterBar'

// Create a client
const queryClient = new QueryClient()

function App() {

  return (
    <QueryClientProvider client={queryClient}>
      <div>
        <HeaderBar title='Modern Retailing'/>
        <div>
          <Routes>
              <Route element={<Home />} path={'/'}></Route>
              <Route element={<Profile />} path='/profile'></Route>
              <Route element={<Collection />} path='/collection'></Route>
              <Route element={<Search />} path='/search'></Route>
              <Route element={<Detail />} path='/detail/:id'></Route>
          </Routes>
        </div>
        <FooterBar />
      </div>
    </QueryClientProvider>
  )
}

export default App
