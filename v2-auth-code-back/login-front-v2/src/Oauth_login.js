function handleOAuthLogin(provider) {
    let url = "";
    switch (provider) {
        case "google":
            url = `http://localhost:8080/oauth2/login/google`;
            break;
        case "naver":
            url = `http://localhost:8080/oauth2/login/naver`;
            break;
        case "kakao":
            url = `http://localhost:8080/oauth2/login/kakao`;
            break;
        default:
            throw new Error("Unsupported OAuth provider");
    }

    window.location.href = url;
}

export default handleOAuthLogin;

