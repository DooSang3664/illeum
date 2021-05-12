import axios from 'axios';

import { ai } from './index';

function faceAI(userData) {
  return ai.post('/eval', userData, {
    headers: {
      'content-type': 'application/json',
    },
  });
}
export { faceAI };
