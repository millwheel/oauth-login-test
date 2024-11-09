import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useEffect } from "react";

const Logout = () => {
  const navigate = useNavigate();

  const logoutHandler = async () => {
    return await axios.post("http://localhost:8080/logout", {});
  };

  useEffect(() => {
    logoutHandler()
      .then((response) => {
        navigate("/");
        window.location.reload();
      })
      .catch((error) => {
        console.error(error.response?.data || error.message);
      });
  }, []);
};

export default Logout;
