import './App.css'
import Navbar from './components/Navbar';
import Login from './Login'
import Profile from './Profile';
import { useAuth0 } from '@auth0/auth0-react';
import { Routes, Route } from 'react-router-dom';
import Events from './pages/Events';
import AllEvents from './pages/AllEvents';
import Report from './pages/Report';
import Bookings from './pages/Bookings.';

function App() {

  const { isAuthenticated, isLoading } = useAuth0();
  if (isLoading) {
    return <div>Loading ...</div>;
  }
  return (
    <div className="App">
      {isAuthenticated ? <><Navbar/></> : <Login />}
        <Routes>
          <Route path="/" element={<Profile />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/events" element={<Events />} />
          <Route path="/allevents" element={<AllEvents />} />
          <Route path="/report" element={<Report/>} />
          <Route path="/bookings" element={<Bookings/>} />
        </Routes>
      
      
    </div>
  )
}

export default App
