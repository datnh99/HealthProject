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

function searchTeacherFreeByName(teacherName) {
  return axios.get(
    `${API_USER}/search-teacher-free-by-name?teacherName=${teacherName}`
  );
}

function searchUser(formSearch, pageIndex) {
  return axios.post(
    `${API_USER}/search-user-to-management?pageIndex=${pageIndex}&pageSize=${pageSize}`,
    formSearch
  );
}
function getUserByUsername(username) {
  return axios.get(`${API_USER}/get-user-by-username?username=${username}`);
}
function addNewUser(formAddNew) {
  return axios.post(`${API_USER}/add-new-user`, formAddNew);
}
function addNewStudent(formAddNew) {
  return axios.post(`${API_USER}/add-new-student`, formAddNew);
}

export default {
  getAllUser,
  searchTeacherByName,
  searchTeacherFreeByName,
  searchUser,
  addNewUser,
  getUserByUsername,
  addNewStudent
};
