import "./styles.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { useState } from "react";

import Login from "./Login";
import Join from "./Join";
import Home from "./Home";
import Logout from "./Logout";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  return (
    <div>
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
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
