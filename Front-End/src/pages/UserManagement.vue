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
                      v-if="checkContainSearchKey(fragment, searchText)"
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
              <template slot="gender" slot-scope="text, record, index, column">
                <span v-if="searchText && searchedColumn === column.dataIndex">
                  <template
                    v-for="(fragment, i) in text
                      .toString()
                      .split(
                        new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i')
                      )"
                  >
                    <mark
                      v-if="'nam' === searchText.toLowerCase()"
                      :key="i"
                      class="highlight"
                    >
                      Nam
                    </mark>
                    <!-- <template v-else>
                      Nam
                    </template> -->
                    <mark
                      v-if="
                        'nu' === removeAccents(searchText).toLowerCase() &&
                          searchText.toLowerCase() !== 'nam'
                      "
                      :key="i"
                      class="highlight"
                    >
                      Nữ
                    </mark>
                    <!-- <template v-else-if="searchText.toLowerCase() !== 'nam'">
                      Nữ
                    </template> -->
                  </template>
                </span>
                <template v-else>
                  <span v-if="text">
                    Nam
                  </span>
                  <span v-else>
                    Nữ
                  </span>
                </template>
              </template>
              <template #dobCustom="item">
                <span>{{ generateTime(item.dob) }}</span>
              </template>
            </a-table>
          </a-spin>
        </div>

        <!-- edit modal -->
        <a-modal
          title="Chỉnh sửa thông tin người dùng"
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
              @click="saveEditUser"
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
              >Họ và Tên
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-input v-model="editForm.fullName" />
              <span v-if="errors.fullName" class="red">
                {{ errors.fullName }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Giới tính
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-radio-group
                v-model="editForm.gender"
                name="radioGroup"
                :default-value="1"
              >
                <a-radio :value="1">
                  Male
                </a-radio>
                <a-radio :value="0">
                  Female
                </a-radio>
              </a-radio-group>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Ngày sinh
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-date-picker v-model="editForm.dob">
                <template slot="renderExtraFooter"> </template>
              </a-date-picker>
              <span v-if="errors.dob" class="red">
                {{ errors.dob }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Số điện thoại
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-input v-model="editForm.phoneNumber" />
              <span v-if="errors.phoneNumber" class="red">
                {{ errors.phoneNumber }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Sđt người thân
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-input v-model="editForm.parentPhoneNumber" />
              <span v-if="errors.parentPhoneNumber" class="red">
                {{ errors.parentPhoneNumber }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8">
              Tỉnh
            </a-col>
            <a-col :span="16">
              <a-select
                v-model="editForm.provinceCode"
                class="filter-select"
                style="width: 100%"
                @search="fetchProvince"
                @change="fetchDistrict(item.code)"
              >
                <a-select-option
                  v-for="item in provinceList"
                  :key="item.code"
                  :value="item.code"
                >
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8">
              Huyện
            </a-col>
            <a-col :span="16">
              <a-select
                v-model="editForm.districtCode"
                class="filter-select"
                :disabled="districtList.length < 1"
                style="width: 100%"
                @search="fetchDistrictAfter"
                @change="fetchWard"
              >
                <a-select-option
                  v-for="item in districtList"
                  :key="item.districtCode"
                  :value="item.districtCode"
                >
                  {{ item.districtName }}
                </a-select-option>
              </a-select>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8">
              Xã
            </a-col>
            <a-col :span="16">
              <a-select
                v-model="editForm.wardCode"
                :disabled="wardList.length < 1"
                class="filter-select"
                style="width: 100%"
                @search="fetchWardAfter"
              >
                <a-select-option
                  v-for="item in wardList"
                  :key="item.wardCode"
                  :value="item.wardCode"
                >
                  {{ item.wardName }}
                </a-select-option>
              </a-select>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Địa chỉ cụ thể
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-textarea
                v-model="editForm.addressDetail"
                :auto-size="{ minRows: 1, maxRows: 5 }"
                :min="0"
                class="full-width--i"
              />
              <span v-if="errors.addressDetail" class="red">
                {{ errors.addressDetail }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8">
              Lớp
            </a-col>
            <a-col :span="16">
              <a-select
                v-model="editForm.classID"
                class="filter-select"
                style="width: 100%"
                @search="fetchClass"
              >
                <a-select-option
                  v-for="item in classList"
                  :key="item.id"
                  :value="item.id"
                >
                  {{ item.className }}
                </a-select-option>
              </a-select>
            </a-col>
          </a-row>
        </a-modal>

        <!-- Add modal -->
        <a-modal
          title="Thêm mới người dùng"
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
              @click="addNewUser"
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
              >Họ và Tên
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-input v-model="addForm.fullName" />
              <span v-if="errors.fullName" class="red">
                {{ errors.fullName }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Giới tính
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-radio-group
                v-model="addForm.gender"
                name="radioGroup"
                :default-value="1"
              >
                <a-radio :value="1">
                  Male
                </a-radio>
                <a-radio :value="0">
                  Female
                </a-radio>
              </a-radio-group>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Ngày sinh
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-date-picker v-model="addForm.dob">
                <template slot="renderExtraFooter"> </template>
              </a-date-picker>
              <span v-if="errors.dob" class="red">
                {{ errors.dob }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Số điện thoại
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-input v-model="addForm.phoneNumber" />
              <span v-if="errors.phoneNumber" class="red">
                {{ errors.phoneNumber }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Sđt người thân
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-input v-model="addForm.parentPhoneNumber" />
              <span v-if="errors.parentPhoneNumber" class="red">
                {{ errors.parentPhoneNumber }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8">
              Tỉnh
            </a-col>
            <a-col :span="16">
              <a-select
                v-model="addForm.provinceCode"
                class="filter-select"
                style="width: 100%"
                @search="fetchProvince"
                @change="fetchDistrict"
              >
                <a-select-option
                  v-for="item in provinceList"
                  :key="item.code"
                  :value="item.code"
                >
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8">
              Huyện
            </a-col>
            <a-col :span="16">
              <a-select
                v-model="addForm.districtCode"
                :disabled="districtList.length < 1"
                class="filter-select"
                style="width: 100%"
                @search="fetchDistrictAfter"
                @change="fetchWard"
              >
                <a-select-option
                  v-for="item in districtList"
                  :key="item.districtCode"
                  :value="item.districtCode"
                >
                  {{ item.districtName }}
                </a-select-option>
              </a-select>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8">
              Xã
            </a-col>
            <a-col :span="16">
              <a-select
                v-model="addForm.wardCode"
                :disabled="wardList.length < 1"
                class="filter-select"
                style="width: 100%"
                @search="fetchWardAfter"
              >
                <a-select-option
                  v-for="item in wardList"
                  :key="item.wardCode"
                  :value="item.wardCode"
                >
                  {{ item.wardName }}
                </a-select-option>
              </a-select>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Địa chỉ cụ thể
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-textarea
                v-model="addForm.addressDetail"
                :auto-size="{ minRows: 1, maxRows: 5 }"
                :min="0"
                class="full-width--i"
              />
              <span v-if="errors.addressDetail" class="red">
                {{ errors.addressDetail }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8">
              Lớp
            </a-col>
            <a-col :span="16">
              <a-select
                v-model="addForm.classID"
                class="filter-select"
                style="width: 100%"
                @search="fetchClass"
              >
                <a-select-option
                  v-for="item in classList"
                  :key="item.id"
                  :value="item.id"
                >
                  {{ item.className }}
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
import ClassRepository from "../api/class.js";
import LocationRepository from "../api/location.js";
import moment from "moment";

const defaultModalState = {
  add: false,
  edit: false,
};

const defaultForm = {
  id: undefined,
  fullName: "",
  dob: "2000-01-01",
  gender: 1,
  phoneNumber: "",
  parentPhoneNumber: "",
  provinceCode: undefined,
  districtCode: undefined,
  wardCode: undefined,
  addressDetail: "",
  classID: undefined,
};

const requiredError = "Không được để trống thông tin này!";

const defaultInputErrors = {
  fullName: "",
  gender: "",
  phoneNumber: "",
  parentPhoneNumber: "",
};

export default {
  data() {
    return {
      data: [],
      classList: [],
      provinceList: [],
      districtList: [],
      wardList: [],
      studentList: [],
      current: 1,
      totals: 0,
      loading: false,
      loadingModal: false,
      formDataSearch: {
        fullName: "",
        genderSearch: "",
        wardName: "",
        districtName: "",
        provinceName: "",
      },
      provinceCodeSearch: undefined,
      districtCodeSearch: undefined,
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
          title: "Họ và Tên",
          dataIndex: "fullName",
          width: 160,
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
            customRender: "gender",
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
          width: 100,
          key: "dob",
          scopedSlots: {
            customRender: "dobCustom",
          },
        },
        {
          title: "Tài khoản",
          dataIndex: "userName",
          width: 100,
          key: "userName",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.userName
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
          title: "Địa chỉ cụ thể",
          dataIndex: "addressDetail",
          width: 100,
          key: "addressDetail",
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
          title: "Số điện thoại",
          dataIndex: "phoneNumber",
          width: 100,
          key: "phoneNumber",
        },
        {
          title: "Sđt nguời thân",
          dataIndex: "parentPhoneNumber",
          width: 100,
          key: "parentPhoneNumber",
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
    this.searchUser();
    this.fetchProvince("");
    this.fetchClass("");
  },
  methods: {
    checkContainSearchKey(fragment, searchText) {
      if (
        this.removeAccents(fragment).toLowerCase() ===
        this.removeAccents(searchText).toLowerCase()
      ) {
        return true;
      }
      return false;
    },
    removeAccents(str) {
      var AccentsMap = [
        "aàảãáạăằẳẵắặâầẩẫấậ",
        "AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬ",
        "dđ",
        "DĐ",
        "eèẻẽéẹêềểễếệ",
        "EÈẺẼÉẸÊỀỂỄẾỆ",
        "iìỉĩíị",
        "IÌỈĨÍỊ",
        "oòỏõóọôồổỗốộơờởỡớợ",
        "OÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢ",
        "uùủũúụưừửữứự",
        "UÙỦŨÚỤƯỪỬỮỨỰ",
        "yỳỷỹýỵ",
        "YỲỶỸÝỴ",
      ];
      for (var i = 0; i < AccentsMap.length; i++) {
        var re = new RegExp("[" + AccentsMap[i].substr(1) + "]", "g");
        var char = AccentsMap[i][0];
        str = str.replace(re, char);
      }
      return str;
    },
    generateTime(dateNumber) {
      const date = new Date(dateNumber);
      const m = moment(date);
      return m.isValid() ? m.format("DD-MM-YYYY") : "";
    },
    paginate(current = 1) {
      this.loading = true;
      this.current = current;
      UserRepository.searchUser(this.formDataSearch, this.current).then(res => {
        this.data = res.data.data.items;
        this.totals = res.data.data.total;
        this.loading = false;
      });
    },
    searchUser() {
      this.loading = true;
      UserRepository.searchUser(this.formDataSearch, 1).then((res) => {
        this.data = res.data.data.items;
        this.totals = res.data.data.total;
        this.current = 1;
        this.loading = false;
      });
    },
    handleSearch(selectedKeys, dataIndex) {
      this.searchText = selectedKeys[0];
      this.searchedColumn = dataIndex;
      console.log("dataIndex", dataIndex);
      if (dataIndex === "fullName") {
        this.formDataSearch.fullName = selectedKeys[0];
      } else if (dataIndex === "gender") {
        this.formDataSearch.genderSearch = selectedKeys[0];
      } else if (dataIndex === "wardName") {
        this.formDataSearch.wardName = selectedKeys[0];
      } else if (dataIndex === "districtName") {
        this.formDataSearch.districtName = selectedKeys[0];
      } else if (dataIndex === "provinceName") {
        this.formDataSearch.provinceName = selectedKeys[0];
      }
      this.searchUser();
    },

    handleReset(dataIndex, clearFilters) {
      if (dataIndex === "fullName") {
        this.formDataSearch.fullName = "";
      } else if (dataIndex === "gender") {
        this.formDataSearch.genderSearch = "";
      } else if (dataIndex === "wardName") {
        this.formDataSearch.wardName = "";
      } else if (dataIndex === "districtName") {
        this.formDataSearch.districtName = "";
      } else if (dataIndex === "provinceName") {
        this.formDataSearch.provinceName = "";
      }
      clearFilters();
      this.searchUser();
      this.searchText = "";
    },
    async handleEditItemBtnClick(item) {
      this.fetchTeacher("");
      this.selectedItem = item;
      // this.editForm.id = item.id;
      // this.editForm.fullName = item.fullName;
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
    saveEditUser() {
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
        add: true,
      };
    },
    addNewUser() {
      console.log("this.addForm.provinceId ===> ", this.addForm.provinceCode);
      this.loadingModal = true;
      const validation = this.validateAddNewStudent();
      if (!validation) {
        this.loadingModal = false;
        return;
      }
      var formAddData = {
        fullName: this.addForm.fullName,
        dob: this.addForm.dob,
        gender: this.addForm.gender,
        phoneNumber: this.addForm.phoneNumber,
        parentPhoneNumber: this.addForm.parentPhoneNumber,
        provinceCode: this.addForm.provinceCode,
        districtCode: this.addForm.districtCode,
        wardCode: this.addForm.wardCode,
        addressDetail: this.addForm.addressDetail,
        classID: this.addForm.classID
      };
      UserRepository.addNewStudent(formAddData)
        .then((response) => {
          if (response.data.success === true) {
            this.$notification.success({
              message: "Thêm mới học sinh thành công!",
            });
            this.paginate();
            this.closeModal();
            this.loadingModal = false;
          } else {
            this.$notification.error({
              message: "Thêm mới học sinh thất bại!",
            });
            this.loadingModal = false;
          }
        })
        .catch(() => {
          this.$notification.error({
            message: "Thêm mới học sinh thất bại!",
          });
          this.loadingModal = false;
        });
    },
    validate() {
      let isValid = true;
      this.errors = { ...defaultInputErrors };
      if (this.editForm.fullName == "" || this.editForm.fullName == null) {
        this.errors.fullName = requiredError;
        isValid = false;
      }
      if (
        this.editForm.phoneNumber == "" ||
        this.editForm.phoneNumber == null
      ) {
        this.errors.phoneNumber = requiredError;
        isValid = false;
      }
      if (
        this.editForm.parentPhoneNumber == "" ||
        this.editForm.parentPhoneNumber == null
      ) {
        this.errors.parentPhoneNumber = requiredError;
        isValid = false;
      }
      if (
        this.editForm.provinceCode == "" ||
        this.editForm.provinceCode == null
      ) {
        this.errors.provinceCode = requiredError;
        isValid = false;
      }
      if (
        this.editForm.districtCode == "" ||
        this.editForm.districtCode == null
      ) {
        this.errors.districtCode = requiredError;
        isValid = false;
      }
      if (this.editForm.wardCode == "" || this.editForm.wardCode == null) {
        this.errors.wardCode = requiredError;
        isValid = false;
      }
      if (
        this.editForm.addressDetail == "" ||
        this.editForm.addressDetail == null
      ) {
        this.errors.addressDetail = requiredError;
        isValid = false;
      }
      return isValid;
    },
    validateAddNewStudent() {
      let isValid = true;
      this.errors = { ...defaultInputErrors };
      if (this.addForm.fullName == "" || this.addForm.fullName == null) {
        this.errors.fullName = requiredError;
        isValid = false;
      }
      if (this.addForm.phoneNumber == "" || this.addForm.phoneNumber == null) {
        this.errors.phoneNumber = requiredError;
        isValid = false;
      }
      if (
        this.addForm.parentPhoneNumber == "" ||
        this.addForm.parentPhoneNumber == null
      ) {
        this.errors.parentPhoneNumber = requiredError;
        isValid = false;
      }
      if (
        this.addForm.provinceCode == "" ||
        this.addForm.provinceCode == null
      ) {
        this.errors.provinceCode = requiredError;
        isValid = false;
      }
      if (
        this.addForm.districtCode == "" ||
        this.addForm.districtCode == null
      ) {
        this.errors.districtCode = requiredError;
        isValid = false;
      }
      if (this.addForm.wardCode == "" || this.addForm.wardCode == null) {
        this.errors.wardCode = requiredError;
        isValid = false;
      }
      if (
        this.addForm.addressDetail == "" ||
        this.addForm.addressDetail == null
      ) {
        this.errors.addressDetail = requiredError;
        isValid = false;
      }
      if (this.addForm.classID == "" || this.addForm.classID == null) {
        this.errors.classID = requiredError;
        isValid = false;
      }
      return isValid;
    },
    async fetchProvince(provinceName) {
      LocationRepository.getProvinceByName(provinceName).then((res) => {
        this.provinceList = res.data.data;
      });
    },
    fetchDistrict(value) {
      this.provinceCodeSearch = value;
      LocationRepository.getDistrictInProvince(
        this.provinceCodeSearch,
        ""
      ).then((res) => {
        console.log(res);
        this.districtList = res.data.data;
      });
    },
    fetchDistrictAfter(districtName) {
      LocationRepository.getDistrictInProvince(
        this.provinceCodeSearch,
        districtName
      ).then((res) => {
        this.districtList = res.data.data;
      });
    },
    fetchWard(value) {
      this.districtCodeSearch = value;
      LocationRepository.getWardInDistrict(this.districtCodeSearch, "").then(
        (res) => {
          this.wardList = res.data.data;
        }
      );
    },
    fetchWardAfter(wardName) {
      LocationRepository.getWardInDistrict(
        this.districtCodeSearch,
        wardName
      ).then((res) => {
        this.wardList = res.data.data;
      });
    },
    fetchClass(className) {
      ClassRepository.searchClassByName(className).then((res) => {
        this.classList = res.data.data.items;
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
