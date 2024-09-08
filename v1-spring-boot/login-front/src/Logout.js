import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import axios from "axios";
import { useEffect } from "react";

const Logout = ({ setIsAuthenticated }) => {
  const navigate = useNavigate();

  const logoutHandler = async () => {
    return await axios.post("http://localhost:8080/logout", {});
  };

  useEffect(() => {
    logoutHandler()
      .then((response) => {
        console.log(response.data);
        toast.success(response.data, {
          toastId: "logoutSuccess1",
        });
        setIsAuthenticated(false);
        navigate("/");
      })
      .catch((error) => {
        console.error(error.response?.data || error.message);
        toast.error(error.response?.data || "Logout failed.");
      });
  }, []);
};

export default Logout;
