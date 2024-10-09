// BackButton.js
import React from "react";
import { useNavigate } from "react-router-dom";

const BackButton = ({ to }) => {
  const navigate = useNavigate();

  return <button onClick={() => navigate(to)}>Back to Home</button>;
};

export default BackButton;
