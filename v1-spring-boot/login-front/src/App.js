import "./styles.css";
import { BrowserRouter, Route, Routes, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

import Login from "./Login";
import Join from "./Join";
import Home from "./Home";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Logout from "./Logout";
import axios from "axios";
import OAuthCallback from "./Oauth_callback";

axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.withCredentials = true;

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const checkAuthStatus = async () => {
    try {
      const response = await axios.get("/session");

      if (response.data.isAuthenticated) {
        setIsAuthenticated(true);
      } else {
        setIsAuthenticated(false);
      }
    } catch (error) {
      setIsAuthenticated(false);
      console.error("Session check failed", error);
    }
  };

  useEffect(() => {
    checkAuthStatus();
  }, []);

  return (
    <div>
      <ToastContainer
        position="top-center"
        autoClose={1000}
        draggable
        pauseOnHover
        limit={1}
      />
      <BrowserRouter>
        <Routes>
          <Route
            path="/"
            element={<Home isAuthenticated={isAuthenticated} />}
          />
          <Route
            path="/login"
            element={<Login setIsAuthenticated={setIsAuthenticated} />}
          />
          <Route path="/join" element={<Join />} />
          <Route
            path="/logout"
            element={<Logout setIsAuthenticated={setIsAuthenticated} />}
          />
          <Route
            path="/oauth/google/callback"
            element={
              <OAuthCallback
                provider="google"
                setIsAuthenticated={setIsAuthenticated}
              />
            }
          />
          <Route
            path="/oauth/naver/callback"
            element={
              <OAuthCallback
                provider="naver"
                setIsAuthenticated={setIsAuthenticated}
              />
            }
          />
          <Route
            path="/oauth/kakao/callback"
            element={
              <OAuthCallback
                provider="kakao"
                setIsAuthenticated={setIsAuthenticated}
              />
            }
          />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
