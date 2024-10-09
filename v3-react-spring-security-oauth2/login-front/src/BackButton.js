import React from "react";
import { useNavigate } from "react-router-dom";

const BackButton = ({ to }) => {
  const navigate = useNavigate();

  return (
    <button className={"back_button"} onClick={() => navigate(to)}>
      Back
    </button>
  );
};

export default BackButton;
