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
              >Tài khoản
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
                v-model="editForm.provinceObj"
                class="filter-select"
                style="width: 100%"
                @search="fetchProvince"
              >
                <a-select-option
                  v-for="item in provinceList"
                  :key="item.code"
                  :value="item"
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
                v-model="editForm.districtObj"
                class="filter-select"
                style="width: 100%"
                @search="fetchDistrict"
              >
                <a-select-option
                  v-for="item in districtList"
                  :key="item.code"
                  :value="item"
                >
                  {{ item.name }}
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
                v-model="editForm.wardObj"
                class="filter-select"
                style="width: 100%"
                @search="fetchWard"
              >
                <a-select-option
                  v-for="item in wardList"
                  :key="item.code"
                  :value="item"
                >
                  {{ item.name }}
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
              >Tài khoản
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
                v-model="addForm.provinceObj"
                class="filter-select"
                style="width: 100%"
                @search="fetchProvince"
              >
                <a-select-option
                  v-for="item in provinceList"
                  :key="item.code"
                  :value="item"
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
                v-model="addForm.districtObj"
                class="filter-select"
                style="width: 100%"
                @search="fetchDistrict"
              >
                <a-select-option
                  v-for="item in districtList"
                  :key="item.code"
                  :value="item"
                >
                  {{ item.name }}
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
                v-model="addForm.wardObj"
                class="filter-select"
                style="width: 100%"
                @search="fetchWard"
              >
                <a-select-option
                  v-for="item in wardList"
                  :key="item.code"
                  :value="item"
                >
                  {{ item.name }}
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
        </a-modal>

        <!-- Add modal -->
        <a-modal
          title="Thêm mới lớp học"
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
                  v-for="item in teacherList"
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
import LocationRepository from "../api/location.js";
import moment from "moment";
import axios from "axios";

const defaultModalState = {
  add: false,
  edit: false,
};

const defaultForm = {
  id: undefined,
  fullName: "",
  username: "",
  dob: "2000-01-01",
  gender: true,
  phoneNumber: "",
  parentPhoneNumber: "",
  provinceId: undefined,
  provinceObj: "",
  provinceName: "",
  districtId: undefined,
  districtObj: "",
  districtName: "",
  wardId: undefined,
  wardObj: "",
  wardName: "",
  addressDetail: "",
};

const requiredError = "Không được để trống thông tin này!";

const defaultInputErrors = {
  fullName: "",
  username: "",
  gender: "",
  phoneNumber: "",
  parentPhoneNumber: "",
};

function markRequireAll(query) {
  const words = query.split(/\s+/);
  return words.map((w) => `+${w}`).join(" ");
}

export default {
  data() {
    return {
      data: [],
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
        gender: "",
        wardName: "",
        districtName: "",
        provinceName: "",
      },
      provinceList: [
        {
          code: 2,
          name: "Hưng Yên",
        },
      ],
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
    this.searchUserDetail();
    this.fetchProvince("");
    // this.fetchDistrict("");
    // this.fetchWard("");
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
      UserRepository.searchUserDetail(this.formDataSearch, this.current).then(
        (res) => {
          this.data = res.data.data.items;
          this.totals = res.data.data.total;
          this.loading = false;
        }
      );
    },
    searchUserDetail() {
      this.loading = true;
      UserRepository.searchUserDetail(this.formDataSearch, 1).then((res) => {
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
        this.formDataSearch.gender = selectedKeys[0];
      } else if (dataIndex === "wardName") {
        this.formDataSearch.wardName = selectedKeys[0];
      } else if (dataIndex === "districtName") {
        this.formDataSearch.districtName = selectedKeys[0];
      } else if (dataIndex === "provinceName") {
        this.formDataSearch.provinceName = selectedKeys[0];
      }
      this.searchUserDetail();
    },

    handleReset(dataIndex, clearFilters) {
      if (dataIndex === "fullName") {
        this.formDataSearch.fullName = "";
      } else if (dataIndex === "gender") {
        this.formDataSearch.gender = "";
      } else if (dataIndex === "wardName") {
        this.formDataSearch.wardName = "";
      } else if (dataIndex === "districtName") {
        this.formDataSearch.districtName = "";
      } else if (dataIndex === "provinceName") {
        this.formDataSearch.provinceName = "";
      }
      clearFilters();
      this.searchUserDetail();
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
      console.log("this.addForm.provinceId ===> ", this.addForm.provinceObj);
      this.loadingModal = true;
      const validation = this.validateAddNew();
      if (!validation) {
        this.loadingModal = false;
        return;
      }
      // var formAddData = {
      //   fullName: this.addForm.fullName,
      //   username: this.addForm.username,
      //   dob: this.addForm.dob,
      //   gender: this.addForm.gender,
      //   phoneNumber: this.addForm.phoneNumber,
      //   parentPhoneNumber: this.addForm.parentPhoneNumber,
      //   provinceId: this.addForm.provinceId,
      //   provinceName: this.addForm.provinceName,
      //   districtId: this.addForm.districtId,
      //   districtName: this.addForm.districtName,
      //   wardId: this.addForm.wardId,
      //   wardName: this.addForm.wardName,
      //   addressDetail: this.addForm.addressDetail
      // };
      // UserRepository.addNewUser(formAddData)
      //   .then((response) => {
      //     if (response.data.success === true) {
      //       this.$notification.success({
      //         message: "Thêm mới thành công!",
      //       });
      //       this.paginate();
      //       this.closeModal();
      //       this.loadingModal = false;
      //     } else {
      //       this.$notification.error({
      //         message: "Tên lớp học đã tồn tại!",
      //       });
      //       this.loadingModal = false;
      //     }
      //   })
      //   .catch(() => {
      //     this.$notification.error({
      //       message: "Tên lớp học đã tồn tại!",
      //     });
      //     this.loadingModal = false;
      //   });
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
    async fetchProvince(provinceName) {
      LocationRepository.getAllProvince(provinceName).then(res => {
        this.provinceList = res;
        console.log("res.provinces ===> ", res);
      });
    },
    fetchDistrict(provinceCode) {
      LocationRepository.getDistrictInProvince(provinceCode).then((res) => {
        this.districtList = res.districts;
        console.log("res.districts ===> ", res.districts);
      });
    },
    fetchWard(wardCode) {
      LocationRepository.getWardInDistrict(wardCode).then((res) => {
        this.wardList = res.wards;
        console.log("res.wards ===> ", res.wards);
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
