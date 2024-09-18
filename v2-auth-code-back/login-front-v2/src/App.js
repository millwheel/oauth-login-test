import "./styles.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { useEffect, useState } from "react";

import Login from "./Login";
import Join from "./Join";
import Home from "./Home";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Logout from "./Logout";
import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.withCredentials = true;

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");

  const checkAuthStatus = async () => {
    return await axios.get("/session");
  };

  useEffect(() => {
    checkAuthStatus()
      .then((response) => {
        if (response.data.isAuthenticated) {
          setIsAuthenticated(true);
        } else {
          setIsAuthenticated(false);
        }
      })
      .catch((error) => {
        setIsAuthenticated(false);
        console.error("Session check failed", error);
      });
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
            element={
              <Home
                isAuthenticated={isAuthenticated}
                name={name}
                email={email}
              />
            }
          />
          <Route
            path="/login"
            element={<Login setIsAuthenticated={setIsAuthenticated} setEmail={setEmail} setName={setName} />}
          />
          <Route path="/join" element={<Join />} />
          <Route
            path="/logout"
            element={<Logout setIsAuthenticated={setIsAuthenticated} />}
          />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
