import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { Auth0Provider } from '@auth0/auth0-react'
import { BrowserRouter } from 'react-router-dom'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Auth0Provider
    domain="eventplatform0.eu.auth0.com"
    clientId="z6D6cixS2HsPzRKmQwP8grTBVUgfMRao"
    authorizationParams={{
      redirect_uri: window.location.origin,
      audience: "https://eventplatform/api"
    }}
  >
    <BrowserRouter>
    <App />
    </BrowserRouter>
    </Auth0Provider>
  </StrictMode>,
)
