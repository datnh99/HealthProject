import axios from "axios";
import config from "../config/index";
const API_HEALTH_REPORT = `${config.apiUrl}/api/health-report`;

function add(form) {
  return axios.post(`${API_HEALTH_REPORT}/add`,form);
}

export default {
  add
};
