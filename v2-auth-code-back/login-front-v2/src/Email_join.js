import axios from "axios";
import {toast} from "react-toastify";

export const handleEmailJoin = async (email, name, password, passwordConfirm, setError, navigate) => {
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

        console.log(response.data);
        toast.success(response.data);

        navigate("/");
    } catch (error) {
        setError(error.response?.data);
        console.error(error.response?.data);
    }
};
