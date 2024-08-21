export const generateStateToken = () => {
  return [...Array(30)].map(() => Math.random().toString(36)[2]).join("");
};

export const handleGoogleOAuthLogin = (e) => {
  e.preventDefault();
  const googleClientId = process.env.REACT_APP_GOOGLE_CLIENT_ID;
  const googleRedirectUri = "http://localhost:3000/oauth/google/callback";
  if (!googleClientId || !googleRedirectUri) {
    console.error("Google Client ID or Redirect URI is missing!");
    return;
  }
  const googleAuthUrl = `https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=${googleClientId}&redirect_uri=${googleRedirectUri}&scope=profile%20email`;
  window.location.href = googleAuthUrl;
};

export const handleNaverOAuthLogin = (e) => {
  e.preventDefault();
  const naverClientId = process.env.REACT_APP_NAVER_CLIENT_ID;
  const naverRedirectUri = "http://localhost:3000/oauth/naver/callback";

  if (!naverClientId || !naverRedirectUri) {
    console.error("Naver Client ID or Redirect URI is missing!");
    return;
  }
  const stateToken = encodeURIComponent(generateStateToken());
  const naverAuthUrl = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${naverClientId}&redirect_uri=${naverRedirectUri}&state=${stateToken}`;
  window.location.href = naverAuthUrl;
};

export const handelKakaoOAuthLogin = (e) => {
  e.preventDefault();
  const kakaoClientId = process.env.REACT_APP_KAKAO_CLIENT_ID;
  const kakaoRedirectUri = "http://localhost:3000/oauth/kakao/callback";

  if (!kakaoClientId || !kakaoRedirectUri) {
    console.error("Kakaos Client ID or Redirect URI is missing!");
    return;
  }
  const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${kakaoClientId}&redirect_uri=${kakaoRedirectUri}`;
  window.location.href = kakaoAuthUrl;
};
