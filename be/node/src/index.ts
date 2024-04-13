import express from 'express';

import questions from './questions.json';
import utils from './utils';

const PORT = 8080;
const app = express();

// Randomize the question order
utils.shuffle(questions);

// Configure a single endpoint for returning a hardcoded leaderboard.
// TODO: Fix this so that the data is not hardcoded and is in the right
// shape that the frontend expects, or maybe not worry about leaderboards at all!
app.get('/leaderboard', (req, res) => {
  const leaderboard = [
    { name: 'Alice', score: 89.79 },
    { name: 'John', score: 65.35 },
    { name: 'James', score: 15.92 },
    { name: 'Janice', score: 3.14 },
  ];
  res.json(leaderboard);
});

// Start up the server
app.listen(PORT, () => {
  console.log(`Captrivia listening on port ${PORT}`);
});
