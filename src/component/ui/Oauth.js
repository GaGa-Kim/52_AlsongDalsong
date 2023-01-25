const REST_API_KEY = "d32d7f81a133c94dd7c13808d17e4166";
const REDIRECT_URI =  "http://localhost:3000/auth/select-page";

export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;