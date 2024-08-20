import "./styles.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./Login";
import Join from "./Join";
import Home from "./Home";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/join" element={<Join />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
