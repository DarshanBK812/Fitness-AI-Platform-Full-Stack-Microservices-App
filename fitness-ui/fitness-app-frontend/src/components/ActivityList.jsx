import { Card, CardContent, Typography } from "@mui/material";
import Grid from "@mui/material/Grid"; // <-- use Grid (not Grid2)
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getActivities, unwrap } from "../services/api";

const ActivityList = () => {
  const [activities, setActivities] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const res = await getActivities();
        const list = unwrap(res);
        setActivities(Array.isArray(list) ? list : []);
      } catch (e) {
        console.error("Error fetching activities:", e);
      }
    })();
  }, []);

  return (
    <Grid container spacing={2}>
      {activities.map((activity) => (
        <Grid key={activity.id} item xs={12} sm={6} md={4}>
          <Card
            sx={{ cursor: "pointer" }}
            onClick={() =>
              navigate(`/activities/${activity.id}`, { state: { activity } })
            }
          >
            <CardContent>
              <Typography variant="h6">{activity.type}</Typography>
              <Typography variant="body2">
                Duration: {activity.duration}
              </Typography>
              <Typography variant="body2">
                Calories burned: {activity.caloriesBurn}
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
};

export default ActivityList;
