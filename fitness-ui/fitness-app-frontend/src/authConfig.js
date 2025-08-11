const authConfig  = {
  clientId: 'darshanbk812',
  authorizationEndpoint: 'http://127.0.0.1:8180/realms/darshanbk812/protocol/openid-connect/auth',
  tokenEndpoint: 'http://127.0.0.1:8180/realms/darshanbk812/protocol/openid-connect/token',
  redirectUri: 'http://localhost:5173',
  scope: 'openid email profile',
  onRefreshTokenExpire: (event) => event.logIn(),
}

export default authConfig;