
import handleOAuthLogin from "./Oauth_login";

function Login({ setIsAuthenticated, setEmail, setName}) {

  return (
    <div className="frame">
      <div className="container">
        <h1>Login format</h1>
        <div className="oauth2_container">
          <button onClick={() => handleOAuthLogin("google")}
                  className="oauth2_button">
            Login with Google
          </button>
          <button onClick={() => handleOAuthLogin("naver")}
                  className="oauth2_button">
            Login with Naver
          </button>
          <button onClick={() => handleOAuthLogin("kakao")} className="oauth2_button">
            Login with Kakao
          </button>
        </div>
      </div>
    </div>
  );
}

export default Login;
