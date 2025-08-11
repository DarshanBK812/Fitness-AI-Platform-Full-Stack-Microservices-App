import React, { useState } from "react";
import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import { addActivity } from "../services/api";

const ActivityForm = ({ onActivitiesAdded }) => {
  const [activity, setActivity] = useState({
    type: "RUNNING",
    duration: "",
    caloriesBurn: "",           // <- use backend field name
    additionalMetrics: {},
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      ...activity,
      duration: Number(activity.duration || 0),
      caloriesBurn: Number(activity.caloriesBurn || 0),
    };

    try {
      await addActivity(payload);
      onActivitiesAdded?.();
      setActivity({ type: "RUNNING", duration: "", caloriesBurn: "", additionalMetrics: {} });
    } catch (err) {
      console.error("Add activity failed:", err);
    }
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mb: 4 }}>
      <FormControl fullWidth sx={{ mb: 2 }}>
        <InputLabel id="activity-type-label">Activity Type</InputLabel>
        <Select
          labelId="activity-type-label"
          label="Activity Type"
          value={activity.type}
          onChange={(e) => setActivity({ ...activity, type: e.target.value })}
        >
          <MenuItem value="RUNNING">Running</MenuItem>
          <MenuItem value="CYCLING">Cycling</MenuItem>
          <MenuItem value="SWIMMING">Swimming</MenuItem>
        </Select>
      </FormControl>

      <TextField
        fullWidth
        label="Duration (in minutes)"
        type="number"
        sx={{ mb: 2 }}
        value={activity.duration}
        onChange={(e) => setActivity({ ...activity, duration: e.target.value })}
      />

      <TextField
        fullWidth
        label="Calories Burned"
        type="number"
        sx={{ mb: 2 }}
        value={activity.caloriesBurn}
        onChange={(e) => setActivity({ ...activity, caloriesBurn: e.target.value })}
      />

      <Button type="submit" variant="contained">Add Activity</Button>
    </Box>
  );
};

export default ActivityForm;
