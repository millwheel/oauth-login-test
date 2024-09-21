import {useEffect, useState} from "react";
import axios from "axios";

function App() {
    const [user, setUser] = useState(null);

    useEffect(() => {
        axios.get("http://localhost:8080/user", { withCredentials: true })
            .then(response => {
                setUser(response.data);
            })
            .catch(error => {
                console.log("User not authenticated", error);
            });
    }, []);

    const handleGoogleLogin = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/google";
    };

    return (
        <div className="App">
            <header className="App-header">
                {user ? (
                    <div>
                        <h1>Welcome, {user.name}</h1>
                        <p>Email: {user.email}</p>
                    </div>
                ) : (
                    <div>
                        <h1>Login with Google</h1>
                        <button onClick={handleGoogleLogin}>
                            Login with Google
                        </button>
                    </div>
                )}
            </header>
        </div>
    );
}

export default App;
