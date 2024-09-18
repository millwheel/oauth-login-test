import React, { useEffect } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";

function OAuthCallback() {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const code = params.get("code");

    if (code) {
      // Call the backend with the authorization code
      axios
          .get(`http://localhost:8080/oauth/google/callback?code=${code}`)
          .then((response) => {
            console.log(response.data);
            const { accessToken, email, name } = response.data;

            // Store token in localStorage or context for later use
            localStorage.setItem("accessToken", accessToken);

            // Do something with the user info, like updating auth state
            console.log(`Logged in as ${name} (${email})`);

            // Redirect to the main page or user dashboard
            navigate("/");
          })
          .catch((error) => {
            console.error("Error during Google login:", error);
            // Handle login error
          });
    }
  }, [location, navigate]);

  return <div>Processing login...</div>;
}

export default OAuthCallback;

