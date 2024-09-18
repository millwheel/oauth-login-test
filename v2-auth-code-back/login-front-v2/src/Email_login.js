import axios from "axios";
import {toast} from "react-toastify";

export const handleEmailLogin = async (email, password, setIsAuthenticated, setError, navigate) => {
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
