import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux";
import { Store } from "./store/Store";
import App from "./App";
import { AuthProvider } from "react-oauth2-code-pkce";
import authConfig from "./authConfig";

ReactDOM.createRoot(document.getElementById("root")).render(
  <AuthProvider authConfig={authConfig} loadingComponent={<div>Loading...</div>}>
    <Provider store={Store}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </Provider>
  </AuthProvider>
);
