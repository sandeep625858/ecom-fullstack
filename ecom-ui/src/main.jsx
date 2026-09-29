import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { createBrowserRouter, createRoutesFromElements, Route, RouterProvider } from 'react-router-dom'
import About from './components/About.jsx'
import Contact from './components/Contact.jsx'
import Login, { loginAction } from './components/Login.jsx'
import Cart from './components/Cart.jsx'
import Home from './components/Home.jsx'
import ErrorPage from './components/ErrorPage.jsx'
import { productsLoader } from './components/Home.jsx'
import { contactAction } from './components/Contact.jsx'
import { ToastContainer , Bounce } from 'react-toastify'
import "react-toastify/dist/ReactToastify.css"
import ProductDetail from './components/ProductDetail.jsx'
import CheckoutForm from './components/CheckoutForm.jsx'
import ProtectedRoute from './components/ProtectedRoute.jsx'
import Profile, { profileAction, profileLoader } from './components/Profile.jsx'
import Orders, { ordersLoader } from './components/Orders.jsx'
import AdminOrders, { adminOrdersLoader } from './components/admin/AdminOrders.jsx'
import Messages, { messagesLoader } from './components/admin/Messages.jsx'
import Register, { registerAction } from './components/Register.jsx'
import { loadStripe } from '@stripe/stripe-js'
import { Elements } from '@stripe/react-stripe-js'
import OrderSuccess from './components/OrderSuccess.jsx'
import store from './store/store.js'
import { Provider } from 'react-redux'

const stripePromise = loadStripe("pk_test_51UAVFa3DVCSxu5CBWV1WG56OTHuR7RVW8Nawb0suEVZyHEPanQN8ptpozlZ94lfYUmor43fE3KeCL1xgk7VoY1Hq00tUf8SweA");


const routeDefinitions = createRoutesFromElements(
  <Route path="/" element={<App/>} errorElement={<ErrorPage/>}>
    <Route index element={<Home/>} loader={productsLoader}/>
    <Route path="/home" element={<Home/>} loader={productsLoader}/>
    <Route path="/about" element={<About/>} />
    <Route path="/contact" element={<Contact/>} action={contactAction}/>
    <Route path="/login" element={<Login/>} action={loginAction}/>
    <Route path="/register" element={<Register/>} action={registerAction}/>
    <Route path="/cart" element={<Cart/>} />
    
    <Route path="/products/:productId" element={<ProductDetail/>} />
    <Route element={<ProtectedRoute/>}>
      <Route path="/checkout" element={<CheckoutForm/>} />
      <Route path="/order-success" element={<OrderSuccess/>} />
      <Route path="/profile" element={<Profile/>} loader={profileLoader} action={profileAction} 
      shouldRevalidate={({actionResult})=>{return !actionResult?.success;}}/>
      <Route path="/orders" element={<Orders/>} loader={ordersLoader}/>
      <Route path="/admin/orders" element={<AdminOrders/>} loader={adminOrdersLoader}/>
      <Route path="/admin/messages" element={<Messages/>} loader={messagesLoader} />
    </Route>
  </Route>
)

const appRouter = createBrowserRouter(routeDefinitions);

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Elements stripe={stripePromise}>
      <Provider store={store}>
        <RouterProvider router={appRouter} />
      </Provider>
      <ToastContainer
        position="top-center"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop={false}
        draggable
        pauseOnHover
        theme={localStorage.getItem("theme") === "dark" ? "dark" : "light"}
        transition={Bounce}
      />
    </Elements>
  </StrictMode>,
)
