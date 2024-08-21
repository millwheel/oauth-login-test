import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import axios from "axios";

const Logout = ({ setIsAuthenticated }) => {
  const navigate = useNavigate();

  const logoutHandler = async () => {
    try {
      const response = await axios.post("http://localhost:8080/logout", {});

      console.log(response.data);
      toast.success(response.data, {
        toastId: "logoutSuccess1",
      });

      // Update authentication state
      setIsAuthenticated(false);

      // Navigate to home after logout
      navigate("/");
    } catch (error) {
      console.error(error.response?.data || error.message);
      toast.error(error.response?.data || "Logout failed.");
    }
  };

  logoutHandler();

  return null;
};

export default Logout;
