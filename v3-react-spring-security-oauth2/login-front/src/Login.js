import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";
import OAuthLogin from "./OAuth";

function Login({ setIsAuthenticated }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleEmailLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/login", {
        email,
        password,
      });

      console.log(response.data);
      toast.success(response.data);

      setIsAuthenticated(true);
      navigate("/");
    } catch (error) {
      setError(error.response?.data);
      console.error(error.response?.data || error.message);
    }
  };

  return (
    <div className="frame">
      <div className="container">
        <h1>Login format</h1>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <form onSubmit={handleEmailLogin}>
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button className="submit_button" type="submit">
            Submit
          </button>
        </form>
        <div className="oauth2_container">
          <OAuthLogin />
        </div>
      </div>
    </div>
  );
}

export default Login;
