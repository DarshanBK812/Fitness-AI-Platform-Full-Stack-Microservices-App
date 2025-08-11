import React, { useContext, useEffect } from "react";
import { Box, Button, Typography } from "@mui/material";
import { useDispatch } from "react-redux";
import { Routes, Route, Navigate } from "react-router-dom";
import { setCredential } from "./store/AuthSlice";
import { AuthContext } from "react-oauth2-code-pkce";
import ActivityList from "./components/ActivityList";
import ActivityForm from "./components/ActivityForm";
import ActivityDetails from "./components/ActivityDetails";

const LoginPage = ({ onLogin }) => (
  <Box sx={{ p: 4, textAlign: "center", border: "1px dashed grey", borderRadius: 2, maxWidth: 600, mx: "auto", mt: 8 }}>
    <Typography variant="h4" gutterBottom>Welcome to the Fitness Recommendation App</Typography>
    <Typography variant="body1" sx={{ mb: 3 }}>Track your workouts, get AI recommendations, and improve performance.</Typography>
    <Button variant="contained" size="large" sx={{ backgroundColor: "#dc004e", "&:hover": { backgroundColor: "#a30036" } }} onClick={() => onLogin()}>
      LOGIN
    </Button>
  </Box>
);

const ActivitiesPage = () => {
  const { logOut } = useContext(AuthContext);
  return (
    <Box component="section" sx={{ p: 2 }}>
      <Box sx={{ display: "flex", justifyContent: "flex-end", mb: 2 }}>
        <Button variant="outlined" color="error" onClick={() => logOut()}>LOGOUT</Button>
      </Box>
      <Box sx={{ p: 2, border: "1px dashed grey" }}>
        {/* Exactly ONE ActivityForm here */}
        <ActivityForm onActivitiesAdded={() => window.location.reload()} />
        <ActivityList />
      </Box>
    </Box>
  );
};

const App = () => {
  const { user, token, tokenData, logIn } = useContext(AuthContext);
  const dispatch = useDispatch();

  useEffect(() => {
    if (token) dispatch(setCredential({ user, token, tokenData }));
  }, [token, tokenData, user, dispatch]);

  return (
    <Routes>
      <Route path="/" element={token ? <Navigate to="/activities" replace /> : <LoginPage onLogin={logIn} />} />
      <Route path="/activities" element={token ? <ActivitiesPage /> : <Navigate to="/" replace />} />
      <Route path="/activities/:id" element={token ? <ActivityDetails /> : <Navigate to="/" replace />} />
      <Route path="*" element={<Navigate to={token ? "/activities" : "/"} replace />} />
    </Routes>
  );
};

export default App;
