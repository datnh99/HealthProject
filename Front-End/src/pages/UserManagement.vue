<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <template slot="header">
          <h4 class="card-title">Student Management</h4>
        </template>

        <div class="container cus-header-class">
          <div class="row justify-content-between">
            <div class="col-2 button-add-custom">
              <base-button type="primary" @click="openAddForm()"
                >Thêm học sinh</base-button
              >
            </div>
            <div class="col-6"></div>
            <div class="col-4">
              <a-pagination
                v-model="current"
                show-quick-jumper
                :default-current="1"
                :total="totals"
                @change="paginate"
              />
            </div>
          </div>
        </div>
        <div class="table-responsive-class text-left">
          <a-spin :spinning="loading">
            <a-icon
              type="loading"
              slot="indicator"
              style="font-size: 24px"
              spin
            />
            <a-table
              :data-source="data"
              :columns="columns"
              :scroll="{ x: 500 }"
              :pagination="false"
            >
              <div
                slot="filterDropdown"
                slot-scope="{
                  setSelectedKeys,
                  selectedKeys,
                  confirm,
                  clearFilters,
                  column,
                }"
                style="padding: 8px"
              >
                <a-input
                  v-ant-ref="(c) => (searchInput = c)"
                  :placeholder="`Search ${column.dataIndex}`"
                  :value="selectedKeys[0]"
                  style="width: 188px; margin-bottom: 8px; display: block;"
                  @change="
                    (e) =>
                      setSelectedKeys(e.target.value ? [e.target.value] : [])
                  "
                  @pressEnter="
                    () => handleSearch(selectedKeys, column.dataIndex)
                  "
                />
                <a-button
                  type="primary"
                  icon="search"
                  size="small"
                  style="width: 90px; margin-right: 8px"
                  @click="() => handleSearch(selectedKeys, column.dataIndex)"
                >
                  Search
                </a-button>
                <a-button
                  size="small"
                  style="width: 90px"
                  @click="() => handleReset(column.dataIndex, clearFilters)"
                >
                  Reset
                </a-button>
              </div>
              <a-icon
                slot="filterIcon"
                slot-scope="filtered"
                type="search"
                :style="{ color: filtered ? '#108ee9' : undefined }"
              />
              <template
                slot="customRender"
                slot-scope="text, record, index, column"
              >
                <span v-if="searchText && searchedColumn === column.dataIndex">
                  <template
                    v-for="(fragment, i) in text
                      .toString()
                      .split(
                        new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i')
                      )"
                  >
                    <mark
                      v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                      :key="i"
                      class="highlight"
                      >{{ fragment }}</mark
                    >
                    <template v-else>{{ fragment }}</template>
                  </template>
                </span>
                <template v-else>
                  {{ text }}
                </template>
              </template>
              <template #action="item">
                <a-dropdown>
                  <a-menu slot="overlay">
                    <a-menu-item key="1" @click="handleEditItemBtnClick(item)">
                      Sửa
                    </a-menu-item>

                    <a-menu-item key="2">
                      <a-popconfirm
                        placement="leftBottom"
                        ok-text="Yes"
                        cancel-text="No"
                        @confirm="deleteSubItemBtnClick(item)"
                      >
                        <template slot="title">
                          <span
                            >Ban có chắc chắn muốn xóa lớp học này không?</span
                          ><br />
                        </template>
                        Xóa
                      </a-popconfirm>
                    </a-menu-item>
                  </a-menu>
                  <a-button> <a-icon type="down" /> </a-button>
                </a-dropdown>
              </template>
            </a-table>
          </a-spin>
        </div>

        <!-- edit modal -->
        <a-modal
          title="Chỉnh sửa lớp học"
          v-model="showModal.edit"
          :maskClosable="false"
          :destroyOnClose="true"
          :closable="false"
        >
          <template slot="footer">
            <a-button
              key="submit"
              type="primary"
              :loading="loadingModal"
              @click="saveEditClass"
            >
              Cập nhật
            </a-button>
            <a-button
              key="cancel"
              type="secondary"
              :disabled="loadingModal"
              @click="closeEditForm()"
            >
              Hủy
            </a-button>
          </template>
          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Tên lớp
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-textarea
                v-model="editForm.className"
                :auto-size="{ minRows: 1, maxRows: 5 }"
                :min="0"
                class="full-width--i"
              />
              <span v-if="errors.className" class="red">
                {{ errors.className }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8">
              Giáo viên chủ nhiệm
            </a-col>
            <a-col :span="16">
              <a-select
                v-model="editForm.teacherID"
                class="filter-select"
                style="width: 100%"
                @search="fetchTeacher"
              >
                <a-select-option
                  v-for="item in studentList"
                  :key="item.id"
                  :value="item.id"
                >
                  {{ item.fullName }}
                </a-select-option>
              </a-select>
            </a-col>
          </a-row>
        </a-modal>

        <!-- Add modal -->
        <a-modal
          title="Thêm mới học sinh"
          v-model="showModal.add"
          :maskClosable="false"
          :destroyOnClose="true"
          :closable="false"
        >
          <template slot="footer">
            <a-button
              key="submit"
              type="primary"
              :loading="loadingModal"
              @click="addNewClass"
            >
              Thêm
            </a-button>
            <a-button
              key="cancel"
              type="secondary"
              :disabled="loadingModal"
              @click="closeModal()"
            >
              Hủy
            </a-button>
          </template>
          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Tên lớp
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-textarea
                v-model="addForm.className"
                :auto-size="{ minRows: 1, maxRows: 5 }"
                :min="0"
                class="full-width--i"
              />
              <span v-if="errors.className" class="red">
                {{ errors.className }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8">
              Giáo viên chủ nhiệm
            </a-col>
            <a-col :span="16">
              <a-select
                v-model="addForm.teacherID"
                class="filter-select"
                style="width: 100%"
                @search="fetchTeacher"
              >
                <a-select-option
                  v-for="item in studentList"
                  :key="item.id"
                  :value="item.id"
                >
                  {{ item.fullName }}
                </a-select-option>
              </a-select>
            </a-col>
          </a-row>
        </a-modal>
      </div>
    </div>
  </div>
</template>
<script>
import UserRepository from "../api/user.js";

const defaultModalState = {
  add: false,
  edit: false
};

const defaultForm = {
  id: undefined,
  fullName: "",
  username: "",
  dob: "2000-01-01",
  gender: 1,
  phoneNumber: "",
  parentPhoneNumber: "",
  provinceId: undefined,
  provinceName: "",
  districtId: undefined,
  districtName: "",
  wardId: undefined,
  wardName: "",
  addressDetail: ""
};

const requiredError = "Không được để trống thông tin này!";

const defaultInputErrors = {
  fullName: "",
  username: "",
  gender: "",
  phoneNumber: "",
  parentPhoneNumber: "",

};

export default {
  data() {
    return {
      data: [],
      studentList: [],
      current: 1,
      totals: 0,
      loading: false,
      loadingModal: false,
      formDataSearch: {
        fullName: "",
        gender: "",
        wardName: "",
        districtName: "",
        provinceName: ""
      },
      showModal: { ...defaultModalState },
      editForm: { ...defaultForm },
      addForm: { ...defaultForm },
      errors: { ...defaultInputErrors },
      selectedItem: null,
      searchText: "",
      searchInput: null,
      searchedColumn: "",
      columns: [
        {
          title: "Stt",
          dataIndex: "index",
          width: 50,
          key: "index",
        },
        {
          title: "Tên học sinh",
          dataIndex: "fullName",
          width: 100,
          key: "fullName",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.fullName
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              }, 0);
            }
          },
        },
        {
          title: "Giới tính",
          dataIndex: "gender",
          width: 100,
          key: "gender",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.gender
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              }, 0);
            }
          },
        },
        {
          title: "Ngày sinh",
          dataIndex: "dob",
          width: 100,
          key: "dob",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.dob
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              }, 0);
            }
          },
        },
        {
          title: "Tài khoản",
          dataIndex: "username",
          width: 100,
          key: "username",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.username
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              });
            }
          },
        },
        {
          title: "Địa chỉ",
          dataIndex: "addressDetail",
          width: 100,
          key: "addressDetail",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.addressDetail
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              });
            }
          },
        },
        {
          title: "Tỉnh",
          dataIndex: "provinceName",
          width: 100,
          key: "provinceName",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.provinceName
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              });
            }
          },
        },
        {
          title: "Huyện",
          dataIndex: "districtName",
          width: 100,
          key: "districtName",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.districtName
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              });
            }
          },
        },
        {
          title: "Xã",
          dataIndex: "wardName",
          width: 100,
          key: "wardName",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.wardName
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              });
            }
          },
        },
        {
          title: "Số điện thoại",
          dataIndex: "phoneNumber",
          width: 100,
          key: "phoneNumber",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.phoneNumber
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              });
            }
          },
        },
        {
          title: "Sđt phụ huynh",
          dataIndex: "parentPhoneNumber",
          width: 100,
          key: "parentPhoneNumber",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.parentPhoneNumber
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              });
            }
          },
        },
        {
          title: "Tùy chọn",
          key: "action",
          fixed: "right",
          width: 100,
          scopedSlots: { customRender: "action" },
        },
      ],
    };
  },
  created() {
    this.searchStudent();
    this.fetchTeacher("");
  },
  methods: {
    paginate(current = 1) {
      this.loading = true;
      this.current = current;
      UserRepository.searchStudent(this.formDataSearch, this.current).then(
        (res) => {
          this.data = res.data.data.items;
          this.totals = res.data.data.total;
          this.loading = false;
        }
      );
    },
    searchStudent() {
      this.loading = true;
      UserRepository.searchStudent(this.formDataSearch, 1).then((res) => {
        this.data = res.data.data.items;
        this.totals = res.data.data.total;
        this.current = 1;
        this.loading = false;
      });
    },
    handleSearch(selectedKeys, dataIndex) {
      this.searchText = selectedKeys[0];
      this.searchedColumn = dataIndex;
      if (dataIndex === "className") {
        this.formDataSearch.className = selectedKeys[0];
      } else if (dataIndex === "teacherName") {
        this.formDataSearch.teacherName = selectedKeys[0];
      }
      this.searchStudent();
    },

    handleReset(dataIndex, clearFilters) {
      if (dataIndex === "fullName") {
        this.formDataSearch.fullName = "";
      } else if (dataIndex === "username") {
        this.formDataSearch.username = "";
      }
      clearFilters();
      this.searchStudent();
      this.searchText = "";
    },
    async handleEditItemBtnClick(item) {
      this.fetchTeacher("");
      this.selectedItem = item;
      // this.editForm.id = item.id;
      // this.editForm.fullName = item.fullName;
      // this.editForm.username = item.username;
      this.showModal = {
        edit: true,
      };
    },
    closeEditForm() {
      this.selectedItem = null;
      this.closeModal();
    },
    closeModal() {
      this.showModal = { ...defaultModalState };
      this.errors = { ...defaultInputErrors };
      this.editForm = { ...defaultForm };
      this.addForm = { ...defaultForm };
    },
    saveEditClass() {
      this.loadingModal = true;
      const validation = this.validate();
      if (!validation) {
        this.loadingModal = false;
        return;
      }
      var formEditData = {
        id: this.editForm.id,
        className: this.editForm.className,
        teacherID: this.editForm.teacherID,
      };
      UserRepository.editClass(formEditData)
        .then((response) => {
          if (response.data.success === true) {
            this.$notification.success({
              message: "Chỉnh sửa thành công!",
            });
            this.paginate();
            this.closeModal();
            this.loadingModal = false;
          } else {
            this.$notification.error({
              message: "Chỉnh sửa thất bại!",
            });
            this.loadingModal = false;
          }
        })
        .catch(() => {
          this.$notification.error({
            message: "Chỉnh sửa thất bại!",
          });
          this.loadingModal = false;
        });
    },
    openAddForm() {
      this.showModal = {
        add: true
      };
    },
    addNewClass() {
      this.loadingModal = true;
      const validation = this.validateAddNew();
      if (!validation) {
        this.loadingModal = false;
        return;
      }
      var formEditData = {
        className: this.addForm.className,
        teacherId: this.addForm.teacherID,
      };
      UserRepository.addNewClass(formEditData)
        .then((response) => {
          if (response.data.success === true) {
            this.$notification.success({
              message: "Thêm mới thành công!",
            });
            this.paginate();
            this.closeModal();
            this.loadingModal = false;
          } else {
            this.$notification.error({
              message: "Tên lớp học đã tồn tại!",
            });
            this.loadingModal = false;
          }
        })
        .catch(() => {
          this.$notification.error({
            message: "Tên lớp học đã tồn tại!",
          });
          this.loadingModal = false;
        });
    },
    validate() {
      let isValid = true;
      if (this.editForm.className == "" || this.editForm.className == null) {
        this.errors.className = requiredError;
        isValid = false;
      }
      return isValid;
    },
    validateAddNew() {
      let isValid = true;
      if (this.addForm.className == "" || this.addForm.className == null) {
        this.errors.className = requiredError;
        isValid = false;
      }
      return isValid;
    },
    fetchTeacher(teacherName) {
      UserRepository.searchTeacherByName(teacherName, 1).then((res) => {
        this.studentList = res.data.data.items;
      });
    },
    deleteSubItemBtnClick(item) {
      this.loading = true;
      this.selectedItem = item;
      UserRepository.deleteClass(this.selectedItem.id)
        .then((res) => {
          if (res.data.success === true) {
            this.$notification.success({
              message: "Xóa lớp học thành công!",
            });
            this.paginate();
          } else {
            this.$notification.error({
              message: "Xóa lớp học thất bại!",
            });
            this.loading = false;
          }
        })
        .catch((e) => {
          this.$notification.error({
            message: e.response.data.message,
          });
          this.loading = false;
        });
    },
  },
};
</script>
<style scoped>
.highlight {
  background-color: rgb(255, 192, 105);
  padding: 0px;
}

.gutter-example {
  padding-bottom: 40px;
}

/* .pagnigation-custom {
  float: right;
} */
.button-add-custom {
  float: left;
}
.table-responsive-class {
  padding-top: 10px;
}
.ant-pagination {
  float: right;
}

.cus-header-class {
  margin: none !important;
  padding: none !important;
}
</style>
