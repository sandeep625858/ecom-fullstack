import './App.css'
import Footer from './components/footer/Footer';
import Header from './components/Header';
import { Outlet, useNavigation } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const navigate = useNavigation()
  return (
    <>
      <Header />
      {navigate.state === "loading"? (
        <div className="flex items-center justify-center min-h-[852px]">
          <span className="text-4xl font-semibold text-primary dark:text-light">
            Loading...
          </span>
        </div>
      ) : <Outlet />}
      <Footer />
    </>
    
  );
}

export default App
