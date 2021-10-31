import axios from "axios";
import config from "../config/index";
const API_USER = `${config.apiUrl}/api/user`;
const pageSize = 10;

function getAllUser() {
  return axios.get(`${API_USER}/getAll`);
}

function searchTeacherByName(teacherName, pageIndex) {
  return axios.get(
    `${API_USER}/search-teacher-by-name?teacherName=${teacherName}&pageIndex=${pageIndex}&pageSize=${pageSize}`
  );
}

function searchUserDetail(formSearch, pageIndex) {
  return axios.post(
    `${API_USER}/search-user-to-management?pageIndex=${pageIndex}&pageSize=${pageSize}`,
    formSearch
  );
}
function getUserByUsername(username) {
  return axios.get(
    `${API_USER}/get-user-by-username?username=${username}`
  );
}
function addNewUser(formAddNew) {
  return axios.post(`${API_USER}/add-new-user`, formAddNew);
}

export default {
  getAllUser,
  searchTeacherByName,
  searchUserDetail,
  addNewUser,
getUserByUsername
};
