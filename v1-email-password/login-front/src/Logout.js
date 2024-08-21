import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import axios from "axios";

function Logout({ setIsAuthenticated }) {
  const navigate = useNavigate();

  const handleSubmit = async () => {
    try {
      const response = await axios.post("http://localhost:8080/logout", {});

      console.log(response.data);
      toast.success(response.data);

      setIsAuthenticated(false);
      navigate("/");
    } catch (error) {
      console.error(error.response?.data || error.message);
      toast.error(error.response?.data);
    }
  };

  useEffect(() => {
    handleSubmit();
  }, []);

  return null;
}

export default Logout;
