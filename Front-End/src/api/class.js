import axios from "axios";
import config from "../config/index";
const API_CLASS = `${config.apiUrl}/api/class`;
const pageSize = 10;

function getALlClass() {
  return axios.get(`${API_CLASS}/getAll`);
}

function searchClass(classFormSearch, pageIndex) {
  return axios.post(
    `${API_CLASS}/search-class?pageIndex=${pageIndex}&pageSize=${pageSize}`,
    classFormSearch
  );
}

export default {
  getALlClass,
  searchClass,
};
