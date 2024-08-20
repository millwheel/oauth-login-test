import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Join({ setIsAuthenticated }) {
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (password !== passwordConfirm) {
      setError("Passwords do not match");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/join", {
        email,
        name,
        password,
      });

      console.log("Signup successful:", response.data);
      setIsLoggedIn(true); // Set login state to true after successful signup
      navigate("/"); // Redirect to home page after signup
    } catch (error) {
      setError("Sign-up failed");
      console.error(error.response?.data || error.message);
    }
  };

  return (
    <div className="frame">
      <div className="container">
        <h1>Join format</h1>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <form onSubmit={handleSubmit}>
          <input
            type="email"
            placeholder="Email"
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="text"
            placeholder="Name"
            onChange={(e) => setName(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password"
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password Confirm"
            onChange={(e) => setPasswordConfirm(e.target.value)}
            required
          />
          <button className="submit_button" type="submit">
            Submit
          </button>
        </form>
      </div>
    </div>
  );
}

export default Join;
