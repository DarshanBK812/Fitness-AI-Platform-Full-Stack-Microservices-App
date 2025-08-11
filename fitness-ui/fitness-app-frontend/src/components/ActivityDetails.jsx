import React, { useEffect, useState } from "react";
import { useParams, useLocation } from "react-router-dom";
import { Box, Card, CardContent, Divider, Typography, CircularProgress, Stack } from "@mui/material";
import { getActivityRecommendation, unwrap } from "../services/api";

const POLL_MS = 2000;            // poll every 2s
const GIVE_UP_AFTER_MS = 120000; // optional: stop after 2min (remove if you want infinite)

const ActivityDetails = () => {
  const { id: activityId } = useParams();
  const { state } = useLocation();
  const preloaded = state?.activity || null;

  const [activity] = useState(preloaded);
  const [rec, setRec] = useState(null);
  const [loading, setLoading] = useState(true);
  const [errorMsg, setErrorMsg] = useState("");
const MAX_ATTEMPTS = 20;       // ~stops after a few minutes
const BASE_DELAY_MS = 1000;    // start at 1s, then 2s, 4s, ...

useEffect(() => {
  let timer, cancelled = false, attempts = 0;

  async function fetchRec() {
    try {
      const r = await getActivityRecommendation(activityId);
      const d = unwrap(r);
      const one = Array.isArray(d) ? d[0] : d;
      if (!cancelled) {
        setRec(one || null);
        setLoading(false);
      }
    } catch (err) {
      const status = err?.response?.status;
      const transient = status === 404 || status === 500;  // treat as “not ready yet”
      if (!cancelled && transient && attempts < MAX_ATTEMPTS) {
        const delay = Math.min(10000, BASE_DELAY_MS * 2 ** attempts++); // cap at 10s
        timer = setTimeout(fetchRec, delay);
      } else if (!cancelled) {
        setErrorMsg("Couldn't load recommendation. Please try again.");
        setLoading(false);
      }
    }
  }

  setLoading(true);
  setRec(null);
  setErrorMsg("");
  fetchRec();

  return () => { cancelled = true; if (timer) clearTimeout(timer); };
}, [activityId]);


  // BLOCK THE WHOLE PAGE until rec is ready (or we give up)
  if (loading) {
    return (
      <Box sx={{ height: "60vh", display: "grid", placeItems: "center" }}>
        <Stack direction="row" spacing={2} alignItems="center">
          <CircularProgress />
          <Typography>Generating recommendation for this activity…</Typography>
        </Stack>
      </Box>
    );
  }

  if (!rec && errorMsg) {
    return (
      <Box sx={{ height: "60vh", display: "grid", placeItems: "center" }}>
        <Typography color="error">{errorMsg}</Typography>
      </Box>
    );
  }

  // When rec is ready, render everything
  const displayType     = activity?.type ?? rec?.activityType ?? "-";
  const displayDuration = activity?.duration ?? 0;
  const displayCalories = activity?.caloriesBurn ?? 0;
  const displayDate     = activity?.createdAt || rec?.createdAt;
  const tips = rec?.suggestions ?? rec?.saftey ?? [];

  return (
    <Box sx={{ maxWidth: 800, mx: "auto", p: 2 }}>
      <Card sx={{ mb: 2 }}>
        <CardContent>
          <Typography variant="h5" gutterBottom>Activity Details</Typography>
          <Typography>Type: {displayType}</Typography>
          <Typography>Duration: {displayDuration} minutes</Typography>
          <Typography>Calories Burned: {displayCalories}</Typography>
          <Typography>Date: {displayDate ? new Date(displayDate).toLocaleString() : "-"}</Typography>
        </CardContent>
      </Card>

      <Card>
        <CardContent>
          <Typography variant="h5" gutterBottom>AI Recommendation</Typography>

          {rec?.recommendation && (
            <>
              <Typography variant="h6">Analysis</Typography>
              <Typography paragraph>{rec.recommendation}</Typography>
              <Divider sx={{ my: 2 }} />
            </>
          )}

          {Array.isArray(rec?.improvements) && rec.improvements.length > 0 && (
            <>
              <Typography variant="h6">Improvements</Typography>
              {rec.improvements.map((imp, i) => (
                <Typography key={i} paragraph>• {imp}</Typography>
              ))}
              <Divider sx={{ my: 2 }} />
            </>
          )}

          {Array.isArray(tips) && tips.length > 0 && (
            <>
              <Typography variant="h6">Suggestions</Typography>
              {tips.map((s, i) => (
                <Typography key={i} paragraph>• {s}</Typography>
              ))}
            </>
          )}
        </CardContent>
      </Card>
    </Box>
  );
};

export default ActivityDetails;
