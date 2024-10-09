

function OAuthLogin () {
    const handleLogin = () => {
        window.location.href = "http://localhost:8080/login";
    };

    return (
        <button onClick={handleLogin} className="oauth2_button">
            Login with OAuth2
        </button>
    );
}


export default OAuthLogin;
