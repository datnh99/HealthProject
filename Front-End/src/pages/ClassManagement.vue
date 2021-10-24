<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <h4 class="card-title">Class Management</h4>
          </template>
          <div class="table-responsive text-left">
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
                  <span
                    v-if="searchText && searchedColumn === column.dataIndex"
                  >
                    <template
                      v-for="(fragment, i) in text
                        .toString()
                        .split(
                          new RegExp(
                            `(?<=${searchText})|(?=${searchText})`,
                            'i'
                          )
                        )"
                    >
                      <mark
                        v-if="
                          fragment.toLowerCase() === searchText.toLowerCase()
                        "
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
                      <a-menu-item
                        key="1"
                        @click="handleEditItemBtnClick(item)"
                      >
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
                              >Ban có chắc chắn muốn xóa lớp học này
                              không?</span
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
              <div class="gutter-example pt-md pagnigation-custom">
                <a-pagination
                  v-model="current"
                  show-quick-jumper
                  :default-current="1"
                  :total="totals"
                  @change="paginate"
                />
              </div>
            </a-spin>
          </div>
        </card>

        <!-- edit modal -->
        <a-modal
          title="Chỉnh sửa lớp học"
          v-model="showModal.edit"
          @ok="saveEditClass"
          @cancel="closeEditForm()"
        >
          <a-row :gutter="[24, 16]">
            <a-col :span="8"
              >Category Name
              <span class="red">*</span>
            </a-col>
            <a-col :span="16">
              <a-textarea
                v-model="editForm.categoryName"
                :auto-size="{ minRows: 1, maxRows: 5 }"
                :min="0"
                class="full-width--i"
              />
              <span v-if="errors.categoryName" class="red">
                {{ errors.categoryName }}
              </span>
            </a-col>
          </a-row>

          <a-row :gutter="[24, 16]">
            <a-col :span="8">
              Giáo viên chủ nhiệm
            </a-col>
            <a-col :span="16">
              <a-select
                v-model="editForm.deleted"
                class="filter-select"
                style="width: 100%"
              >
                <a-select-option
                  v-for="item in statusList"
                  :key="item.key"
                  :value="item.value"
                >
                  {{ item.key }}
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
import ClassRepository from "../api/class.js";

const defaultModalState = {
  add: false,
  edit: false,
};

const defaultForm = {
  id: undefined,
  className: "",
  teacherID: undefined,
};

const requiredError = "This field can't blank";

const defaultInputErrors = {
  className: ""
};

export default {
  data() {
    return {
      data: [],
      current: 1,
      totals: 0,
      loading: false,
      formDataSearch: {
        className: "",
        teacherName: "",
      },
      showModal: { ...defaultModalState },
      editForm: { ...defaultForm },
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
          title: "Tên lớp",
          dataIndex: "className",
          width: 100,
          key: "className",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.name
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
          title: "Tài khoản GVCN",
          dataIndex: "teacherAccount",
          width: 100,
          key: "teacherAccount",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.address
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
          title: "Tên GVCN",
          dataIndex: "teacherName",
          width: 100,
          key: "teacherName",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.address
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
    this.searchClass();
  },
  methods: {
    paginate(current = 1) {
      this.loading = true;
      this.current = current;
      ClassRepository.searchClass(this.formDataSearch, this.current).then(
        (res) => {
          this.data = res.data.data.items;
          this.totals = res.data.data.total;
          this.loading = false;
        }
      );
    },
    searchClass() {
      this.loading = true;
      ClassRepository.searchClass(this.formDataSearch, 1).then((res) => {
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
      this.searchClass();
    },

    handleReset(dataIndex, clearFilters) {
      if (dataIndex === "className") {
        this.formDataSearch.className = "";
      } else if (dataIndex === "teacherName") {
        this.formDataSearch.teacherName = "";
      }
      clearFilters();
      this.searchClass();
      this.searchText = "";
    },
    async handleEditItemBtnClick(item) {
      this.selectedItem = item;
      this.editForm.id = item.id;
      this.editForm.className = item.className;
      this.editForm.teacherID = item.teacherID;
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
    },
    saveEditClass() {
      const validation = this.validate();
      if (!validation) {
        return;
      }
      var formEditData = {
        id: this.editForm.id,
        className: this.editForm.className,
        teacherID: this.editForm.teacherID,
      };
      ClassRepository.editClass(formEditData)
        .then(response => {
          if (response.data.data === true) {
            this.$notification.success({
              message: "Chỉnh sửa thành công!"
            });
            this.paginate();
            this.closeModal();
          } else {
            this.$notification.error({
              message: "Chỉnh sửa thất bại!"
            });
          }
        })
        .catch(() => {
          this.$notification.error({
            message: "Chỉnh sửa thất bại!"
          });
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

.pagnigation-custom {
  float: right;
}
</style>
